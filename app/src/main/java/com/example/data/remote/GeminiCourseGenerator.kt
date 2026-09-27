package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.ActiveRecallCheckpoint
import com.example.data.model.Course
import com.example.data.model.CourseDuration
import com.example.data.model.CourseSection
import com.example.data.model.ExamQuestion
import com.example.data.model.Flashcard
import com.example.data.model.FreeCertificationsData
import com.example.data.model.ProficiencyLevel
import com.example.data.model.ScopeTier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.TimeUnit

class GeminiCourseGenerator {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun generateCourse(
        topic: String,
        proficiencyLevel: ProficiencyLevel = ProficiencyLevel.INTERMEDIATE,
        duration: CourseDuration = CourseDuration.STANDARD,
        onStatusUpdate: (String) -> Unit = {}
    ): Result<Course> = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (_: Exception) {
            ""
        }

        val hasValidKey = apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY"

        if (!hasValidKey) {
            onStatusUpdate("Configuring course via local knowledge engine (${proficiencyLevel.title} • ${duration.title})...")
            val offlineCourse = OfflineCourseTemplates.generateDynamicOfflineCourse(topic, proficiencyLevel, duration)
            return@withContext Result.success(offlineCourse)
        }

        onStatusUpdate("Synthesizing curriculum with Gemini 3.5 Flash (${proficiencyLevel.title} • ${duration.title})...")

