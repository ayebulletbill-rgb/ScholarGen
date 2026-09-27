package com.example.data.remote

import com.example.data.model.ActiveRecallCheckpoint
import com.example.data.model.Course
import com.example.data.model.CourseDuration
import com.example.data.model.CourseSection
import com.example.data.model.EducationalCategory
import com.example.data.model.ExamQuestion
import com.example.data.model.Flashcard
import com.example.data.model.FreeCertificationsData
import com.example.data.model.ProficiencyLevel
import com.example.data.model.ScopeTier
import java.util.UUID

object AcademicCourseTemplates {

    // ==================== MATHEMATICS ====================

    fun createLinearAlgebraCourse(): Course {
        val courseId = "course-math-linear-algebra"
        val topic = "Linear Algebra & Vector Spaces"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Vector Spaces, Subspaces & Linear Independence",
                content = """
                    Linear algebra is the foundational language of multidimensional computation, geometry, and machine learning. 

                    1. Vector Space Axioms: A vector space V over a field F (usually real numbers ℝ) is a set closed under vector addition and scalar multiplication satisfying 8 core axioms (associativity, commutativity, additive identity, additive inverse, and distributive properties).
                    2. Linear Combinations & Span: For vectors v1, ..., vk in V, any expression c1*v1 + ... + ck*vk is a linear combination. The span of these vectors represents all possible linear combinations that can be formed.
                    3. Linear Independence: A set of vectors {v1, ..., vk} is linearly independent if and only if c1*v1 + ... + ck*vk = 0 implies all coefficients c1 = c2 = ... = ck = 0. If any vector can be written as a combination of the others, the set is dependent.
                    4. Basis and Dimension: A basis B of V is a linearly independent set that spans V. The cardinality of B is invariant and defines the dimension dim(V). In ℝ³, any three linearly independent vectors form a complete basis.
                """.trimIndent(),
                takeaways = listOf(
                    "A vector space must be closed under addition and scalar multiplication.",
                    "Vectors are linearly independent if zero can only be formed by all-zero scalar weights.",
                    "A basis is a minimal spanning set and maximum linearly independent set whose size defines dimension."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "If vectors v1, v2, v3 in ℝ³ satisfy 2*v1 - 3*v2 + v3 = 0, what can be concluded?",
                    options = listOf(
                        "The vectors {v1, v2, v3} are linearly dependent because a non-trivial linear combination yields zero.",
                        "The vectors form a basis for ℝ³ because their coefficients sum to zero.",
                        "The vectors are orthogonal to one another.",
                        "The dimension of their span is strictly 3."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Linear independence requires all scalar coefficients to be zero. Having non-zero weights (2, -3, 1) producing the zero vector proves linear dependence."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Linear Transformations, Matrices & Kernel/Range",
                content = """
                    Matrices represent linear transformations between vector spaces.

                    1. Definition of Linear Transformation: A mapping T: V -> W is linear if T(u + v) = T(u) + T(v) and T(c*v) = c*T(v) for all vectors u, v and scalar c. Linear transformations preserve vector addition and scalar scaling, mapping lines through the origin to lines or points.
                    2. Standard Matrix Representation: Every linear transformation T: ℝⁿ -> ℝᵐ can be represented by an m×n matrix A where columns are the images of standard basis vectors T(e1), ..., T(en).
                    3. Kernel (Null Space): The kernel ker(T) or nullspace N(A) is the set of all vectors x such that Ax = 0. It is always a subspace of the domain ℝⁿ.
                    4. Image (Column Space / Range): The range of T is the span of matrix columns in ℝᵐ.
                    5. Rank-Nullity Theorem: Fundamental theorem stating dim(V) = rank(T) + nullity(T). For an m×n matrix, rank(A) + nullity(A) = n.
                """.trimIndent(),
                takeaways = listOf(
                    "Linear maps preserve vector addition and scalar multiplication, keeping the origin fixed.",
                    "The nullspace contains all vectors mapped to zero, while the column space is the transformation's range.",
                    "Rank-Nullity theorem dictates: rank(A) + dim(nullspace) = total columns n."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "For a 4×6 matrix with rank 3, what is the dimension of its nullspace (kernel)?",
                    options = listOf(
                        "3, because by Rank-Nullity theorem: nullity = columns (6) - rank (3) = 3.",
                        "1, because rank cannot exceed rows.",
                        "4, matching the row count.",
                        "0, because the matrix is full rank."
                    ),
                    correctOptionIndex = 0,
                    explanation = "The Rank-Nullity theorem states rank + nullity = n (number of columns). Here, 3 + nullity = 6, so nullity = 3."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "Eigenvalues, Eigenvectors & Diagonalization",
                content = """
                    Eigen-analysis reveals invariant geometric axes of linear operators.

                    1. The Eigenvalue Equation: For a square n×n matrix A, a non-zero vector v is an eigenvector with scalar eigenvalue λ if A*v = λ*v. Geometrically, A only scales v by factor λ without rotating its direction.
                    2. Characteristic Polynomial: Rewriting as (A - λ*I)v = 0 requires det(A - λ*I) = 0 for non-trivial solutions. Solving this degree-n polynomial yields the eigenvalues.
                    3. Eigenspaces: For each eigenvalue λ, the nullspace N(A - λ*I) forms the eigenspace E_λ, containing all eigenvectors associated with λ plus the zero vector.
                    4. Diagonalization: A matrix A is diagonalizable (A = P*D*P⁻¹) if and only if it has n linearly independent eigenvectors. D is a diagonal matrix of eigenvalues, and P contains the corresponding eigenvectors as columns.
                    5. Spectral Theorem for Symmetric Matrices: Real symmetric matrices (A = Aᵀ) always possess real eigenvalues and can be orthogonally diagonalized (A = Q*D*Qᵀ with Q orthogonal).
                """.trimIndent(),
                takeaways = listOf(
                    "Eigenvectors maintain their directional orientation under matrix transformation, scaled only by λ.",
                    "Eigenvalues are roots of the characteristic polynomial equation det(A - λ*I) = 0.",
                    "Real symmetric matrices always have real eigenvalues and orthogonal eigenvectors."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why does det(A - λ*I) = 0 allow finding eigenvalues?",
                    options = listOf(
                        "Because a non-zero eigenvector v satisfies (A - λ*I)v = 0, requiring (A - λ*I) to be non-invertible with determinant zero.",
                        "Because the trace of any matrix equals its determinant.",
                        "Because it proves all eigenvalues are positive.",
                        "Because diagonal matrices always have determinant zero."
                    ),
                    correctOptionIndex = 0,
                    explanation = "If det(A - λ*I) were non-zero, the matrix would be invertible, yielding only the trivial solution v = 0. A non-zero eigenvector requires non-trivial nullspace, meaning det = 0."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-4",
                courseId = courseId,
                orderIndex = 3,
                title = "Orthogonality, Gram-Schmidt & Singular Value Decomposition",
                content = """
                    Orthogonality enables optimal geometric projection and data reduction.

                    1. Inner Products & Orthogonality: Two vectors u, v are orthogonal if their dot product u · v = 0. An orthonormal set consists of mutually orthogonal unit vectors (length 1).
                    2. Orthogonal Projections: The projection of vector y onto subspace W spanned by orthonormal basis {u1, ..., uk} is given by proj_W(y) = (y·u1)u1 + ... + (y·uk)uk. This minimizes the distance ||y - proj_W(y)||.
                    3. Gram-Schmidt Orthogonalization: An algorithmic procedure taking any linearly independent basis {v1, ..., vk} and systematically subtracting parallel projections to generate an orthonormal basis {q1, ..., qk}. Yields QR factorization (A = Q*R).
                    4. Singular Value Decomposition (SVD): Any real m×n matrix A can be factored as A = U * Σ * Vᵀ, where U (m×m) and V (n×n) are orthogonal matrices, and Σ is an m×n diagonal matrix of non-negative singular values σ1 ≥ σ2 ≥ ... ≥ 0. SVD powers PCA, dimensionality reduction, and recommender systems.
                """.trimIndent(),
                takeaways = listOf(
                    "Orthogonal vectors have zero inner product, forming optimal coordinate axes.",
                    "Gram-Schmidt converts any arbitrary basis into an orthonormal coordinate system.",
                    "SVD factors any rectangular matrix into rotation, scaling, and rotation (A = U * Σ * Vᵀ)."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the primary significance of Singular Value Decomposition (SVD) over standard diagonalization?",
                    options = listOf(
                        "SVD applies to any rectangular matrix (m×n), whereas eigendecomposition requires square diagonalizable matrices.",
                        "SVD only works for integer matrices.",
                        "SVD eliminates all non-zero entries from a matrix.",
                        "SVD requires all matrix entries to be positive real numbers."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Unlike eigendecomposition which requires square matrices with sufficient eigenvectors, SVD decomposes any arbitrary m×n real or complex matrix into orthonormal components."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Vector Space", "A mathematical set closed under vector addition and scalar multiplication satisfying 8 linear axioms.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Linear Independence", "A set of vectors where no vector can be expressed as a linear combination of the others.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Rank-Nullity Theorem", "dim(Domain) = rank(T) + nullity(T). For m×n matrix, rank + dim(Nullspace) = n.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Eigenvalue Equation", "A*v = λ*v. Non-zero vector v scaled by scalar λ without changing spatial orientation.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Gram-Schmidt Process", "Algorithm transforming any linearly independent basis into an orthonormal basis.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Singular Value Decomposition (SVD)", "Factorization A = U*Σ*Vᵀ decomposing any matrix into orthonormal singular vectors and singular values.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "What condition defines linear independence for vectors {v1, v2, v3}?",
                options = listOf(
                    "c1*v1 + c2*v2 + c3*v3 = 0 holds if and only if c1 = c2 = c3 = 0",
                    "Their dot products must all equal 1",
                    "Their norms must all be equal",
                    "At least one vector must equal the sum of the other two"
                ),
                correctOptionIndex = 0,
                explanation = "Linear independence strictly dictates that the only linear combination yielding the zero vector is the trivial all-zero scalar weights."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "For a linear transformation T: ℝ⁵ -> ℝ³, what is the maximum possible rank of T?",
                options = listOf(
                    "3, because the rank cannot exceed the dimension of the codomain ℝ³",
                    "5, because the domain dimension is 5",
                    "8, the sum of domain and codomain dimensions",
                    "15, the product of dimensions"
                ),
                correctOptionIndex = 0,
                explanation = "The range is a subspace of the codomain ℝ³. Since the codomain has dimension 3, the dimension of the range (rank) cannot exceed 3."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 2,
                question = "If λ is an eigenvalue of matrix A, which matrix must be singular (non-invertible)?",
                options = listOf(
                    "A - λ*I",
                    "A + λ*I",
                    "A²",
                    "λ*A"
                ),
                correctOptionIndex = 0,
                explanation = "Because (A - λ*I)v = 0 has a non-zero solution v, the operator (A - λ*I) must have a non-trivial nullspace, meaning det(A - λ*I) = 0 and it is singular."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 3,
                question = "What property characterizes an orthogonal matrix Q?",
                options = listOf(
                    "Qᵀ * Q = I, meaning columns are mutually orthonormal and Q⁻¹ = Qᵀ",
                    "All eigenvalues of Q must equal zero",
                    "det(Q) must equal 0",
                    "Q must be a diagonal matrix"
                ),
                correctOptionIndex = 0,
                explanation = "An orthogonal matrix has mutually orthonormal columns and rows, satisfying Qᵀ * Q = I, which implies Q⁻¹ = Qᵀ."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Rigorous academic study of vector spaces, matrix representations, rank-nullity, spectral theory, eigenvalues, Gram-Schmidt orthogonalization, and SVD.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.MATHEMATICS,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    fun createCalculusCourse(): Course {
        val courseId = "course-math-calculus"
        val topic = "Calculus & Differential Equations"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Limits, Continuity & Derivative Foundations",
                content = """
                    Calculus investigates rates of instantaneous change and accumulation.

                    1. Formal Limit Definition: A function f(x) approaches limit L as x approaches c if for every ε > 0 there exists δ > 0 such that 0 < |x - c| < δ implies |f(x) - L| < ε.
                    2. Continuity: A function is continuous at c if lim_{x->c} f(x) = f(c). Continuity ensures no breaks, jumps, or vertical asymptotes.
                    3. The Derivative: Defined as the instantaneous rate of change: f'(x) = lim_{h->0} [f(x + h) - f(x)] / h. Geometrically represents the slope of the tangent line to the curve at x.
                    4. Operational Rules: Product rule (f*g)' = f'*g + f*g', Quotient rule (f/g)' = (f'*g - f*g') / g², and Chain Rule (f(g(x)))' = f'(g(x)) * g'(x).
                """.trimIndent(),
                takeaways = listOf(
                    "Limits formalize behavior near a point without requiring evaluation at that point.",
                    "The derivative measures instantaneous rate of change via the difference quotient limit.",
                    "The chain rule handles composition of rates across composite systems."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What does the derivative f'(x) geometrically represent at x = a?",
                    options = listOf(
                        "The exact slope of the tangent line to the curve y = f(x) at x = a.",
                        "The area under the curve between 0 and a.",
                        "The average value of the function over all real numbers.",
                        "The distance of the function from the x-axis."
                    ),
                    correctOptionIndex = 0,
                    explanation = "The derivative represents the limiting secant slope as points coalesce, giving the instantaneous tangent slope."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Integrals & Fundamental Theorem of Calculus",
                content = """
                    Integration measures continuous accumulation and reverses differentiation.

                    1. Riemann Sums: The definite integral ∫_a^b f(x) dx is defined as the limit of Riemann sums as partition mesh width approaches zero: lim_{n->∞} ∑ f(x_i*) Δx.
                    2. Fundamental Theorem of Calculus (Part 1): If f is continuous on [a, b] and g(x) = ∫_a^x f(t) dt, then g'(x) = f(x). Differentiation undoes continuous integration.
                    3. Fundamental Theorem of Calculus (Part 2): If F is any antiderivative of f (F'(x) = f(x)), then ∫_a^b f(x) dx = F(b) - F(a). Enables exact evaluation via antiderivatives.
                    4. Integration Techniques: Substitution rule (u-substitution, reversing chain rule), Integration by Parts (∫ u dv = u*v - ∫ v du, reversing product rule), and partial fractions decomposition.
                """.trimIndent(),
                takeaways = listOf(
                    "Definite integration computes net signed area via the limit of approximating rectangles.",
                    "FTC establishes that differentiation and integration are inverse mathematical processes.",
                    "Integration by parts reverses the product rule for products of algebraic and transcendental functions."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "According to the Fundamental Theorem of Calculus, if F'(x) = f(x), what is ∫_a^b f(x) dx?",
                    options = listOf(
                        "F(b) - F(a)",
                        "F'(b) - F'(a)",
                        "f(b) - f(a)",
                        "F(b) * F(a)"
                    ),
                    correctOptionIndex = 0,
                    explanation = "The second part of the FTC evaluates a definite integral by taking the difference in the antiderivative at the upper and lower limits: F(b) - F(a)."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "Ordinary Differential Equations & Dynamic Systems",
                content = """
                    Differential equations describe natural laws relating quantities to their rates of change.

                    1. First-Order Separable Equations: Equations of the form dy/dx = g(x)*h(y) can be solved by separating variables: ∫ (1/h(y)) dy = ∫ g(x) dx.
                    2. Linear First-Order Equations: dy/dx + P(x)y = Q(x). Solved using an integrating factor I(x) = e^(∫ P(x) dx), multiplying both sides to rewrite the left side as d/dx [I(x)*y].
                    3. Second-Order Homogeneous Linear Equations: a*y'' + b*y' + c*y = 0. Solved via the characteristic equation a*r² + b*r + c = 0, producing real distinct roots, repeated roots, or complex conjugate roots (Euler's formula creating harmonic oscillations).
                    4. Applications: Modeling exponential population growth, Newton's law of cooling, radioactive decay, and RLC electrical damping circuits.
                """.trimIndent(),
                takeaways = listOf(
                    "Separable equations are integrated after grouping x and y terms with their respective differentials.",
                    "Integrating factor I(x) = exp(∫ P dx) turns linear first-order equations into an exact derivative.",
                    "Second-order linear ODEs model harmonic oscillators and electrical resonance circuits."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What integrating factor is used to solve the linear ODE dy/dx + 3y = 6?",
                    options = listOf(
                        "e^(3x), because P(x) = 3 and I(x) = e^(∫ 3 dx) = e^(3x).",
                        "3x",
                        "ln(3x)",
                        "e^(-3x)"
                    ),
                    correctOptionIndex = 0,
                    explanation = "The integrating factor is e^(∫ P(x) dx). With P(x) = 3, ∫ 3 dx = 3x, yielding e^(3x)."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Derivative Definition", "lim_{h->0} [f(x+h) - f(x)] / h. Instantaneous rate of change and tangent slope.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Chain Rule", "[f(g(x))]' = f'(g(x)) * g'(x). Rule for differentiating composite functions.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Fundamental Theorem of Calculus", "∫_a^b f(x) dx = F(b) - F(a), where F'(x) = f(x). Connects integration to differentiation.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Integration by Parts", "∫ u dv = u*v - ∫ v du. Derivation from the product rule of differentiation.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Integrating Factor", "I(x) = e^(∫ P(x) dx). Multiplier transforming linear differential equations into exact derivatives.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Harmonic Oscillator ODE", "y'' + ω²y = 0. General solution y = C1*cos(ωt) + C2*sin(ωt).", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "What is the derivative of f(x) = sin(x²)?",
                options = listOf(
                    "2x * cos(x²), by applying the chain rule",
                    "cos(x²)",
                    "2x * sin(x)",
                    "-2x * cos(x²)"
                ),
                correctOptionIndex = 0,
                explanation = "By the chain rule, d/dx [f(g(x))] = f'(g(x)) * g'(x). Here f(u) = sin(u) with derivative cos(u), and g(x) = x² with derivative 2x."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "What is the value of the definite integral ∫_0^2 (3x²) dx?",
                options = listOf(
                    "8, because the antiderivative is x³ and [2³ - 0³] = 8",
                    "6",
                    "12",
                    "4"
                ),
                correctOptionIndex = 0,
                explanation = "Antiderivative of 3x² is x³. Evaluating between 0 and 2 gives 2³ - 0³ = 8 - 0 = 8."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 2,
                question = "For dy/dt = -k*y with y(0) = y0, what is the exact solution?",
                options = listOf(
                    "y(t) = y0 * e^(-kt), exponential decay",
                    "y(t) = y0 - kt",
                    "y(t) = y0 * cos(kt)",
                    "y(t) = y0 / (1 + kt)"
                ),
                correctOptionIndex = 0,
                explanation = "Separating variables (dy/y = -k dt) and integrating yields ln|y| = -kt + C, which exponentiates to y(t) = y0 * e^(-kt)."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Differential and integral calculus covering epsilon-delta limits, differentiation rules, FTC, integration techniques, and ordinary differential equations.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.MATHEMATICS,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    // ==================== NATURAL SCIENCES ====================

    fun createMolecularGeneticsCourse(): Course {
        val courseId = "course-science-genetics"
        val topic = "Molecular Genetics & CRISPR Technology"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "DNA Architecture, Replication & The Central Dogma",
                content = """
                    Molecular biology explains life through nucleic acid polymers and enzymatic pathways.

                    1. DNA Double Helix: Described by Watson, Crick, and Franklin. Antiparallel strands (5' to 3' and 3' to 5') linked by phosphodiester backbone and complementary nitrogenous base pairs: Adenine pairs with Thymine (2 hydrogen bonds), Guanine pairs with Cytosine (3 hydrogen bonds).
                    2. Semiconservative Replication: DNA helicase unwinds the double helix at replication forks. DNA polymerase synthesizes new strands strictly in the 5'->3' direction, requiring an RNA primer created by primase.
                    3. Leading vs Lagging Strand: The leading strand is synthesized continuously; the lagging strand is synthesized discontinuously as Okazaki fragments, later joined by DNA ligase.
                    4. The Central Dogma: Genetic information flows sequentially: DNA is transcribed into messenger RNA (mRNA) by RNA polymerase in the nucleus, followed by translation of mRNA codons into polypeptides on ribosomes in the cytoplasm.
                """.trimIndent(),
                takeaways = listOf(
                    "Antiparallel strands pair via complementary hydrogen bonds (A-T: 2 bonds, G-C: 3 bonds).",
                    "DNA polymerase only adds nucleotides to the 3' hydroxyl group, requiring Okazaki fragments on the lagging strand.",
                    "The Central Dogma describes DNA -> RNA (transcription) -> Protein (translation)."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why does Guanine-Cytosine (G-C) rich DNA require higher temperatures to denature than Adenine-Thymine (A-T) rich DNA?",
                    options = listOf(
                        "G-C base pairs are stabilized by 3 hydrogen bonds, compared to only 2 hydrogen bonds between A-T pairs.",
                        "G-C pairs have covalent bonds instead of hydrogen bonds.",
                        "Cytosine has a larger molecular weight that absorbs heat.",
                        "A-T pairs repel each other at elevated temperatures."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Guanine and Cytosine form 3 intermolecular hydrogen bonds, requiring significantly higher thermal energy to break apart than the 2 hydrogen bonds connecting Adenine and Thymine."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Transcription, RNA Processing & Epigenetic Regulation",
                content = """
                    Gene expression is modulated by chromatin structure, transcriptional machinery, and post-transcriptional processing.

                    1. Eukaryotic Transcription: RNA Polymerase II binds to gene promoter sequences (e.g., the TATA box) assisted by transcription factors. It synthesizes a primary pre-mRNA transcript complementary to the template strand.
                    2. Post-Transcriptional Modifications: Three critical eukaryotic modifications occur:
                       - 5' 7-methylguanosine capping (protects from degradation and aids ribosome binding).
                       - 3' polyadenylation (poly-A tail conferring stability and nuclear export).
                       - Splicing via the spliceosome, excising non-coding introns and joining coding exons. Alternative splicing allows one gene to encode multiple distinct protein isoforms.
                    3. Epigenetics: Heritable changes in gene expression without alterations in nucleotide sequence. DNA methylation (cytosine methylation at CpG islands) silences genes. Histone acetylation (by HATs) relaxes chromatin (euchromatin) promoting active transcription.
                """.trimIndent(),
                takeaways = listOf(
                    "Eukaryotic pre-mRNA requires 5' capping, 3' poly-A tailing, and intron splicing before translation.",
                    "Alternative splicing generates proteomic diversity from a compact genome.",
                    "Epigenetic methylation silences chromatin while histone acetylation promotes active gene expression."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the biological function of the spliceosome?",
                    options = listOf(
                        "It excises non-coding introns from pre-mRNA and ligates coding exons together.",
                        "It replicates chromosomal telomeres during S-phase.",
                        "It translates codons into amino acid polypeptides.",
                        "It attaches poly-A tails to the 5' end of DNA."
                    ),
                    correctOptionIndex = 0,
                    explanation = "The spliceosome is an enzymatic ribonuclear complex that removes introns and splices exons together to produce mature mRNA."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "CRISPR-Cas9 Mechanism & Precision Genome Editing",
                content = """
                    CRISPR represents a revolutionary bacterial adaptive immune system adapted for targeted genomic engineering.

                    1. Bacterial Origins: Clustered Regularly Interspaced Short Palindromic Repeats (CRISPR) capture viral spacer sequences to remember past phage infections.
                    2. Cas9 Endonuclease Anatomy: Streptococcus pyogenes Cas9 is an RNA-guided endonuclease containing two catalytic nuclease domains: RuvC and HNH.
                    3. Guide RNA & PAM Recognition: Cas9 forms a ribonucleoprotein complex with a synthetic single guide RNA (sgRNA). The complex scans genomic DNA for a 3-nucleotide Protospacer Adjacent Motif (PAM: 5'-NGG-3'). Upon PAM binding, RNA-DNA base pairing unzips the target locus.
                    4. Double-Strand Break (DSB) & Repair Pathways: Cas9 cleaves both DNA strands 3 base pairs upstream of PAM. Cell repair initiates:
                       - Non-Homologous End Joining (NHEJ): Error-prone repair creating insertions/deletions (indels) that knock out gene function via frameshift mutations.
                       - Homology-Directed Repair (HDR): High-fidelity repair utilizing an exogenous donor DNA template to insert precise edits or novel sequences.
                """.trimIndent(),
                takeaways = listOf(
                    "CRISPR-Cas9 is an RNA-guided endonuclease adapted from bacterial anti-phage defense.",
                    "Target specificity requires both guide RNA hybridization and an adjacent PAM motif (5'-NGG-3').",
                    "NHEJ creates gene knockouts through random indels, while HDR enables precise sequence insertion using donor templates."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why is the Protospacer Adjacent Motif (PAM) sequence essential for SpCas9 genome editing?",
                    options = listOf(
                        "Cas9 cannot bind and unwind target DNA without first recognizing the 5'-NGG-3' PAM sequence.",
                        "PAM acts as the template for RNA polymerase transcription.",
                        "PAM prevents Cas9 from cutting any DNA.",
                        "PAM is the amino acid sequence that forms the ribosome."
                    ),
                    correctOptionIndex = 0,
                    explanation = "SpCas9 strictly requires initial physical interaction with the specific PAM motif (5'-NGG-3') to trigger local DNA melting and allow guide RNA strand invasion."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Central Dogma", "The directional biological sequence: DNA -> RNA (transcription) -> Protein (translation).", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Okazaki Fragments", "Short, newly synthesized DNA segments formed on the lagging strand during replication.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Alternative Splicing", "Process allowing a single pre-mRNA to yield multiple protein isoforms by combining different exons.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Histone Acetylation", "Epigenetic modification by HATs that neutralizes positive charges on histones, decondensing chromatin to enable transcription.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Cas9 Endonuclease", "RNA-guided enzyme that introduces targeted double-strand breaks in genomic DNA.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "PAM Motif", "Protospacer Adjacent Motif (5'-NGG-3' for SpCas9) required for target recognition and cleavage.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "During DNA replication, which enzyme synthesizes the initial short RNA sequence required by DNA polymerase?",
                options = listOf(
                    "RNA Primase",
                    "DNA Ligase",
                    "Topoisomerase",
                    "Reverse Transcriptase"
                ),
                correctOptionIndex = 0,
                explanation = "DNA polymerases can only extend an existing 3'-OH group. Primase synthesizes a complementary RNA primer to provide this starting substrate."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "What impact does extensive DNA methylation at promoter CpG islands typically have on gene expression?",
                options = listOf(
                    "Transcriptional repression / gene silencing",
                    "Immediate upregulation of translation",
                    "Duplication of the entire chromosome",
                    "Conversion of DNA into transfer RNA"
                ),
                correctOptionIndex = 0,
                explanation = "Promoter hypermethylation recruits methyl-CpG-binding proteins and histone deacetylases, condensing chromatin and silencing transcription."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 2,
                question = "To achieve targeted knock-in of a specific genetic sequence using CRISPR-Cas9, which cellular DNA repair pathway must be stimulated?",
                options = listOf(
                    "Homology-Directed Repair (HDR) using a donor repair template",
                    "Non-Homologous End Joining (NHEJ)",
                    "Base Excision Repair (BER)",
                    "Mismatch Repair (MMR)"
                ),
                correctOptionIndex = 0,
                explanation = "NHEJ is error-prone and generates knockouts. HDR utilizes homologous sequence arms on an exogenous donor template to achieve precise sequence knock-in."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Molecular biology and genetics: double-helix architecture, transcription and epigenetic regulation, spliceosome dynamics, and CRISPR-Cas9 genome engineering.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.NATURAL_SCIENCES,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    fun createAstrophysicsCourse(): Course {
        val courseId = "course-science-astrophysics"
        val topic = "Astrophysics & Modern Cosmology"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Stellar Physics, Nucleosynthesis & Gravitational Collapse",
                content = """
                    Stars are sustained thermodynamic engines balancing gravitational collapse with nuclear fusion pressure.

                    1. Hydrostatic Equilibrium: A star remains stable when inward gravitational compression is exactly balanced by outward thermal radiation pressure generated by core nuclear fusion: dP/dr = -G * M(r) * ρ(r) / r².
                    2. Nuclear Fusion Cycles: In main sequence stars, hydrogen fuses into helium via the Proton-Proton (p-p) chain (dominant in stars like the Sun) or the CNO cycle (dominant in massive stars at higher core temperatures).
                    3. Post-Main Sequence Evolution: When core hydrogen is exhausted, the core contracts while outer envelopes expand into Red Giants. In stars exceeding 8 solar masses, successive fusion stages synthesize Carbon, Oxygen, Neon, Silicon, culminating in Iron-56.
                    4. The Iron Catastrophe & Supernovae: Iron-56 has the highest binding energy per nucleon; fusing iron is endothermic (consumes energy). Core fusion ceases instantly, triggering catastrophic gravitational collapse within seconds. The shockwave erupts as a Type II Core-Collapse Supernova, seeding the cosmos with heavy elements via the r-process.
                """.trimIndent(),
                takeaways = listOf(
                    "Hydrostatic equilibrium balances gravitational collapse against thermal fusion pressure.",
                    "The proton-proton chain and CNO cycle convert hydrogen into helium.",
                    "Iron-56 fusion is endothermic, terminating nuclear support and triggering core collapse into supernovae."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why does nuclear fusion cease once a massive star's core converts to Iron-56?",
                    options = listOf(
                        "Iron-56 possesses the highest nuclear binding energy per nucleon, making further fusion endothermic (absorbing energy rather than releasing it).",
                        "Iron is a liquid and cannot fuse under pressure.",
                        "Iron nuclei repel gravitational waves.",
                        "Iron absorbs all neutrons instantly, neutralizing core mass."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Because Iron-56 sits at the peak of the nuclear binding energy curve, fusing elements heavier than iron requires net input of energy, removing radiation pressure and initiating collapse."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Compact Objects: White Dwarfs, Neutron Stars & Black Holes",
                content = """
                    Extreme remnants of stellar evolution test fundamental physics at quantum and relativistic thresholds.

                    1. White Dwarfs & Electron Degeneracy: Stellar remnants below the Chandrasekhar Limit (~1.44 solar masses) are supported against gravitational collapse exclusively by electron degeneracy pressure (Pauli Exclusion Principle preventing fermions from occupying identical quantum states).
                    2. Neutron Stars: Above the Chandrasekhar limit, gravitational pressure forces electrons into atomic nuclei (electron capture: p + e⁻ -> n + ν_e), creating a macroscopic sphere of degenerate neutrons. Supported by neutron degeneracy pressure up to the Tolman-Oppenheimer-Volkoff (TOV) limit (~2.17 solar masses). Pulsars are rapidly rotating magnetized neutron stars emitting synchrotron radiation beams.
                    3. Black Holes & General Relativity: Beyond the TOV limit, no known fundamental physical force can resist gravitational collapse. The mass collapses to a central gravitational singularity. The boundary from which even light cannot escape is the Event Horizon, characterized by the Schwarzschild Radius: R_s = 2GM / c².
                """.trimIndent(),
                takeaways = listOf(
                    "White dwarfs are stabilized by electron degeneracy up to the 1.44 solar mass Chandrasekhar limit.",
                    "Neutron stars are dense remnants supported by neutron degeneracy pressure up to ~2.2 solar masses.",
                    "Black holes form beyond the TOV limit, enclosed by an event horizon at Schwarzschild radius R_s = 2GM/c²."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What fundamental quantum mechanical principle prevents white dwarf stars from collapsing under gravity?",
                    options = listOf(
                        "Pauli Exclusion Principle (electron degeneracy pressure)",
                        "Heisenberg Uncertainty Principle for photons",
                        "Bose-Einstein Condensation",
                        "Strong nuclear force repulsion between protons"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Electrons are fermions. By the Pauli Exclusion Principle, two electrons cannot occupy the same quantum state, generating macroscopic degeneracy pressure that supports the star."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "Big Bang Cosmology, CMB & Dark Matter / Energy",
                content = """
                    Cosmology studies the origin, geometry, and ultimate fate of the universe.

                    1. Hubble-Lemaître Law & Spacetime Metric: Edwin Hubble discovered galaxies recede with velocities proportional to their distance: v = H0 * d. This demonstrates that spacetime itself is expanding, modeled mathematically by the Friedmann-Lemaître-Robertson-Walker (FLRW) metric.
                    2. Cosmic Microwave Background (CMB): Discovered by Penzias and Wilson. At recombination (~380,000 years post-Big Bang), temperatures cooled below 3,000 K, allowing electrons to bind to protons (neutral hydrogen). Photons decoupled and free-streamed across the cosmos, redshifted today to a pristine 2.725 K blackbody glow.
                    3. Dark Matter: Gravitational evidence (flat galactic rotation curves discovered by Vera Rubin, gravitational lensing of galaxy clusters, and the Bullet Cluster) indicates ~27% of the universe is non-baryonic dark matter.
                    4. Dark Energy & Cosmic Acceleration: In 1998, Type Ia supernovae observations revealed cosmic expansion is accelerating, driven by dark energy (~68% of total energy density), modeled as Einstein's Cosmological Constant (Λ).
                """.trimIndent(),
                takeaways = listOf(
                    "Hubble's law (v = H0 * d) establishes cosmological spacetime expansion.",
                    "The 2.7 K Cosmic Microwave Background is the relic thermal radiation from photon decoupling.",
                    "The universe is composed of ~5% baryonic matter, ~27% dark matter, and ~68% dark energy."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What observational evidence led to the inference of Dark Matter in galaxies?",
                    options = listOf(
                        "Galactic rotation curves show stars at outer edges orbit at unexpectedly high, constant speeds instead of slowing down.",
                        "Stars at galaxy edges stop rotating and fall into black holes.",
                        "Direct photographic images of dark matter particles.",
                        "The total disappearance of galactic magnetic fields."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Under Newtonian mechanics, stars far from the luminous galactic center should move slower. Observed rotation curves remain flat, proving massive halos of invisible mass (Dark Matter)."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Hydrostatic Equilibrium", "The balance between inward gravitational force and outward thermal radiation pressure in a star.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Chandrasekhar Limit", "~1.44 solar masses; maximum mass a white dwarf can sustain via electron degeneracy before collapse.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Schwarzschild Radius", "R_s = 2GM/c². The physical radius of a non-rotating black hole's event horizon.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Cosmic Microwave Background", "2.725 K relic blackbody radiation from the early universe released during photon decoupling.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Hubble's Law", "v = H0 * d. Galaxies recede with speed proportional to distance due to expanding metric space.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Dark Energy", "Hypothesized energy form causing the accelerating expansion of the universe (~68% of cosmic energy density).", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "What element represents the absolute limit of exothermic fusion in massive stars?",
                options = listOf(
                    "Iron-56 (Fe)",
                    "Helium-4",
                    "Carbon-12",
                    "Uranium-238"
                ),
                correctOptionIndex = 0,
                explanation = "Iron-56 has the highest binding energy per nucleon; any fusion beyond iron requires net energy input, terminating stellar radiation pressure."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "If the mass of a non-rotating black hole is doubled, what happens to its Schwarzschild radius?",
                options = listOf(
                    "It doubles, because R_s = 2GM / c² is directly proportional to mass M",
                    "It quadruples",
                    "It is halved",
                    "It remains unchanged"
                ),
                correctOptionIndex = 0,
                explanation = "The Schwarzschild radius formula R_s = (2G/c²) * M demonstrates strict linear proportionality to mass M. Doubling M doubles R_s."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 2,
                question = "What epoch occurred approximately 380,000 years after the Big Bang when neutral atoms formed and light decoupled?",
                options = listOf(
                    "Recombination",
                    "Inflation",
                    "Baryogenesis",
                    "Nucleosynthesis"
                ),
                correctOptionIndex = 0,
                explanation = "During Recombination, temperature dropped below 3000 K, allowing protons to capture electrons into neutral hydrogen, releasing the Cosmic Microwave Background."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Stellar physics, nucleosynthesis, electron/neutron degeneracy, black hole event horizons, FLRW expanding metric, CMB radiation, and dark energy.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.NATURAL_SCIENCES,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    // ==================== HISTORY & CIVILIZATIONS ====================

    fun createAncientCivilizationsCourse(): Course {
        val courseId = "course-history-ancient"
        val topic = "Ancient Civilizations of the Mediterranean"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Mesopotamia, Sumerian City-States & Legal Codes",
                content = """
                    The fertile crescent birthed sedentary civilization, codified law, and monumental architecture.

                    1. The Tigris-Euphrates River Basin: Early urban centers (Uruk, Ur, Eridu) arose due to intensive agricultural irrigation between the Tigris and Euphrates rivers.
                    2. Cuneiform & Bureaucracy: The invention of cuneiform script on wet clay tablets in ~3400 BCE shifted accounting from tokens to phonetic writing, recording agricultural surpluses, trade contracts, and dynastic annals.
                    3. Ziggurat Architecture: Monumental terraced step pyramids constructed of sun-baked mud brick serving as theocratic hubs combining economic grain storage with religious priesthood governance.
                    4. The Code of Hammurabi (~1750 BCE): King Hammurabi of Babylon established 282 codified laws inscribed on a basalt stele. Known for the principle of talionic justice (lex talionis: 'an eye for an eye'), it formally differentiated legal rights across social classes (nobles, commoners, enslaved persons) and established commercial contracts and consumer protection laws.
                """.trimIndent(),
                takeaways = listOf(
                    "Mesopotamian urbanization was driven by hydraulic irrigation between the Tigris and Euphrates.",
                    "Cuneiform evolved from economic trade accounting to sophisticated literature (Epic of Gilgamesh).",
                    "Hammurabi's Code established standardized written statutory law and liability across civic life."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What was the significance of the Code of Hammurabi in the history of jurisprudence?",
                    options = listOf(
                        "It established public, written statutory laws setting defined penalties and legal precedents across society.",
                        "It abolished all forms of monarchical rule.",
                        "It was the first document to establish universal voting rights.",
                        "It prohibited all commercial contracts and interest charges."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Hammurabi's Code was revolutionary because laws were inscribed publicly on stone, replacing arbitrary ruler whims with codified statutory precedents."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Classical Greece: Athenian Democracy, Philosophy & Sparta",
                content = """
                    Classical Greece pioneered radical political organization, philosophy, and architectural harmony.

                    1. The Polis (City-State): Mountainous Aegean topography favored autonomous city-states (poleis) over centralized empires.
                    2. Athenian Democracy: Under reforms by Cleisthenes (508 BCE) and Pericles, Athens established direct democracy (ekklesia assembly, boule council chosen by sortition, and dicasteries juror courts). Citizens (free adult males) directly debated and voted on policy.
                    3. The Spartan Oligarchy: In contrast, Sparta instituted a dual monarchy and militaristic oligarchy (Gerousia elders council and Ephors) powered by an enslaved agricultural underclass (helots) and the agoge martial training system.
                    4. The Philosophical Revolution: The Socratic method challenged dogmatic assumptions through inductive inquiry. Plato founded the Academy, formulating the Theory of Forms. Aristotle pioneered empirical natural science, formal deductive logic (syllogisms), and political teleology.
                    5. Classical Architecture & Art: Construction of the Parthenon on the Acropolis utilizing entasis (subtle convex curvature of columns to correct optical illusions) and contrapposto sculpting conveying dynamic realism.
                """.trimIndent(),
                takeaways = listOf(
                    "Athens practiced direct democracy with civic participation powered by sortition (lottery selection).",
                    "Sparta developed a dual-king militaristic oligarchy dependent on helot agriculture.",
                    "Greek philosophy transitioned human inquiry from mythopoetic explanations to rational dialectic investigation."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "How were members of the Council of 500 (Boule) predominantly selected in Classical Athens?",
                    options = listOf(
                        "By sortition (random civic lottery) to ensure egalitarian representation without political campaigning.",
                        "By hereditary dynastic succession.",
                        "By wealthy land-owning auctions.",
                        "By appointed decrees from the High Priestess."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Sortition was considered the hallmark of Athenian democracy, preventing corrupt oligarchs from purchasing power by randomly selecting citizens to serve on councils."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "The Roman Republic, Imperial Transition & Engineering",
                content = """
                    Rome forged enduring legal institutions, monumental infrastructure, and imperial hegemony.

                    1. The Roman Republic (509–27 BCE): A mixed constitution combining monarchical (two Consuls), aristocratic (the Senate), and democratic elements (Tribunes of the Plebs with veto power). Governed by the Twelve Tables legal foundation and civic duty (cursus honorum).
                    2. Fall of the Republic: Territorial expansion after the Punic Wars enriched oligarchs, impoverished plebeian legionaries, and enabled ambitious warlords (Marius, Sulla, Julius Caesar). Following Caesar's assassination and civil war, Octavian became Augustus in 27 BCE, founding the Principate.
                    3. Pax Romana & Imperial Governance: Two centuries of relative internal stability. Rome managed provincial empires through administrative autonomy, citizenship incentives, and the Pax Romana legal network (Jus Gentium).
                    4. Master Engineering: Revolutionized construction through Roman Pozzolanic concrete (volcanic ash curing underwater), the semicircular arch, barrel vaults, and cross vaults. Built over 50,000 miles of paved military highways and aqueduct networks transporting fresh water across vast valleys.
                """.trimIndent(),
                takeaways = listOf(
                    "The Roman Republic balanced power across Consuls, the Senate, and Plebeian Tribunes.",
                    "Civil wars and military loyalty shifts transitioned Rome from Republic to Augustus's Principate.",
                    "Roman concrete (pozzolana) and structural arch engineering allowed enduring monumental aqueducts and domes."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What technological material enabled the Romans to construct massive unreinforced domes like the Pantheon?",
                    options = listOf(
                        "Pozzolanic hydraulic concrete incorporating volcanic ash and aggregate grading",
                        "Structural steel beams and tempered glass",
                        "Interlocking dry-stone granite blocks without mortar",
                        "Solid fired bronze casting"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Roman concrete (opus caementicium) made with volcanic ash from Pozzuoli reacted chemically with slaked lime to create ultra-durable hydraulic cement capable of curing underwater and spanning massive dome vaults."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Code of Hammurabi", "Babylonian legal stele of 282 statutory laws formalizing lex talionis and civil liability.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Sortition", "Selection of political officials by random lottery used in Athenian democracy to prevent oligarchic corruption.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Ziggurat", "Monumental terraced step pyramid in Mesopotamian city-states serving theocratic and administrative functions.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Cursus Honorum", "The sequential order of public political offices held by aspiring politicians in the Roman Republic.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Tribune of the Plebs", "Roman magistrate office granted sacrosanctity and the power of 'Veto' to protect plebeians from patrician abuse.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Pozzolanic Concrete", "Durable Roman concrete combining slaked lime and volcanic ash (pozzolana) capable of curing underwater.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "Which rivers defined the geographic heartland of ancient Mesopotamian civilization?",
                options = listOf(
                    "Tigris and Euphrates",
                    "Nile and Jordan",
                    "Indus and Ganges",
                    "Danube and Rhine"
                ),
                correctOptionIndex = 0,
                explanation = "Mesopotamia literally translates to 'land between rivers', referring specifically to the floodplains of the Tigris and Euphrates rivers."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "What architectural technique was used in the Parthenon columns to counteract optical illusions of concavity?",
                options = listOf(
                    "Entasis (subtle convex swelling along the column shaft)",
                    "Internal steel reinforcing bars",
                    "Spiral fluting",
                    "Random staggered base alignments"
                ),
                correctOptionIndex = 0,
                explanation = "Greek architects used entasis—a slight convex curve along the column—so that from a distance the columns appear perfectly straight to human eyes rather than pinched inward."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 2,
                question = "Who became the first official Emperor of Rome following the collapse of the Roman Republic in 27 BCE?",
                options = listOf(
                    "Augustus (Octavian)",
                    "Julius Caesar",
                    "Nero",
                    "Marcus Aurelius"
                ),
                correctOptionIndex = 0,
                explanation = "After defeating Mark Antony and Cleopatra, Octavian took the title 'Augustus' in 27 BCE, inaugurating the Roman Empire and the Pax Romana."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Survey of ancient Mediterranean civilizations: Mesopotamian urbanization and legal codes, Athenian democracy and Greek philosophy, and the Roman Republic's engineering.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.HISTORY_CIVILIZATION,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    fun createIndustrialRevolutionCourse(): Course {
        val courseId = "course-history-industrial"
        val topic = "The Industrial Revolution & Modern World"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Steam Power, Mechanized Textiles & Metallurgy",
                content = """
                    The transition from biological muscle power to fossil-fueled mechanical kinetic energy reshaped civilization.

                    1. The British Genesis (~1760–1840): Favorable conditions converged in Britain: abundant accessible surface coal and iron ore, robust patent laws, maritime trade dominance, surplus agricultural labor from Enclosure Acts, and commercial investment capital.
                    2. James Watt's Steam Engine (1776): Watt's separate condenser improved thermal efficiency over Newcomen's atmospheric engine by a factor of four. Converting reciprocal piston movement into rotary motion liberated factories from waterwheel dependency on riverbanks.
                    3. Textile Mechanization: Inventions such as Kay's flying shuttle, Hargreaves' spinning jenny, Arkwright's water frame, and Crompton's mule multiplied yarn output tenfold. Mechanized textile mills concentrated labor under the factory system.
                    4. The Bessemer Steel Process (1856): Blowing air through molten pig iron oxidized impurities, converting brittle iron into ductile structural steel at 10% of prior crucible costs, inaugurating modern railways, suspension bridges, and skyscraper engineering.
                """.trimIndent(),
                takeaways = listOf(
                    "Fossil-fuel steam power decoupled industrial production from geographic riverbank constraints.",
                    "James Watt's separate condenser engine powered rotary factory machinery and locomotives.",
                    "The Bessemer process mass-produced structural steel for railway networks and civil infrastructure."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What was the crucial mechanical innovation in James Watt's steam engine compared to Newcomen's engine?",
                    options = listOf(
                        "The separate condenser chamber, preventing the main cylinder from cooling and reheating on every stroke.",
                        "The use of nuclear reactor heat exchangers.",
                        "Replacing steam with compressed atmospheric air.",
                        "Eliminating the piston and cylinder entirely."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Newcomen's engine cooled the entire cylinder to condense steam on every cycle, wasting enormous thermal energy. Watt condensed steam in a separate cold vessel while keeping the cylinder hot, multiplying efficiency."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Urbanization, Labor Movements & Social Transformations",
                content = """
                    Industrialization prompted rapid demographic shifts, social stratification, and labor reform.

                    1. Rapid Urban Migration: Millions migrated from agrarian rural villages to industrial manufacturing hubs (Manchester, Birmingham, Pittsburgh). Cities grew without adequate sanitation, producing cholera outbreaks, tenement crowding, and soot pollution.
                    2. The Factory System & Discipline: Clock time replaced solar rhythm. Workers endured 14-to-16 hour workdays, dangerous unshielded machinery, and pervasive child labor in textile mills and coal mine shafts.
                    3. The Rise of Labor Advocacy: Luddite machine wrecking resisted wage degradation. The Chartist movement demanded universal male suffrage and secret ballots. Trade unions formed to negotiate collective bargaining agreements.
                    4. Legislative Interventions: British Factory Acts (1833, 1847) gradually restricted child working hours and mandated basic schooling, giving birth to the modern regulatory welfare state.
                """.trimIndent(),
                takeaways = listOf(
                    "Industrialization caused massive urban concentration and severe public health challenges.",
                    "Factory discipline instituted standardized clock-time schedules replacing agricultural seasonal rhythms.",
                    "Labor advocacy and Factory Acts catalyzed the creation of modern workplace safety regulations."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What was the primary goal of the British Factory Act of 1833?",
                    options = listOf(
                        "To regulate and restrict child labor hours in textile manufacturing and mandate inspectorates.",
                        "To outlaw all mechanized steam looms.",
                        "To nationalize all coal mines.",
                        "To prevent women from entering commercial trade."
                    ),
                    correctOptionIndex = 0,
                    explanation = "The Factory Act of 1833 was landmark legislation restricting daily work hours for children and creating the first independent factory inspectorate to enforce standards."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Separate Condenser", "James Watt's breakthrough keeping steam cylinders continuously hot, multiplying thermodynamic efficiency.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Bessemer Process", "Method for mass-producing steel by blowing air through molten pig iron to oxidize impurities.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Enclosure Acts", "British parliamentary laws fencing common agricultural lands, driving rural laborers into industrial factory cities.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Luddites", "19th-century textile artisans who smashed mechanized industrial looms in protest of wage degradation.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Chartism", "British working-class movement (1838–1848) campaigning for universal male suffrage and secret ballots.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "Which fossil fuel was the indispensable energy driver of the First Industrial Revolution?",
                options = listOf(
                    "Coal",
                    "Petroleum oil",
                    "Natural gas",
                    "Uranium"
                ),
                correctOptionIndex = 0,
                explanation = "Coal replaced depleted charcoal and wood fuel, powering blast furnaces for pig iron smelting and boiling water for steam engines."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "Which early 19th-century British movement campaigned extensively for working-class political franchise and secret ballots?",
                options = listOf(
                    "The Chartists",
                    "The Jacobins",
                    "The Levellers",
                    "The Physiocrats"
                ),
                correctOptionIndex = 0,
                explanation = "The Chartist movement presented the People's Charter of 1838 to Parliament, demanding democratic reforms including universal male suffrage."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Technological, economic, and social consequences of the Industrial Revolution: steam thermodynamics, factory systems, urbanization, and labor law reform.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.HISTORY_CIVILIZATION,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    // ==================== ART & HUMANITIES ====================

    fun createRenaissanceArtCourse(): Course {
        val courseId = "course-art-renaissance"
        val topic = "Renaissance Masters & Visual Perspective"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Linear Perspective & The Optical Revolution",
                content = """
                    The Italian Renaissance reconciled artistic representation with mathematical optics and humanistic philosophy.

                    1. The Shift from Medieval Iconography: Medieval Gothic art used hierarchical scale (figures sized by theological importance rather than physical depth) and flat gold backgrounds. The Renaissance restored naturalism rooted in empirical observation.
                    2. Brunelleschi's Experiment (1415): Filippo Brunelleschi empirically proved linear perspective using a mirror and painted panel of the Florence Baptistery.
                    3. Alberti's 'De Pictura' (1435): Leon Battista Alberti codified the mathematical rules of perspective:
                       - The Horizon Line representing the viewer's eye level.
                       - The Vanishing Point on the horizon where parallel lines (orthogonals) converge.
                       - Transversals: Horizontal lines running parallel to the picture plane, spaced progressively closer to create realistic receding depth.
                    4. Atmospheric (Aerial) Perspective: Introduced by Leonardo da Vinci, recognizing that atmospheric particulates scatter light, rendering distant landscapes hazy, lower in contrast, and distinctly shifted toward cool blue wavelengths.
                """.trimIndent(),
                takeaways = listOf(
                    "Linear perspective translates three-dimensional physical space onto a two-dimensional plane mathematically.",
                    "Alberti codified perspective via horizon line, vanishing point, orthogonals, and transversals.",
                    "Atmospheric perspective mimics particulate scattering by shifting distant horizons into low-contrast blue tones."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In single-point linear perspective, where do parallel orthogonal lines converge?",
                    options = listOf(
                        "At the single vanishing point situated along the horizon line.",
                        "At the upper left corner of the frame.",
                        "Parallel orthogonals never converge in perspective drawing.",
                        "At the center of each individual painted figure."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Linear perspective dictates that all lines perpendicular to the picture plane (orthogonals) converge precisely at the vanishing point located on the viewer's horizon line."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Chiaroscuro, Sfumato & The High Renaissance Masters",
                content = """
                    Mastery of anatomical modeling, light diffusion, and psychological composition defined the High Renaissance.

                    1. Chiaroscuro (Light-Dark): The subtle transition between illuminated surfaces and deep shadows to model three-dimensional volume on a flat surface, replacing hard graphic outlines with volumetric plasticity.
                    2. Sfumato (Smoke / Soft Edges): Leonardo da Vinci perfected this technique of applying micro-thin translucent oil glazes without abrupt borders or harsh lines: 'without lines or borders, in the manner of smoke.' Evident in the enigmatic expression and soft facial contours of the Mona Lisa.
                    3. Michelangelo & Anatomical Heroism: In the Sistine Chapel ceiling and David, Michelangelo combined classical contrapposto with profound dynamic muscular anatomy, expressing psychological tension (terribilità).
                    4. Raphael's Harmony & Balance: In 'The School of Athens' (Apostolic Palace, Vatican), Raphael synthesized mathematical perspective with harmonious multi-figure composition, contrasting Plato's vertical metaphysical gesture with Aristotle's horizontal empirical orientation.
                """.trimIndent(),
                takeaways = listOf(
                    "Chiaroscuro renders three-dimensional mass through modulated gradients of light and shadow.",
                    "Sfumato softens contours with microscopic translucent oil glazes, eliminating harsh borders.",
                    "Raphael's School of Athens exemplifies harmonious compositional balance and intellectual philosophy."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the defining visual characteristic of Leonardo da Vinci's 'sfumato' technique?",
                    options = listOf(
                        "Subtle, hazy blending of tones and soft contours without sharp outlines, evoking smoke.",
                        "Thick impasto knife marks raised off the canvas.",
                        "Using solely gold leaf for background divine halos.",
                        "Drawing sharp, black graphic boundaries around all subjects."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Sfumato (derived from the Italian word for smoke) refers to blending colors and tones so imperceptibly that transitions appear soft and devoid of hard, sharp outlines."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Linear Perspective", "Mathematical system projecting 3D space onto 2D planes via horizon lines and converging orthogonals.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Chiaroscuro", "Artistic technique using strong tonal contrast between light and dark to convey three-dimensional volume.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Sfumato", "Painting technique of imperceptibly soft tonal transitions without harsh boundary lines, perfected by Da Vinci.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Atmospheric Perspective", "Rendering distant scenery cooler, bluer, and less distinct to mimic atmospheric particle scattering.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Contrapposto", "Sculptural pose where body weight rests primarily on one leg, imparting naturalistic counter-poise.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "Who is credited with mathematically proving and demonstrating linear perspective in Florence around 1415?",
                options = listOf(
                    "Filippo Brunelleschi",
                    "Giotto di Bondone",
                    "Caravaggio",
                    "Sandro Botticelli"
                ),
                correctOptionIndex = 0,
                explanation = "Brunelleschi conducted the famous Florence Baptistery mirror demonstration, proving single-point optical perspective mathematically."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "In Raphael's 'The School of Athens', which philosophical contrast is central to the central figures of Plato and Aristotle?",
                options = listOf(
                    "Plato points upward toward the transcendent realm of ideal Forms, while Aristotle gestures downward toward the empirical physical world.",
                    "Plato preaches military conquest while Aristotle advocates ascetic retreat.",
                    "Plato reads from an Egyptian papyrus while Aristotle holds an astrolabe.",
                    "Both philosophers turn away from each other in silence."
                ),
                correctOptionIndex = 0,
                explanation = "Plato holds the Timaeus and points to the heavens representing metaphysical idealism; Aristotle holds the Ethics and gestures palm-down toward the earth, representing empirical scientific realism."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "The Italian Renaissance optical and aesthetic revolution: Brunelleschi's perspective, Alberti's treatise, Da Vinci's sfumato, Michelangelo's anatomy, and Raphael.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.ARTS_HUMANITIES,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    fun createColorTheoryCourse(): Course {
        val courseId = "course-art-colortheory"
        val topic = "Color Theory & Visual Composition"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Color Physics, Harmonies & Contrast Models",
                content = """
                    Color is perceptual sensory information produced by light wavelengths and psychological contrast.

                    1. Additive vs Subtractive Color:
                       - Additive (RGB): Emitted light (screens, monitors). Red, Green, and Blue combine toward pure white light.
                       - Subtractive (CMYK / RYB): Reflected pigment (print, paint). Cyan, Magenta, Yellow absorb light wavelengths, combining toward dark black/brown.
                    2. Color Dimensions:
                       - Hue: The dominant spectral wavelength (Red, Green, Blue, etc.).
                       - Saturation (Chroma): Purity or intensity of the color relative to gray.
                       - Value (Lightness): The relative lightness or darkness from pure black to pure white.
                    3. Johannes Itten's 7 Color Contrasts: Developed at the Bauhaus school:
                       - Contrast of hue, light-dark contrast, cold-warm contrast, complementary contrast, simultaneous contrast, contrast of saturation, and contrast of extension.
                    4. Color Harmonies: Complementary (opposites on wheel creating maximum vibration), Analogous (adjacent colors creating serene unity), Triadic (equidistant triangle providing vibrant balance), and Split-Complementary.
                """.trimIndent(),
                takeaways = listOf(
                    "Additive color (RGB) combines emitted light to white; subtractive color (CMYK) subtracts reflected light toward black.",
                    "Color consists of three independent dimensions: Hue (wavelength), Saturation (chroma), and Value (lightness).",
                    "Complementary colors heighten apparent chromatic intensity through simultaneous contrast."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why do blue and orange placed side-by-side appear more vibrant than blue placed beside violet?",
                    options = listOf(
                        "Blue and orange are complementary colors positioned directly opposite on the color wheel, creating maximum simultaneous chromatic contrast.",
                        "Orange produces ultraviolet wavelengths that stimulate the eye.",
                        "Blue absorbs all light emitted by orange.",
                        "Analogous colors always appear more contrasting than complementary pairs."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Complementary colors on opposite sides of the color wheel create the highest optical vibration and chromatic excitation when juxtaposed."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Gestalt Principles, Grid Systems & Visual Hierarchy",
                content = """
                    Composition directs the viewer's gaze and establishes cognitive clarity.

                    1. Gestalt Psychology in Visual Design:
                       - Proximity: Elements placed close together are perceived as belonging to a collective group.
                       - Similarity: Elements sharing visual characteristics (shape, color, size) are linked conceptually.
                       - Continuity: The human eye instinctively follows lines, curves, and vectors through an image.
                       - Closure: The brain automatically fills in missing visual information to perceive a complete shape.
                       - Figure-Ground: The visual separation between the focal subject (figure) and surrounding field (ground).
                    2. Compositional Frameworks:
                       - Rule of Thirds: Dividing canvas into a 3×3 grid; placing focal points along lines or intersections creates asymmetric dynamic interest.
                       - Golden Ratio (Phi ~ 1.618): Logarithmic spiral providing organic compositional proportion.
                       - Leading Lines: Using architectural diagonals or gaze vectors to guide attention directly to the primary narrative focal point.
                """.trimIndent(),
                takeaways = listOf(
                    "Gestalt principles explain how human perception groups disparate visual stimuli into unified wholes.",
                    "The Rule of Thirds prevents static symmetry by anchoring focal points at grid line intersections.",
                    "Figure-ground relationship creates readable visual depth and focal clarity."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "According to the Gestalt principle of 'Closure', how does the human visual system respond to incomplete shapes?",
                    options = listOf(
                        "The brain automatically fills in missing gaps to perceive a familiar, cohesive whole.",
                        "The eye ignores incomplete shapes entirely.",
                        "The viewer experiences severe optical dizziness.",
                        "Incomplete shapes are always perceived as separate, unrelated dots."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Closure dictates that the human perceptual system seeks completeness, mentally bridging gaps in broken or partial contours to perceive a finished figure."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Additive Color Model", "RGB light model where red, green, and blue combine to produce white light on electronic displays.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Subtractive Color Model", "CMYK pigment model where physical inks absorb wavelengths, combining toward black.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Simultaneous Contrast", "Optical effect where the appearance of a color shifts depending on the surrounding colors.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Rule of Thirds", "Compositional guideline placing key focal points at the intersections of a 3×3 grid.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Gestalt Proximity", "Principle that objects positioned near each other are cognitively perceived as a related group.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "Which three parameters fully specify any perceptual color in the HSV color space?",
                options = listOf(
                    "Hue, Saturation, and Value",
                    "Red, Green, and Black",
                    "Wavelength, Temperature, and Mass",
                    "Luminance, Opacity, and Refraction"
                ),
                correctOptionIndex = 0,
                explanation = "HSV specifies color by Hue (dominant spectral wavelength), Saturation (chromatic purity), and Value (brightness from dark to light)."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "What compositional effect occurs when a primary subject is centered strictly in the dead middle of a canvas without tension?",
                options = listOf(
                    "Static symmetry that can feel formal or monotonous, lacking the dynamic visual energy of the Rule of Thirds",
                    "Immediate optical distortion of colors",
                    "The eye is forced out of the frame",
                    "It automatically creates diagonal leading lines"
                ),
                correctOptionIndex = 0,
                explanation = "Dead-center compositions create static formal balance that lacks the directional eye movement and dynamic engagement created by asymmetric third-line placements."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Optical physics and graphic design fundamentals: additive/subtractive color models, Johannes Itten's contrasts, Gestalt psychology, and grid composition.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.ARTS_HUMANITIES,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    // ==================== COMPUTER SCIENCE ====================

    fun createDistributedSystemsCourse(): Course {
        val courseId = "course-cs-distributed"
        val topic = "Distributed Systems & Consensus Protocols"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Distributed System Models, Faults & The CAP Theorem",
                content = """
                    Distributed systems execute state machines across autonomous network nodes connected by unreliable links.

                    1. Distributed System Realities: Asynchronous networks experience arbitrary packet latency, network partitions, clock drift, and independent node failures.
                    2. Failure Models:
                       - Crash-Stop: A node functions correctly until it stops permanently.
                       - Crash-Recovery: Nodes crash and reboot, recovering persistent log state.
                       - Byzantine Failures: Nodes may act maliciously, lie, or send conflicting messages to different peers.
                    3. The CAP Theorem (Brewer / Gilbert & Lynch): A distributed data store can guarantee at most two of three properties under network partitioning:
                       - Consistency (Linearizability): Every read receives the most recent write or an error.
                       - Availability: Every non-failing node returns a non-error response without guarantee of recent write.
                       - Partition Tolerance: System continues operating despite dropped or delayed network partitions. Because network partitions are inevitable in real hardware, systems must choose between CP and AP.
                    4. Eventual Consistency & PACELC: PACELC extends CAP: If there is a Partition (P), choose Availability (A) or Consistency (C); Else (E), choose Latency (L) or Consistency (C).
                """.trimIndent(),
                takeaways = listOf(
                    "Asynchronous networks suffer packet loss, arbitrary latency, and independent server crashes.",
                    "Under a network partition (P), a distributed system must sacrifice either Consistency (C) or Availability (A).",
                    "PACELC extends CAP by analyzing latency versus consistency trade-offs during normal non-partition operation."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In the CAP theorem, why is 'Partition Tolerance' (P) considered non-negotiable in real-world distributed architectures?",
                    options = listOf(
                        "Because physical network switches, fiber cables, and routers can and will experience transient disconnections that software cannot prevent.",
                        "Because cloud vendors charge extra for partitions.",
                        "Because software algorithms can eliminate all network hardware failures.",
                        "Because CAP only applies when servers are running in the same CPU core."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Physical networks inevitably experience split-brain partitions due to cable cuts or router failures. Systems must be engineered to handle partitions, forcing a choice between CP and AP."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Consensus Protocols: Raft, Paxos & Quorum Slicing",
                content = """
                    Consensus protocols ensure multiple replicas agree on an identical append-only log sequence.

                    1. The Consensus Problem: Replicas must agree on state transitions despite node crashes, out-of-order delivery, and leader changes.
                    2. Quorums & Majorities: To guarantee that any two decisions overlap by at least one node, decisions require agreement from a strict majority quorum: Q = ⌊N/2⌋ + 1. An N-node cluster can tolerate f failures where N = 2f + 1.
                    3. The Raft Consensus Algorithm: Deconstructs consensus into three understandable sub-problems:
                       - Leader Election: Randomized election timeouts prevent split votes. Candidates request votes; nodes grant votes if candidate log is at least as up-to-date as their own.
                       - Log Replication: Leader receives client commands, appends them to local log, and broadcasts AppendEntries RPCs. Once replicated on a majority of nodes, the entry is committed and applied to the state machine.
                       - Safety: Leader completeness guarantees that if an entry is committed in a given term, it will be present in logs of all leaders for all subsequent terms.
                    4. Paxos Overview: Classic Leslie Lamport consensus with Proposers, Acceptors, and Learners utilizing Prepare/Promise and Accept/Accepted phases to prevent conflicting values.
                """.trimIndent(),
                takeaways = listOf(
                    "A cluster of 2f + 1 servers can tolerate f node crashes by requiring majority quorums (⌊N/2⌋ + 1).",
                    "Raft breaks consensus into Leader Election, Log Replication, and Safety Invariants.",
                    "An entry is committed only when safely acknowledged by a strict majority of replicas."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "How many node failures can a 5-node Raft consensus cluster tolerate while remaining operational?",
                    options = listOf(
                        "2 failures, because a majority quorum of 3 nodes (⌊5/2⌋ + 1) is still available to reach consensus.",
                        "4 failures, as long as 1 leader survives.",
                        "3 failures.",
                        "0 failures, all nodes must be operational."
                    ),
                    correctOptionIndex = 0,
                    explanation = "In a cluster of N = 2f + 1 nodes, the system can tolerate f failures. For N = 5, 2f + 1 = 5 gives f = 2. A majority quorum requires at least 3 nodes to function."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "CAP Theorem", "Fundamental theorem proving a distributed store can guarantee at most 2 of Consistency, Availability, Partition tolerance.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Linearizability", "Strong consistency guarantee where all operations appear to execute atomically at a specific point in real time.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Majority Quorum", "⌊N/2⌋ + 1 nodes. Ensures any two quorum sets share at least one overlapping member.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Raft Leader Election", "Process using randomized heartbeat timeouts and RequestVote RPCs to establish a singular cluster coordinator.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Byzantine Fault Tolerance", "System resilience against nodes that fail arbitrarily, send malicious data, or collude against the network.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 0,
                question = "Under a network partition in a CP (Consistency / Partition-tolerant) distributed database, how does the system respond to client write requests in the minority partition?",
                options = listOf(
                    "It rejects or blocks the writes to prevent inconsistent diverging state (split-brain)",
                    "It silently accepts writes and discards them later",
                    "It automatically shuts down the entire global network",
                    "It immediately converts the minority partition into the new leader"
                ),
                correctOptionIndex = 0,
                explanation = "CP databases prioritize strong consistency. Since the minority partition cannot reach a majority quorum, it must reject writes to avoid split-brain inconsistencies."
            ),
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = 1,
                question = "In the Raft consensus protocol, what happens if two candidates start an election simultaneously and split the votes equally?",
                options = listOf(
                    "The election times out, and randomized election timers cause one candidate to time out first and win the subsequent vote",
                    "The cluster crashes permanently",
                    "The candidates both become co-leaders simultaneously",
                    "The lowest IP address automatically takes over without voting"
                ),
                correctOptionIndex = 0,
                explanation = "Raft uses randomized election timeouts (e.g., between 150ms and 300ms) to ensure split-vote ties are resolved quickly on the next cycle."
            )
        )

        return Course(
            id = courseId,
            title = topic,
            topic = topic,
            description = "Architectural principles of distributed systems: network failure models, CAP theorem, linearizable consistency, Raft leader election and log replication, and Paxos.",
            tier = ScopeTier.MEDIUM,
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE,
            duration = CourseDuration.STANDARD,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = EducationalCategory.COMPUTER_SCIENCE,
            certification = FreeCertificationsData.findCertificationForTopic(topic, topic)
        )
    }

    fun getAllAcademicStarterCourses(): List<Course> {
        return listOf(
            createLinearAlgebraCourse(),
            createCalculusCourse(),
            createMolecularGeneticsCourse(),
            createAstrophysicsCourse(),
            createAncientCivilizationsCourse(),
            createIndustrialRevolutionCourse(),
            createRenaissanceArtCourse(),
            createColorTheoryCourse(),
            createDistributedSystemsCourse()
        )
    }
}
