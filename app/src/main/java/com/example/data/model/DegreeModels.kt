package com.example.data.model

enum class DegreeLevel(
    val title: String,
    val credentialLabel: String,
    val defaultCredits: Int,
    val colorHex: Long
) {
    ASSOCIATE(
        title = "Associate Degree",
        credentialLabel = "Associate of Science (A.S.)",
        defaultCredits = 60,
        colorHex = 0xFF0288D1
    ),
    BACHELOR(
        title = "Bachelor of Science",
        credentialLabel = "Bachelor of Science (B.S.)",
        defaultCredits = 120,
        colorHex = 0xFF2E7D32
    ),
    BACHELOR_ARTS(
        title = "Bachelor of Arts",
        credentialLabel = "Bachelor of Arts (B.A.)",
        defaultCredits = 120,
        colorHex = 0xFFC2185B
    ),
    MASTER(
        title = "Master of Science",
        credentialLabel = "Master of Science (M.S.)",
        defaultCredits = 36,
        colorHex = 0xFF6A1B9A
    ),
    PROFESSIONAL_DIPLOMA(
        title = "Professional Vocational Diploma",
        credentialLabel = "Master Tradesman & Tech Diploma",
        defaultCredits = 96,
        colorHex = 0xFFD84315
    )
}

data class DegreeAcademicYear(
    val yearName: String,
    val semesterCredits: Int = 30,
    val courses: List<String>
)

data class DegreeProgram(
    val id: String,
    val title: String,
    val credentialName: String,
    val department: String,
    val level: DegreeLevel,
    val iconEmoji: String,
    val description: String,
    val requiredCourseTitles: List<String>,
    val passingScoreThreshold: Int = 70, // Minimum 70% on final exam
    val honorsScoreThreshold: Int = 90,  // 90%+ qualifies for Summa Cum Laude
    val totalCredits: Int = 120,
    val academicYears: List<DegreeAcademicYear> = emptyList()
)

data class DegreeCourseStatus(
    val requiredTitle: String,
    val matchedCourse: Course?,
    val isCompleted: Boolean,
    val bestScore: Int?,
    val isPassed: Boolean // bestScore != null && bestScore >= 70
)

data class DegreeProgress(
    val program: DegreeProgram,
    val courseStatuses: List<DegreeCourseStatus>,
    val completedCount: Int,
    val totalCount: Int,
    val isConferred: Boolean,
    val averageScore: Int?,
    val honorsDesignation: String?
) {
    val progressPercentage: Float
        get() = if (totalCount == 0) 0f else (completedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f)

    fun getStatusesForYear(year: DegreeAcademicYear): List<DegreeCourseStatus> {
        val titleSet = year.courses.toSet()
        return courseStatuses.filter { it.requiredTitle in titleSet }
    }
}

object DegreeRegistry {

