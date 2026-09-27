package com.example.data.model

enum class ScopeTier(
    val title: String,
    val targetSections: Int,
    val sectionRangeDescription: String,
    val description: String,
    val badgeColorHex: Long = 0xFF4CAF50
) {
    SHORT(
        title = "Short",
        targetSections = 3,
        sectionRangeDescription = "3 Focused Sections",
        description = "Core concepts, high-yield overview, low latency output"
    ),
    MEDIUM(
        title = "Medium",
        targetSections = 6,
        sectionRangeDescription = "5 to 7 Sections",
        description = "Intermediate mastery, practical depth, structured examples"
    ),
    LONG(
        title = "Long",
        targetSections = 10,
        sectionRangeDescription = "8 to 12 Sections",
        description = "Comprehensive in-depth study, real-world applications & deep analysis"
    );

    companion object {
        fun fromString(value: String): ScopeTier {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: MEDIUM
        }
    }
}

enum class ProficiencyLevel(
    val title: String,
    val subtitle: String,
    val emoji: String,
    val pedagogyFocus: String
) {
    BEGINNER(
        title = "Beginner",
        subtitle = "Foundational Principles & Core Concepts",
        emoji = "🌱",
        pedagogyFocus = "Zero prerequisite knowledge assumed. Clear plain-language explanations, intuitive analogies, step-by-step introduction of terminology, and foundational mastery."
    ),
    INTERMEDIATE(
        title = "Intermediate",
        subtitle = "Practical Workflows & Applied Skills",
        emoji = "⚡",
        pedagogyFocus = "Assumes basic familiarity. Focuses on practical implementation, standard operational procedures, common troubleshooting, and real-world trade/domain applications."
    ),
    ADVANCED(
        title = "Advanced",
        subtitle = "Deep Architecture, Edge Cases & Optimizations",
        emoji = "🚀",
        pedagogyFocus = "Deep technical analysis, architectural trade-offs, high-load edge cases, diagnostics, and nuanced system behavior."
    ),
    MASTER(
        title = "Mastery / Expert",
        subtitle = "Professional Standards & Regulatory Mastery",
        emoji = "👑",
        pedagogyFocus = "Exhaustive standard compliance, regulatory codes, multi-disciplinary integrations, failure mode mitigation, and capstone challenges."
    );

    companion object {
        fun fromString(value: String): ProficiencyLevel {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: INTERMEDIATE
        }
    }
}

enum class CourseDuration(
    val title: String,
    val estimatedTime: String,
    val targetSections: Int,
    val scopeTier: ScopeTier,
    val emoji: String,
    val description: String
) {
    CRASH_COURSE(
        title = "Crash Course",
        estimatedTime = "15 – 30 min",
        targetSections = 3,
        scopeTier = ScopeTier.SHORT,
        emoji = "⚡",
        description = "High-yield micro-learning. 3 concentrated modules for rapid onboarding and immediate active recall review."
    ),
    STANDARD(
        title = "Standard Course",
        estimatedTime = "1 – 2 hours",
        targetSections = 5,
        scopeTier = ScopeTier.MEDIUM,
        emoji = "📖",
        description = "Comprehensive standard curriculum. 5 structured modules balanced between theoretical foundation and practical application."
    ),
    DEEP_DIVE(
        title = "Deep Dive",
        estimatedTime = "3 – 5 hours",
        targetSections = 8,
        scopeTier = ScopeTier.LONG,
        emoji = "🔬",
        description = "Immersive multi-module curriculum. 8 comprehensive sections covering domain depth, complex workflows, and edge cases."
    ),
    MASTERCLASS(
        title = "Full Masterclass",
        estimatedTime = "6+ hours",
        targetSections = 10,
        scopeTier = ScopeTier.LONG,
        emoji = "🎓",
        description = "Exhaustive professional pathway. 10 exhaustive sections with rigorous diagnostics and cumulative mastery."
    );

    companion object {
        fun fromString(value: String): CourseDuration {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: STANDARD
        }
    }
}