        try {
            val responseJson = callGeminiApi(topic, proficiencyLevel, duration, apiKey)
            onStatusUpdate("Parsing structured course modules & active recall checkpoints...")
            val course = parseCourseJson(topic, proficiencyLevel, duration, responseJson)
            Result.success(course)
        } catch (e: Exception) {
            Log.w("GeminiCourseGenerator", "Online generation failed: ${e.message}. Falling back to offline engine.", e)
            onStatusUpdate("Online API call reached limit or network drop. Falling back to local offline generator...")
            val fallbackCourse = OfflineCourseTemplates.generateDynamicOfflineCourse(topic, proficiencyLevel, duration)
            Result.success(fallbackCourse)
        }
    }

    suspend fun generateCourse(
        topic: String,
        tier: ScopeTier,
        onStatusUpdate: (String) -> Unit = {}
    ): Result<Course> {
        val duration = when (tier) {
            ScopeTier.SHORT -> CourseDuration.CRASH_COURSE
            ScopeTier.MEDIUM -> CourseDuration.STANDARD
            ScopeTier.LONG -> CourseDuration.DEEP_DIVE
        }
        return generateCourse(topic, ProficiencyLevel.INTERMEDIATE, duration, onStatusUpdate)
    }

    private fun callGeminiApi(
        topic: String,
        proficiencyLevel: ProficiencyLevel,
        duration: CourseDuration,
        apiKey: String
    ): String {
        val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val prompt = buildGenerationPrompt(topic, proficiencyLevel, duration)
        val systemInstruction = """
            You are an elite educational instructional designer and university professor specializing in $topic.
            Your task is to generate complete, high-quality, comprehensive educational course material in strict JSON format.
            Target Proficiency Level: ${proficiencyLevel.title} (${proficiencyLevel.subtitle}).
            Calibrate pedagogical depth, technical terminology, and instructional examples strictly to this proficiency level:
            - ${proficiencyLevel.pedagogyFocus}
            Course Duration: ${duration.title} (Estimated time: ${duration.estimatedTime}).
            You must strictly adhere to the requested number of sections: exactly ${duration.targetSections} sections.
            Each section must have thorough instructional content, exactly 3 distinct key takeaways, and 1 active recall checkpoint with multiple choice options.
            Also generate 6-10 flashcards (term and clear definition tailored for ${proficiencyLevel.title}), and 6-10 cumulative multiple-choice final exam questions testing the material across all sections.
            Output ONLY valid raw JSON with no Markdown backticks, no preamble, and no explanation.
        """.trimIndent()

        val requestJson = JSONObject().apply {
            val contentsArr = JSONArray().apply {
                put(JSONObject().apply {
                    val partsArr = JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", prompt)
                        })
                    }
                    put("parts", partsArr)
                })
            }
            put("contents", contentsArr)

            val sysInstObj = JSONObject().apply {
                val parts = JSONArray().apply {
                    put(JSONObject().apply {
                        put("text", systemInstruction)
                    })
                }
                put("parts", parts)
            }
            put("systemInstruction", sysInstObj)

            val genConfig = JSONObject().apply {
                put("responseMimeType", "application/json")
                put("temperature", 0.3)
                put("topP", 0.95)
            }
            put("generationConfig", genConfig)
        }

        val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(endpoint)
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()
        val bodyStr = response.body?.string() ?: throw IllegalStateException("Empty response from Gemini API")

        if (!response.isSuccessful) {
            throw IllegalStateException("Gemini API error ${response.code}: $bodyStr")
        }

        val respJson = JSONObject(bodyStr)
        val candidates = respJson.optJSONArray("candidates")
            ?: throw IllegalStateException("Missing candidates array in response")
        val candidate = candidates.getJSONObject(0)
        val content = candidate.getJSONObject("content")
        val parts = content.getJSONArray("parts")
        val text = parts.getJSONObject(0).getString("text")

        return cleanJsonOutput(text)
    }

    private fun cleanJsonOutput(raw: String): String {
        var cleaned = raw.trim()
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.removePrefix("```json").trim()
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.removePrefix("```").trim()
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.removeSuffix("```").trim()
        }
        return cleaned
    }

    fun parseCourseJson(
        topic: String,
        proficiencyLevel: ProficiencyLevel,
        duration: CourseDuration,
        jsonString: String
    ): Course {
        val root = JSONObject(jsonString)
        val courseId = UUID.randomUUID().toString()
        val title = root.optString("title", "Course: $topic")
        val description = root.optString("description", "A custom designed course on $topic")
        val tier = duration.scopeTier

        val sectionsArr = root.optJSONArray("sections") ?: JSONArray()
        val sectionsList = mutableListOf<CourseSection>()
        for (i in 0 until sectionsArr.length()) {
            val secObj = sectionsArr.getJSONObject(i)
            val secId = UUID.randomUUID().toString()
            val secTitle = secObj.optString("title", "Section ${i + 1}")
            val secContent = secObj.optString("content", "Instructional material for $secTitle.")

            val takeawaysList = mutableListOf<String>()
            val takeawaysArr = secObj.optJSONArray("takeaways")
            if (takeawaysArr != null) {
                for (j in 0 until takeawaysArr.length()) {
                    takeawaysList.add(takeawaysArr.getString(j))
                }
            }
            if (takeawaysList.isEmpty()) {
                takeawaysList.add("Understand the primary principles of $secTitle")
                takeawaysList.add("Analyze practical real-world applications and use cases")
                takeawaysList.add("Synthesize findings for critical evaluation and retention")
            }

            val chkObj = secObj.optJSONObject("checkpoint")
            val checkpoint = if (chkObj != null) {
                val q = chkObj.optString("question", "What is the key insight of $secTitle?")
                val opts = mutableListOf<String>()
                val optsArr = chkObj.optJSONArray("options")
                if (optsArr != null && optsArr.length() > 0) {
                    for (k in 0 until optsArr.length()) {
                        opts.add(optsArr.getString(k))
                    }
                } else {
                    opts.addAll(listOf("Key concept", "Secondary detail", "Unrelated factor", "Opposite result"))
                }
                val correctIdx = chkObj.optInt("correctOptionIndex", 0)
                val expl = chkObj.optString("explanation", "This highlights the core mechanism explained in the section.")
                ActiveRecallCheckpoint(q, opts, correctIdx, expl)
            } else {
                ActiveRecallCheckpoint(
                    question = "What is the primary objective of $secTitle?",
                    options = listOf(
                        "Foundational application and comprehension",
                        "Superficial overview with no implementation",
                        "Disregarding core principles",
                        "Arbitrary theoretical abstraction"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Foundational comprehension empowers practical mastery."
                )
            }

            sectionsList.add(
                CourseSection(
                    id = secId,
                    courseId = courseId,
                    orderIndex = i,
                    title = secTitle,
                    content = secContent,
                    takeaways = takeawaysList,
                    checkpoint = checkpoint,
                    isCompleted = false
                )
            )
        }

        // Flashcards
        val flashcardsList = mutableListOf<Flashcard>()
        val flashcardsArr = root.optJSONArray("flashcards")
        if (flashcardsArr != null) {
            for (i in 0 until flashcardsArr.length()) {
                val cardObj = flashcardsArr.getJSONObject(i)
                val term = cardObj.optString("term", "Term ${i + 1}")
                val def = cardObj.optString("definition", "Definition for $term")
                flashcardsList.add(
                    Flashcard(
                        id = UUID.randomUUID().toString(),
                        courseId = courseId,
                        term = term,
                        definition = def,
                        isMastered = false
                    )
                )
            }
        }

        // Exam questions
        val examList = mutableListOf<ExamQuestion>()
        val examArr = root.optJSONArray("examQuestions")
        if (examArr != null) {
            for (i in 0 until examArr.length()) {
                val qObj = examArr.getJSONObject(i)
                val qText = qObj.optString("question", "Question ${i + 1}")
                val optsList = mutableListOf<String>()
                val optsArr = qObj.optJSONArray("options")
                if (optsArr != null) {
                    for (j in 0 until optsArr.length()) {
                        optsList.add(optsArr.getString(j))
                    }
                }
                if (optsList.size < 2) {
                    optsList.clear()
                    optsList.addAll(listOf("Optimal Solution", "Suboptimal Approach", "Ineffective Strategy", "Invalid Method"))
                }
                val corr = qObj.optInt("correctOptionIndex", 0)
                val expl = qObj.optString("explanation", "The correct response reflects validated domain principles.")

                examList.add(
                    ExamQuestion(
                        id = UUID.randomUUID().toString(),
                        courseId = courseId,
                        sectionIndex = qObj.optInt("sectionIndex", i % (sectionsList.size.coerceAtLeast(1))),
                        question = qText,
                        options = optsList,
                        correctOptionIndex = corr,
                        explanation = expl
                    )
                )
            }
        }

        val category = FreeCertificationsData.categorizeTopic(topic, title)
        val cert = FreeCertificationsData.findMatchingCertification(topic, title)

        return Course(
            id = courseId,
            title = title,
            topic = topic,
            description = description,
            tier = tier,
            proficiencyLevel = proficiencyLevel,
            duration = duration,
            sections = sectionsList,
            flashcards = flashcardsList,
            examQuestions = examList,
            category = category,
            certification = cert
        )
    }

    fun parseCourseJson(topic: String, tier: ScopeTier, jsonString: String): Course {
        val duration = when (tier) {
            ScopeTier.SHORT -> CourseDuration.CRASH_COURSE
            ScopeTier.MEDIUM -> CourseDuration.STANDARD
            ScopeTier.LONG -> CourseDuration.DEEP_DIVE
        }
        return parseCourseJson(topic, ProficiencyLevel.INTERMEDIATE, duration, jsonString)
    }

    private fun buildGenerationPrompt(
        topic: String,
        proficiencyLevel: ProficiencyLevel,
        duration: CourseDuration
    ): String {
        return """
            Generate an educational course for the topic: "$topic".
            Target Proficiency Level: ${proficiencyLevel.title} (${proficiencyLevel.subtitle}).
            Pedagogical Focus: ${proficiencyLevel.pedagogyFocus}.
            Course Duration: ${duration.title} (Estimated time: ${duration.estimatedTime}, exactly ${duration.targetSections} sections).
            Duration Blueprint: ${duration.description}.

            Required JSON Schema:
            {
              "title": "String (engaging course title)",
              "description": "String (concise overview of what the student will learn tailored for ${proficiencyLevel.title})",
              "sections": [
                {
                  "title": "String (Section title)",
                  "content": "String (Thorough instructional content formatted with paragraphs, practical workflows, and real examples)",
                  "takeaways": ["String (Takeaway 1)", "String (Takeaway 2)", "String (Takeaway 3)"],
                  "checkpoint": {
                    "question": "String (Active recall checkpoint multiple choice question)",
                    "options": ["String (Option A)", "String (Option B)", "String (Option C)", "String (Option D)"],
                    "correctOptionIndex": 0,
                    "explanation": "String (Diagnostic explanation of why this answer is correct)"
                  }
                }
              ],
              "flashcards": [
                {
                  "term": "String (Technical term, principle, or formula for ${proficiencyLevel.title})",
                  "definition": "String (Clear, high-yield definition and practical context)"
                }
              ],
              "examQuestions": [
                {
                  "sectionIndex": 0,
                  "question": "String (Comprehensive test question)",
                  "options": ["String (A)", "String (B)", "String (C)", "String (D)"],
                  "correctOptionIndex": 0,
                  "explanation": "String (Detailed rationale for why this option is correct and why other options are incorrect)"
                }
              ]
            }

            Make sure:
            1. The number of sections is exactly ${duration.targetSections}.
            2. Number of flashcards: at least ${if (duration.targetSections <= 3) 6 else if (duration.targetSections <= 6) 8 else 10}.
            3. Number of examQuestions: at least ${if (duration.targetSections <= 3) 6 else if (duration.targetSections <= 6) 8 else 10}.
            4. Keep JSON strictly valid without comments or trailing commas.
        """.trimIndent()
    }

    private fun buildGenerationPrompt(topic: String, tier: ScopeTier): String {
        val duration = when (tier) {
            ScopeTier.SHORT -> CourseDuration.CRASH_COURSE
            ScopeTier.MEDIUM -> CourseDuration.STANDARD
            ScopeTier.LONG -> CourseDuration.DEEP_DIVE
        }
        return buildGenerationPrompt(topic, ProficiencyLevel.INTERMEDIATE, duration)
    }
}