    private val csYears = listOf(
        DegreeAcademicYear(
            yearName = "Year 1: Freshman Foundations & Core STEM",
            semesterCredits = 30,
            courses = listOf(
                "CS 101: Introduction to Computer Science & Algorithmic Problem Solving",
                "CS 102: Object-Oriented Programming & Principles",
                "MATH 151: Single-Variable Calculus I",
                "MATH 152: Single-Variable Calculus II",
                "CS 110: Discrete Mathematics & Propositional Logic",
                "PHYS 201: University Physics I: Classical Mechanics",
                "ENGL 101: Rhetoric, Academic Writing & Scientific Communication",
                "PHIL 105: Engineering Ethics, Privacy & Digital Rights",
                "HIST 111: History of Science, Technology & Computing",
                "COMM 104: Technical Public Speaking & Collaboration"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 2: Sophomore Systems & Theory",
            semesterCredits = 30,
            courses = listOf(
                "CS 201: Computer Systems Architecture & Assembly Language",
                "CS 210: Data Structures & Advanced Algorithmic Complexity",
                "CS 220: Theory of Computation & Formal Automata",
                "CS 230: Systems Programming in C/C++ & Rust",
                "CS 240: Web Systems & Mobile Application Engineering",
                "MATH 253: Multivariable Calculus for Engineers",
                "MATH 270: Linear Algebra & Vector Spaces",
                "STAT 280: Probability & Statistical Inference",
                "PHYS 202: University Physics II: Electromagnetism & Circuits",
                "ECON 102: Engineering Economics & Technological Innovation"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 3: Junior Specializations & Core Systems",
            semesterCredits = 30,
            courses = listOf(
                "CS 301: Operating Systems Internals, Kernels & Concurrency",
                "CS 310: Database Management Systems & Relational Theory",
                "CS 320: Modern Android Architecture with Compose",
                "CS 330: Computer Networks & Network Protocols",
                "CS 340: Software Engineering Design Patterns & Architecture",
                "CS 350: Distributed Systems & Consensus Protocols",
                "CS 360: Compilers & Programming Language Theory",
                "CS 370: Artificial Intelligence & Search Heuristics",
                "CS 380: Cryptography & Cybersecurity Foundations",
                "CS 390: Cloud Computing Infrastructure & DevOps"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 4: Senior Advanced Topics & Capstone Thesis",
            semesterCredits = 30,
            courses = listOf(
                "CS 410: Deep Learning & Transformer Architectures",
                "CS 420: Parallel & GPU Computing with CUDA",
                "CS 430: Computer Vision & Convolutional Neural Networks",
                "CS 440: Natural Language Processing & Large Language Models",
                "CS 450: Fault-Tolerant Distributed Storage (Raft/Paxos)",
                "CS 460: Quantum Computing Principles & Algorithms",
                "CS 470: Human-Computer Interaction & Cognitive Ergonomics",
                "CS 480: Advanced Information Retrieval & Vector Databases",
                "CS 498: Senior Capstone Design I: System Specification",
                "CS 499: Senior Capstone Design II: Production Implementation & Defense"
            )
        )
    )

    private val mathYears = listOf(
        DegreeAcademicYear(
            yearName = "Year 1: Freshman Calculus & Foundations",
            semesterCredits = 30,
            courses = listOf(
                "MATH 151: Differential & Integral Calculus I",
                "MATH 152: Differential & Integral Calculus II",
                "MATH 160: Introduction to Mathematical Proofs & Logic",
                "MATH 170: Linear Algebra & Vector Spaces",
                "CS 105: Scientific Programming in Python & Julia",
                "PHYS 201: University Physics I: Mechanics & Waves",
                "ENGL 101: Rhetoric & Mathematical Exposition",
                "PHIL 102: Philosophy of Science & Formal Epistemology",
                "CHEM 101: General Chemistry I for Physical Sciences",
                "HIST 108: History of Mathematics from Antiquity to Modernity"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 2: Sophomore Analysis & Differential Equations",
            semesterCredits = 30,
            courses = listOf(
                "MATH 253: Multivariable & Vector Calculus",
                "MATH 260: Ordinary Differential Equations & Dynamical Systems",
                "MATH 275: Matrix Computations & Numerical Linear Algebra",
                "MATH 280: Probability Theory & Discrete Probability Models",
                "MATH 290: Elementary Number Theory & Cryptographic Algorithms",
                "CS 215: Algorithms & Combinatorial Optimization",
                "PHYS 202: University Physics II: Fields & Waves",
                "STAT 285: Mathematical Statistics & Estimation Theory",
                "ECON 201: Mathematical Microeconomics",
                "MATH 295: Discrete Mathematics & Graph Theory"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 3: Junior Advanced Pure & Applied Mathematics",
            semesterCredits = 30,
            courses = listOf(
                "MATH 301: Real Analysis I: Topology & Metric Spaces",
                "MATH 302: Real Analysis II: Measure Theory & Integration",
                "MATH 311: Abstract Algebra I: Group Theory & Symmetry",
                "MATH 312: Abstract Algebra II: Rings, Fields & Ideals",
                "MATH 320: Complex Analysis & Conformal Mappings",
                "MATH 330: Partial Differential Equations & Fourier Analysis",
                "MATH 340: Numerical Analysis & Scientific Computing",
                "STAT 350: Stochastic Processes & Markov Chains",
                "MATH 360: Calculus of Variations & Optimal Control",
                "MATH 370: Mathematical Modeling in Biology & Physics"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 4: Senior Advanced Analysis & Research Capstone",
            semesterCredits = 30,
            courses = listOf(
                "MATH 401: Functional Analysis & Hilbert Spaces",
                "MATH 410: Differential Geometry & Tensor Calculus",
                "MATH 420: Algebraic Topology & Homology",
                "MATH 430: High-Performance Numerical Simulations",
                "MATH 440: Nonlinear Dynamics & Chaos Theory",
                "STAT 450: Bayesian Data Analysis & Monte Carlo Methods",
                "MATH 460: Financial Mathematics & Derivative Pricing",
                "MATH 470: Combinatorics & Ramsey Theory",
                "MATH 498: Senior Mathematics Colloquium I: Literature Review",
                "MATH 499: Senior Mathematics Thesis II: Capstone Defense"
            )
        )
    )

    private val scienceYears = listOf(
        DegreeAcademicYear(
            yearName = "Year 1: Freshman Biological & Physical Principles",
            semesterCredits = 30,
            courses = listOf(
                "CHEM 101: Principles of Chemistry I",
                "CHEM 102: Principles of Chemistry II",
                "BIOL 101: General Biology I: Cell & Molecular Mechanisms",
                "BIOL 102: General Biology II: Organismal Diversity & Ecology",
                "MATH 151: Calculus for Life & Physical Sciences I",
                "MATH 152: Calculus for Life & Physical Sciences II",
                "PHYS 111: General Physics I: Classical Mechanics",
                "ENGL 101: Technical Writing & Scientific Reporting",
                "PHIL 107: Bioethics & Research Integrity",
                "GEOL 101: Earth Systems & Planetary Geology"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 2: Sophomore Organic Chemistry & Genetics",
            semesterCredits = 30,
            courses = listOf(
                "CHEM 211: Organic Chemistry I: Structure & Stereochemistry",
                "CHEM 212: Organic Chemistry II: Organic Synthesis & Mechanisms",
                "BIOL 210: Genetics: Mendelian Inheritance & Genomics",
                "PHYS 112: General Physics II: Electromagnetism & Optics",
                "MATH 253: Multivariable Calculus for Physical Sciences",
                "STAT 250: Biostatistics & Experimental Design",
                "CHEM 220: Analytical Chemistry & Quantitative Instrumentation",
                "BIOL 230: Cell Biology & Intracellular Signal Transduction",
                "ASTR 201: Introduction to Astronomy & the Solar System",
                "ENVR 201: Environmental Chemistry & Atmospheric Science"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 3: Junior Biochemistry & Modern Physics",
            semesterCredits = 30,
            courses = listOf(
                "BIOL 301: Molecular Genetics & CRISPR Technology",
                "CHEM 310: Biochemistry I: Protein Structure & Enzymology",
                "CHEM 312: Biochemistry II: Metabolic Pathways & Regulation",
                "PHYS 301: Modern Physics & Quantum Phenomena",
                "ASTR 310: Astrophysics & Modern Cosmology",
                "CHEM 320: Physical Chemistry I: Chemical Thermodynamics",
                "CHEM 322: Physical Chemistry II: Quantum Chemistry & Spectroscopy",
                "BIOL 340: Microbiology & Microbial Physiology",
                "BIOL 350: Immunology & Pathogen Interactions",
                "BIOL 360: Molecular Biology Techniques & Gene Editing Labs"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 4: Senior Advanced Genomics, Cosmology & Thesis",
            semesterCredits = 30,
            courses = listOf(
                "ASTR 410: Stellar Evolution, Black Holes & Gravitational Waves",
                "BIOL 420: Functional Genomics & Bioinformatics Algorithms",
                "BIOL 430: Developmental Biology & Stem Cell Engineering",
                "CHEM 440: Advanced Organic Synthesis & Reaction Kinetics",
                "PHYS 410: Nuclear & Particle Physics Principles",
                "BIOL 450: Pharmacology, Drug Design & Toxicology",
                "CHEM 460: Instrumental Analysis (NMR, Mass Spectrometry, FTIR)",
                "ASTR 480: Observational Astrophysics & Astronomical Data Processing",
                "SCIE 498: Senior Scientific Capstone I: Research Proposal",
                "SCIE 499: Senior Scientific Capstone II: Experimental Defense"
            )
        )
    )

    private val historyYears = listOf(
        DegreeAcademicYear(
            yearName = "Year 1: Freshman Ancient Civilizations & Methodology",
            semesterCredits = 30,
            courses = listOf(
                "HIST 101: Ancient Civilizations of the Mediterranean",
                "HIST 102: Western Civilization: Antiquity to the Renaissance",
                "HIST 103: East Asian Civilizations & Dynastic Empires",
                "HIST 104: Foundations of Islamic Civilizations & Golden Age",
                "HIST 105: Historical Methods & Primary Source Analysis",
                "ENGL 101: Rhetorical Writing & Historiographical Inquiry",
                "ANTH 101: Introduction to Cultural Anthropology & Archaeology",
                "PHIL 101: Ancient Philosophy: Plato, Aristotle & Stoics",
                "GEOG 101: World Regional Geography & Geopolitics",
                "POLS 101: Introduction to Comparative Political Systems"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 2: Sophomore Medieval, Renaissance & Global Trade",
            semesterCredits = 30,
            courses = listOf(
                "HIST 201: Medieval Europe: Monasticism, Feudalism & Crusades",
                "HIST 202: Renaissance & Reformation in European History",
                "HIST 203: The Age of Exploration & Maritime Empires",
                "HIST 204: Pre-Columbian Civilizations: Maya, Aztec & Inca",
                "HIST 205: Sub-Saharan African Civilizations & Trade Kingdoms",
                "HIST 206: Ottoman Empire & Eastern Mediterranean History",
                "HIST 207: Colonial Americas & the Atlantic Slave Trade",
                "HIST 208: Scientific Revolution & Enlightenment Thought",
                "POLS 202: Classical & Modern Political Philosophy",
                "SOC 201: Sociology of Revolutions & Social Movements"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 3: Junior Industrialization, Geopolitics & World Wars",
            semesterCredits = 30,
            courses = listOf(
                "HIST 301: The Industrial Revolution & the Modern World",
                "HIST 302: 19th Century Nationalism & European Imperialism",
                "HIST 303: World War I & the Collapse of Global Empires",
                "HIST 304: Russian Revolution & the Soviet Union",
                "HIST 305: World War II & the Holocaust in Global Context",
                "HIST 306: The Cold War & Global Geopolitics",
                "HIST 307: Decolonization & Post-Colonial Nation Building",
                "HIST 308: Modern Middle East: State Formation & Conflicts",
                "HIST 309: Modern China: Qing Dynasty to Contemporary Era",
                "HIST 310: Latin American History in the 20th Century"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 4: Senior Historiography & Honors Thesis",
            semesterCredits = 30,
            courses = listOf(
                "HIST 401: Historiography: Philosophies of Historical Interpretation",
                "HIST 402: History of Human Rights & International Law",
                "HIST 403: Digital History: Archival Curation & Quantitative Methods",
                "HIST 404: Oral History & Memory Studies",
                "HIST 405: Comparative Empires & Global Trade Networks",
                "HIST 406: History of Economic Globalization & Trade Policy",
                "HIST 407: Environmental History: Climate, Ecology & Civilizations",
                "HIST 408: Advanced Research Seminar: Archival Investigations",
                "HIST 498: Senior Honors Colloquium I: Thesis Prospectus",
                "HIST 499: Senior Honors Thesis II: Archival Defense"
            )
        )
    )

    private val artYears = listOf(
        DegreeAcademicYear(
            yearName = "Year 1: Freshman Visual Culture & Studio Foundations",
            semesterCredits = 30,
            courses = listOf(
                "ART 101: Survey of World Art I: Prehistory to the Gothic Age",
                "ART 102: Survey of World Art II: Renaissance to the Modern Era",
                "ART 103: Foundations of Drawing, Perspective & Form",
                "ART 104: Color Theory & Visual Composition",
                "ART 105: Visual Literacy: Semiotics & Image Interpretation",
                "ENGL 101: Critical Writing & Visual Culture Analysis",
                "PHIL 108: Aesthetics & the Philosophy of Art",
                "HIST 102: Cultural History of the Western Tradition",
                "ANTH 105: Art, Ritual & Anthropology of Material Culture",
                "DSGN 101: Design Principles & Graphic Communication"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 2: Sophomore Classical, Renaissance & Global Traditions",
            semesterCredits = 30,
            courses = listOf(
                "ART 201: Renaissance Masters & Visual Perspective",
                "ART 202: Northern Renaissance & Printmaking Revolution",
                "ART 203: Baroque & Rococo: Drama, Light & Illusion",
                "ART 204: Asian Art & Visual Aesthetics: China, Japan & India",
                "ART 205: Islamic Art, Geometry & Architectural Calligraphy",
                "ART 206: Indigenous Arts of the Americas & Oceania",
                "ART 207: History of Architecture: From Antiquity to Iron",
                "ART 208: History of Photography as Fine Art",
                "ART 209: Museum Studies: History & Ethics of Collections",
                "SOC 205: Sociology of Art & Creative Communities"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 3: Junior Modernism, Avant-Garde & Theory",
            semesterCredits = 30,
            courses = listOf(
                "ART 301: 19th Century Art: Romanticism, Realism & Impressionism",
                "ART 302: Post-Impressionism, Fauvism & Early Avant-Garde",
                "ART 303: Cubism, Futurism & the Deconstruction of Space",
                "ART 304: Dada, Surrealism & the Unconscious Mind",
                "ART 305: Bauhaus, Constructivism & Modern Architecture",
                "ART 306: Abstract Expressionism & the New York School",
                "ART 307: Pop Art, Minimalism & Conceptual Art",
                "ART 308: Feminist & Post-Colonial Art Movements",
                "ART 309: Modern & Contemporary Art Movements",
                "ART 310: Curatorial Practice & Exhibition Planning"
            )
        ),
        DegreeAcademicYear(
            yearName = "Year 4: Senior Contemporary Art & Monograph Thesis",
            semesterCredits = 30,
            courses = listOf(
                "ART 401: Contemporary Global Art & International Biennales",
                "ART 402: Installation, Performance & Time-Based Media",
                "ART 403: Art Market Economics, Provenance & Appraisals",
                "ART 404: Conservation Science & Preservation of Cultural Heritage",
                "ART 405: Critical Theory & Contemporary Art Criticism",
                "ART 406: Digital Art, Virtual Realities & Algorithmic Media",
                "ART 407: Public Art, Monuments & Urban Aesthetics",
                "ART 408: Advanced Curatorial Seminar: Exhibition Staging",
                "ART 498: Senior Art History Seminar I: Monograph Proposal",
                "ART 499: Senior Art History Monograph II: Public Defense"
            )
        )
    )

    private val tradesYears = listOf(
        DegreeAcademicYear(
            yearName = "Level 1: Industrial Safety, Measurement & Fasteners",
            semesterCredits = 24,
            courses = listOf(
                "IND 101: Industrial Safety Standards, OSHA 30 & Hazardous Communication",
                "IND 102: Precision Measurement Instruments, Calipers & Micrometers",
                "IND 103: Technical Blueprint Reading & Industrial Drafting Schematics",
                "IND 104: Industrial Fasteners, Thread Standards & Torque Specifications",
                "IND 105: Rigging Principles, Slings, Shackles & Crane Signaling",
                "IND 106: Applied Trade Mathematics: Geometry & Shop Trigonometry",
                "IND 107: Metallurgy Foundations & Material Properties",
                "IND 108: Emergency First Aid, CPR & Life Support"
            )
        ),
        DegreeAcademicYear(
            yearName = "Level 2: Mechanical Machinery & Motor Controls",
            semesterCredits = 24,
            courses = listOf(
                "IND 201: Industrial Machinery & Mechanical Systems: Millwright",
                "IND 202: Power Transmission: Belts, Chains, Couplings & Gearboxes",
                "IND 203: Bearing Technology: Lubrication, Seals & Mounting",
                "IND 204: Industrial Electrical Distribution & Motor Controls",
                "IND 205: Single-Phase & Three-Phase AC Power Circuits",
                "IND 206: Industrial Fluid Power: Hydraulics Principles & Circuits",
                "IND 207: Industrial Pneumatics: Air Preparation & Actuators",
                "IND 208: Shielded Metal Arc Welding (SMAW / Stick) Operations"
            )
        ),
        DegreeAcademicYear(
            yearName = "Level 3: Thermodynamics, Automation & Welding",
            semesterCredits = 24,
            courses = listOf(
                "IND 301: HVAC/R Thermodynamics & Refrigerant Systems",
                "IND 302: EPA Section 608 Universal Refrigerant Certification",
                "IND 303: Commercial Refrigeration Cycles & Defrost Controls",
                "IND 304: Air Conditioning Psychrometrics & Duct Design",
                "IND 305: Gas Metal Arc (GMAW / MIG) & Flux-Cored Welding",
                "IND 306: Gas Tungsten Arc Welding (GTAW / TIG) on Pipe & Plate",
                "IND 307: Welding Metallurgy, Joint Design & AWS Standards",
                "IND 308: Programmable Logic Controllers (PLC) & Ladder Logic"
            )
        ),
        DegreeAcademicYear(
            yearName = "Level 4: Master Operations, Diagnostics & Commissioning",
            semesterCredits = 24,
            courses = listOf(
                "IND 401: Vibration Analysis & Dynamic Machine Balancing",
                "IND 402: Precision Laser Shaft Alignment & Thermal Growth",
                "IND 403: Centrifugal Pump Overhaul & Mechanical Seal Maintenance",
                "IND 404: Industrial Compressors, Blowers & Vacuum Systems",
                "IND 405: National Electrical Code (NEC) Industrial Compliance",
                "IND 406: Automated Robotic Workcells & End-Effector Systems",
                "IND 407: Predictive Maintenance (PdM) & Infrared Thermography",
                "IND 408: Master Tradesman Capstone Inspection & Plant Commissioning"
            )
        )
    )

    val allDegrees: List<DegreeProgram> = listOf(
        DegreeProgram(
            id = "degree-cs",
            title = "Computer Science & Distributed Systems",
            credentialName = "Bachelor of Science in Computer Science & Systems",
            department = "Faculty of Computing, Software & Information Systems",
            level = DegreeLevel.BACHELOR,
            iconEmoji = "💻",
            description = "Rigorous 40-course university curriculum covering compiler theory, distributed fault tolerance, transformer architectures, and modern Android mobile engineering.",
            requiredCourseTitles = csYears.flatMap { it.courses },
            academicYears = csYears,
            totalCredits = 120
        ),
        DegreeProgram(
            id = "degree-math",
            title = "Applied Mathematics & Mathematical Computation",
            credentialName = "Bachelor of Science in Applied Mathematics",
            department = "School of Mathematical Sciences & Quantitative Analysis",
            level = DegreeLevel.BACHELOR,
            iconEmoji = "📐",
            description = "Comprehensive 40-course university curriculum in analytical vector spaces, multivariable differential equations, linear transformations, and probabilistic statistical inference.",
            requiredCourseTitles = mathYears.flatMap { it.courses },
            academicYears = mathYears,
            totalCredits = 120
        ),
        DegreeProgram(
            id = "degree-science",
            title = "Natural & Physical Sciences",
            credentialName = "Bachelor of Science in Natural & Physical Sciences",
            department = "College of Biological, Physical & Chemical Sciences",
            level = DegreeLevel.BACHELOR,
            iconEmoji = "🔬",
            description = "Full 40-course collegiate physical science curriculum encompassing CRISPR molecular genetics, astrophysics cosmology, and organic reaction mechanisms.",
            requiredCourseTitles = scienceYears.flatMap { it.courses },
            academicYears = scienceYears,
            totalCredits = 120
        ),
        DegreeProgram(
            id = "degree-history",
            title = "World History & Global Civilizations",
            credentialName = "Bachelor of Arts in World History",
            department = "Department of Historical Inquiry & International Civilization",
            level = DegreeLevel.BACHELOR_ARTS,
            iconEmoji = "🏛️",
            description = "Comprehensive 40-course collegiate curriculum spanning ancient Mediterranean legal codes, industrial mechanization, and Cold War geopolitics.",
            requiredCourseTitles = historyYears.flatMap { it.courses },
            academicYears = historyYears,
            totalCredits = 120
        ),
        DegreeProgram(
            id = "degree-art",
            title = "Art History & Visual Aesthetics",
            credentialName = "Bachelor of Arts in Art History & Visual Culture",
            department = "School of Fine Arts, Architecture & Visual Communication",
            level = DegreeLevel.BACHELOR_ARTS,
            iconEmoji = "🎨",
            description = "Rigorous 40-course visual curriculum exploring Renaissance perspective, color psychology and harmony, and 20th century modern art movements.",
            requiredCourseTitles = artYears.flatMap { it.courses },
            academicYears = artYears,
            totalCredits = 120
        ),
        DegreeProgram(
            id = "degree-trades",
            title = "Master of Industrial Engineering & Skilled Trades",
            credentialName = "Master Tradesman & Industrial Engineering Diploma",
            department = "Institute of Advanced Industrial Technology & Safety",
            level = DegreeLevel.PROFESSIONAL_DIPLOMA,
            iconEmoji = "🛠️",
            description = "Exhaustive 32-course vocational curriculum unifying industrial machinery, HVAC thermodynamics, motor control circuits, welding metallurgy, and life-support safety.",
            requiredCourseTitles = tradesYears.flatMap { it.courses },
            academicYears = tradesYears,
            totalCredits = 96
        )
    )

    fun calculateDegreeProgress(
        program: DegreeProgram,
        allUserCourses: List<Course>
    ): DegreeProgress {
        val courseStatuses = program.requiredCourseTitles.map { reqTitle ->
            val matched = allUserCourses.find { userCourse ->
                userCourse.title.contains(reqTitle, ignoreCase = true) ||
                        userCourse.topic.contains(reqTitle, ignoreCase = true) ||
                        reqTitle.contains(userCourse.topic, ignoreCase = true) ||
                        reqTitle.contains(userCourse.title, ignoreCase = true)
            }
            val bestScore = matched?.bestScore
            val isPassed = bestScore != null && bestScore >= program.passingScoreThreshold
            DegreeCourseStatus(
                requiredTitle = reqTitle,
                matchedCourse = matched,
                isCompleted = matched?.isCompleted == true || isPassed,
                bestScore = bestScore,
                isPassed = isPassed
            )
        }

        val passedCount = courseStatuses.count { it.isPassed }
        val totalCount = program.requiredCourseTitles.size
        val isConferred = passedCount == totalCount && totalCount > 0

        val scores = courseStatuses.mapNotNull { it.bestScore }
        val averageScore = if (scores.isNotEmpty()) scores.average().toInt() else null

        val honorsDesignation = when {
            !isConferred -> null
            averageScore != null && averageScore >= program.honorsScoreThreshold -> "Summa Cum Laude (Highest Academic Honors)"
            averageScore != null && averageScore >= 82 -> "Magna Cum Laude (High Academic Honors)"
            else -> "Cum Laude (Academic Distinction)"
        }

        return DegreeProgress(
            program = program,
            courseStatuses = courseStatuses,
            completedCount = passedCount,
            totalCount = totalCount,
            isConferred = isConferred,
            averageScore = averageScore,
            honorsDesignation = honorsDesignation
        )
    }

    fun calculateAllDegreeProgresses(allUserCourses: List<Course>): List<DegreeProgress> {
        return allDegrees.map { calculateDegreeProgress(it, allUserCourses) }
    }
}