enum class EducationalCategory(
    val title: String,
    val subtitle: String,
    val emoji: String
) {
    ALL(
        title = "All Disciplines",
        subtitle = "Complete curriculum catalog",
        emoji = "📚"
    ),
    MATHEMATICS(
        title = "Mathematics",
        subtitle = "Linear Algebra, Calculus, Probability & Logic",
        emoji = "📐"
    ),
    NATURAL_SCIENCES(
        title = "Natural Sciences",
        subtitle = "Genetics, Astrophysics, Chemistry & Cosmology",
        emoji = "🔬"
    ),
    HISTORY_CIVILIZATION(
        title = "History & Civilizations",
        subtitle = "Ancient Civilizations, Industrial Age, Geopolitics",
        emoji = "🏛️"
    ),
    ARTS_HUMANITIES(
        title = "Art & Humanities",
        subtitle = "Renaissance Masters, Color Theory & Modern Movements",
        emoji = "🎨"
    ),
    COMPUTER_SCIENCE(
        title = "Computer Science",
        subtitle = "Software, Android, AI & Distributed Systems",
        emoji = "💻"
    ),
    SKILLED_TRADES(
        title = "Skilled Trades",
        subtitle = "Millwright, HVAC, Electrician, Carpentry, Welding",
        emoji = "🛠️"
    ),
    HEALTHCARE_SAFETY(
        title = "Healthcare & Safety",
        subtitle = "Emergency First Aid, CPR, OSHA & FEMA Safety",
        emoji = "🏥"
    ),
    SCIENCE_ENGINEERING(
        title = "Engineering & Physics",
        subtitle = "Quantum Systems, Thermodynamics & Robotics",
        emoji = "⚙️"
    ),
    BUSINESS_FINANCE(
        title = "Business & Leadership",
        subtitle = "Operations, Project Economics & Bookkeeping",
        emoji = "📊"
    );

    companion object {
        fun detectCategory(topic: String, title: String): EducationalCategory {
            val text = "$topic $title".lowercase()
            return when {
                text.contains("first aid") || text.contains("cpr") || text.contains("health") ||
                text.contains("medical") || text.contains("aed") || text.contains("fema") ||
                text.contains("hazard") || text.contains("safety") || text.contains("epidemiology") -> HEALTHCARE_SAFETY

                text.contains("linear algebra") || text.contains("vector space") || text.contains("calculus") ||
                text.contains("differential equation") || text.contains("probability") || text.contains("statistical") ||
                text.contains("matrix") || text.contains("eigenvalue") || text.contains("mathematics") ||
                text.contains("math") -> MATHEMATICS

                text.contains("genetics") || text.contains("crispr") || text.contains("astrophysics") ||
                text.contains("cosmology") || text.contains("organic chemistry") || text.contains("biology") ||
                text.contains("dna") || text.contains("chemical") || text.contains("physics") ||
                text.contains("quantum") || text.contains("science") -> NATURAL_SCIENCES

                text.contains("history") || text.contains("civilization") || text.contains("ancient") ||
                text.contains("industrial revolution") || text.contains("cold war") || text.contains("geopolitics") ||
                text.contains("mediterranean") || text.contains("mesopotamia") || text.contains("roman") ||
                text.contains("greece") -> HISTORY_CIVILIZATION

                text.contains("art") || text.contains("renaissance") || text.contains("color theory") ||
                text.contains("visual") || text.contains("painting") || text.contains("aesthetic") ||
                text.contains("cubism") || text.contains("impressionism") || text.contains("bauhaus") ||
                text.contains("composition") -> ARTS_HUMANITIES

                text.contains("millwright") || text.contains("hvac") || text.contains("electrician") ||
                text.contains("carpenter") || text.contains("carpentry") || text.contains("weld") ||
                text.contains("plumb") || text.contains("mechanic") || text.contains("machin") ||
                text.contains("trade") || text.contains("refriger") || text.contains("nec") ||
                text.contains("framing") || text.contains("alignment") || text.contains("rigging") -> SKILLED_TRADES

                text.contains("android") || text.contains("compose") || text.contains("neural") ||
                text.contains("deep learning") || text.contains("machine learning") ||
                text.contains("artificial intelligence") || Regex("\\bai\\b").containsMatchIn(text) ||
                text.contains("code") || text.contains("programming") || text.contains("rust") ||
                text.contains("python") || text.contains("compiler") || text.contains("software") ||
                text.contains("distributed") -> COMPUTER_SCIENCE

                text.contains("business") || text.contains("finance") || text.contains("account") ||
                text.contains("bookkeep") || text.contains("economics") || text.contains("manage") -> BUSINESS_FINANCE

                else -> COMPUTER_SCIENCE
            }
        }
    }
}

data class Course(
    val id: String,
    val title: String,
    val topic: String,
    val description: String,
    val tier: ScopeTier,
    val proficiencyLevel: ProficiencyLevel = ProficiencyLevel.INTERMEDIATE,
    val duration: CourseDuration = when (tier) {
        ScopeTier.SHORT -> CourseDuration.CRASH_COURSE
        ScopeTier.MEDIUM -> CourseDuration.STANDARD
        ScopeTier.LONG -> CourseDuration.DEEP_DIVE
    },
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
    val bestScore: Int? = null,
    val lastScore: Int? = null,
    val sections: List<CourseSection> = emptyList(),
    val flashcards: List<Flashcard> = emptyList(),
    val examQuestions: List<ExamQuestion> = emptyList(),
    val category: EducationalCategory = EducationalCategory.detectCategory(topic, title),
    val certification: FreeCertification? = FreeCertificationsData.findCertificationForTopic(topic, title),
    val thumbnailUri: String? = null
)

data class CourseSection(
    val id: String,
    val courseId: String,
    val orderIndex: Int,
    val title: String,
    val content: String,
    val takeaways: List<String>,
    val checkpoint: ActiveRecallCheckpoint,
    val isCompleted: Boolean = false
)

data class ActiveRecallCheckpoint(
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class Flashcard(
    val id: String,
    val courseId: String,
    val term: String,
    val definition: String,
    val isMastered: Boolean = false
)

data class ExamQuestion(
    val id: String,
    val courseId: String,
    val sectionIndex: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class TestAttempt(
    val id: String,
    val courseId: String,
    val attemptNumber: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val scorePercentage: Int,
    val gradeLetter: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val questionFeedbacks: List<QuestionFeedback>
)

data class QuestionFeedback(
    val questionId: String,
    val questionText: String,
    val selectedOptionIndex: Int,
    val correctOptionIndex: Int,
    val isCorrect: Boolean,
    val selectedAnswerText: String,
    val correctAnswerText: String,
    val diagnosticExplanation: String
)

data class GradeResult(
    val scorePercentage: Int,
    val gradeLetter: String,
    val feedbackMessage: String,
    val colorHex: Long
) {
    companion object {
        fun calculate(correctCount: Int, totalCount: Int): GradeResult {
            if (totalCount == 0) return GradeResult(0, "F", "No questions evaluated", 0xFFD32F2F)
            val percentage = ((correctCount.toDouble() / totalCount.toDouble()) * 100).toInt()
            return when {
                percentage >= 90 -> GradeResult(percentage, "A", "Outstanding Mastery! You demonstrated exceptional understanding.", 0xFF2E7D32)
                percentage >= 80 -> GradeResult(percentage, "B", "Strong Performance! Solid grasp of core and intermediate concepts.", 0xFF1976D2)
                percentage >= 70 -> GradeResult(percentage, "C", "Satisfactory Understanding. Review flagged concepts to solidify knowledge.", 0xFFF57C00)
                percentage >= 60 -> GradeResult(percentage, "D", "Needs Improvement. Essential areas require targeted review.", 0xFFE65100)
                else -> GradeResult(percentage, "F", "Comprehensive Review Recommended. Revisit section checkpoints and flashcards.", 0xFFC62828)
            }
        }
    }
}
