package com.example.data.model

data class FreeCertification(
    val id: String,
    val title: String,
    val shortName: String,
    val issuingOrganization: String,
    val category: EducationalCategory,
    val credentialType: String,
    val realWorldValue: String,
    val freeAccessProvider: String,
    val examFormat: String,
    val officialUrl: String,
    val keySkillsCovered: List<String>,
    val matchingKeywords: List<String>
)

object FreeCertificationsData {

    val allCertifications: List<FreeCertification> = listOf(
        // === COMPUTER SCIENCE ===
        FreeCertification(
            id = "cs50-harvard",
            title = "CS50's Introduction to Computer Science",
            shortName = "Harvard CS50x Certificate",
            issuingOrganization = "Harvard University / HarvardX",
            category = EducationalCategory.COMPUTER_SCIENCE,
            credentialType = "Verifiable Academic Certificate of Completion",
            realWorldValue = "World-renowned credential recognized by tech employers globally for foundational rigor in C, algorithms, memory management, data structures, and Python.",
            freeAccessProvider = "Harvard CS50 Portal & edX Free Audit (Free verified CS50 certificate upon completing problem sets)",
            examFormat = "10 programming problem sets and comprehensive final capstone project",
            officialUrl = "https://cs50.harvard.edu/x",
            keySkillsCovered = listOf(
                "Computational thinking & algorithmic asymptotic complexity (Big-O)",
                "C pointers, memory layout, dynamic allocation (malloc/free)",
                "Data structures: linked lists, hash tables, tries, binary trees",
                "Full-stack software engineering with Python, SQL, and Flask"
            ),
            matchingKeywords = listOf("computer science", "cs50", "algorithms", "c programming", "data structures", "software")
        ),
        FreeCertification(
            id = "fcc-python",
            title = "Scientific Computing with Python Certification",
            shortName = "freeCodeCamp Python Cert",
            issuingOrganization = "freeCodeCamp",
            category = EducationalCategory.COMPUTER_SCIENCE,
            credentialType = "Verifiable Digital Industry Credential",
            realWorldValue = "300 hours of coursework recognized worldwide by engineering leaders as proof of algorithmic proficiency and scientific data analysis.",
            freeAccessProvider = "freeCodeCamp.org (100% Free digital verifiable certification)",
            examFormat = "5 verified algorithmic project submissions passing automated test suites",
            officialUrl = "https://www.freecodecamp.org/learn/scientific-computing-with-python/",
            keySkillsCovered = listOf(
                "Algorithmic problem solving, recursion & dynamic programming",
                "Object-oriented programming, class invariants & design patterns",
                "Data processing, file I/O & numerical analysis",
                "Automated unit testing & unit test assertions"
            ),
            matchingKeywords = listOf("python", "scientific computing", "deep learning", "neural", "compiler", "distributed")
        ),
        FreeCertification(
            id = "kaggle-deep-learning",
            title = "Deep Learning & Transformer Models Specialization",
            shortName = "Kaggle DL & Transformers",
            issuingOrganization = "Google / Kaggle",
            category = EducationalCategory.COMPUTER_SCIENCE,
            credentialType = "Digital Practical Micro-Credential",
            realWorldValue = "Hands-on proof of implementing convolutional neural nets, attention mechanisms, transfer learning, and production PyTorch/TensorFlow pipelines.",
            freeAccessProvider = "Kaggle Learn (100% Free interactive GPU exercises and certificates)",
            examFormat = "GPU notebook code execution & practical evaluation exercises",
            officialUrl = "https://www.kaggle.com/learn",
            keySkillsCovered = listOf(
                "Multi-head self-attention mechanisms & encoder-decoder blocks",
                "Batch normalization, dropout & gradient descent optimization",
                "Computer vision transfer learning & fine-tuning pretrained backbones",
                "Evaluation metrics: BLEU, Perplexity, ROC-AUC, Precision-Recall"
            ),
            matchingKeywords = listOf("deep learning", "neural network", "transformer", "machine learning", "ai", "artificial intelligence")
        ),
        FreeCertification(
            id = "linux-foundation",
            title = "Linux Foundation Introduction to Linux (LFS101x)",
            shortName = "Linux Foundation LFS101",
            issuingOrganization = "The Linux Foundation",
            category = EducationalCategory.COMPUTER_SCIENCE,
            credentialType = "Global Open Source Credential",
            realWorldValue = "Industry-standard credential for understanding Linux kernel concepts, bash automation, filesystem hierarchy, process management, and sysadmin operations.",
            freeAccessProvider = "edX & The Linux Foundation (Free training and verifiable digital completion)",
            examFormat = "Online lab assessments, terminal simulations, and final comprehensive examination",
            officialUrl = "https://training.linuxfoundation.org",
            keySkillsCovered = listOf(
                "System architecture, package managers & init daemon (systemd)",
                "Process signals, IPC, job control & permission flags (chmod/chown)",
                "Filesystem layout (FHS), inode structure & symbolic links",
                "Bash automation, regex piping & text processing (awk/sed/grep)"
            ),
            matchingKeywords = listOf("linux", "distributed systems", "operating system", "compilers", "sysadmin", "server")
        ),

        // === MATHEMATICS ===
        FreeCertification(
            id = "saylor-calculus",
            title = "Saylor Academy College Calculus & Differential Equations",
            shortName = "Saylor College Calculus",
            issuingOrganization = "Saylor Academy / ACE & NCCRS Aligned",
            category = EducationalCategory.MATHEMATICS,
            credentialType = "College-Credit Eligible Digital Certificate",
            realWorldValue = "Recognized across accredited universities for college credit transfer and by employers demonstrating advanced calculus and differential equations competence.",
            freeAccessProvider = "Saylor Academy (100% Free tuition, courses, and proctored final credential)",
            examFormat = "Comprehensive 50-question proctored final exam (70%+ passing grade required)",
            officialUrl = "https://www.saylor.org/courses/ma101/",
            keySkillsCovered = listOf(
                "Epsilon-delta limit proofs & continuous function behavior",
                "Derivative chain rule, implicit differentiation & optimization",
                "Riemann sums, Fundamental Theorem of Calculus & definite integrals",
                "First-order separable and linear ordinary differential equations"
            ),
            matchingKeywords = listOf("calculus", "differential equations", "derivatives", "integrals", "limits", "mathematics")
        ),
        FreeCertification(
            id = "saylor-linear-algebra",
            title = "Linear Algebra, Vector Spaces & Matrix Transformations",
            shortName = "Linear Algebra Certificate",
            issuingOrganization = "Saylor Academy / Open Education Consortium",
            category = EducationalCategory.MATHEMATICS,
            credentialType = "Verifiable Academic Certificate",
            realWorldValue = "Essential foundational proof for machine learning engineering, quantitative financial modeling, computer graphics, and robotics.",
            freeAccessProvider = "Saylor Academy Open Education (100% Free)",
            examFormat = "Timed cumulative final exam evaluating theoretical proofs and matrix computation",
            officialUrl = "https://www.saylor.org/courses/ma211/",
            keySkillsCovered = listOf(
                "Vector space axioms, linear independence, basis & dimension",
                "Linear transformations, matrix representations & kernel/nullspace",
                "Eigenvalues, eigenvectors & matrix diagonalization (spectral theorem)",
                "Orthogonality, Gram-Schmidt process & Singular Value Decomposition (SVD)"
            ),
            matchingKeywords = listOf("linear algebra", "vector spaces", "matrix", "eigenvalues", "orthogonal", "math")
        ),
        FreeCertification(
            id = "khan-statistics",
            title = "Probability & Inferential Statistics Mastery",
            shortName = "Inferential Statistics Mastery",
            issuingOrganization = "Khan Academy / College Board AP Aligned",
            category = EducationalCategory.MATHEMATICS,
            credentialType = "Verified Competency Portfolio & Certificate",
            realWorldValue = "Demonstrates analytical mastery in hypothesis testing, Bayesian reasoning, probability distributions, and clinical/scientific research statistics.",
            freeAccessProvider = "Khan Academy (100% Free digital mastery transcript)",
            examFormat = "Diagnostic unit tests and cumulative course challenge exam",
            officialUrl = "https://www.khanacademy.org/math/statistics-probability",
            keySkillsCovered = listOf(
                "Bayes' Theorem, conditional probability & combinatorics",
                "Discrete & continuous probability distributions (Normal, Binomial, Poisson)",
                "Central Limit Theorem & sampling error propagation",
                "Hypothesis testing: z-tests, t-tests, ANOVA, Chi-Square, and p-values"
            ),
            matchingKeywords = listOf("probability", "statistics", "statistical", "inference", "hypothesis", "bayes")
        ),

        // === NATURAL SCIENCES ===
        FreeCertification(
            id = "openwho-epidemiology",
            title = "World Health Organization Outbreak Investigation & Epidemiology",
            shortName = "WHO Epidemiology Credential",
            issuingOrganization = "World Health Organization (WHO / OpenWHO)",
            category = EducationalCategory.NATURAL_SCIENCES,
            credentialType = "Official International United Nations Credential",
            realWorldValue = "Official credential recognized by public health agencies, clinical research teams, and non-profits worldwide for disease surveillance and epidemiological response.",
            freeAccessProvider = "OpenWHO.org (100% Free official WHO verified certificate)",
            examFormat = "Online module evaluations and final cumulative epidemiological assessment",
            officialUrl = "https://openwho.org",
            keySkillsCovered = listOf(
                "Epidemiological surveillance metrics: Incidence, Attack Rates, Case-Fatality",
                "Contact tracing protocols, quarantine standards & transmission vectors",
                "Statistical risk ratios, odds ratios & diagnostic sensitivity/specificity",
                "Public health emergency interventions & international containment frameworks"
            ),
            matchingKeywords = listOf("epidemiology", "public health", "genetics", "crispr", "molecular genetics", "biology", "health")
        ),
        FreeCertification(
            id = "esa-astrobiology",
            title = "Space Exploration & Astrobiology Foundations",
            shortName = "ESA Space Sciences Cert",
            issuingOrganization = "European Space Agency (ESA) & Coursera Open",
            category = EducationalCategory.NATURAL_SCIENCES,
            credentialType = "Verified Scientific Institution Credential",
            realWorldValue = "Demonstrates rigorous comprehension of planetary astrophysics, stellar nucleosynthesis, cosmic radiation, and spectroscopic instrumentation.",
            freeAccessProvider = "Coursera & ESA Open Learning (Free audit & course certificate)",
            examFormat = "Module quizzes and peer-evaluated astrophysical problem sets",
            officialUrl = "https://www.esa.int/Education",
            keySkillsCovered = listOf(
                "Stellar lifecycle: Main sequence, Red Giants, Supernovae, Neutron Stars",
                "Cosmic Microwave Background (CMB) & Big Bang cosmological models",
                "General Relativity: Spacetime curvature, geodesic motion & black hole thermodynamics",
                "Astronomical spectroscopy, Doppler redshift & exoplanet detection"
            ),
            matchingKeywords = listOf("astrophysics", "cosmology", "astronomy", "space", "relativity", "quantum", "physics")
        ),
        FreeCertification(
            id = "saylor-chemistry",
            title = "Organic Chemistry & Molecular Reaction Mechanisms",
            shortName = "Organic Chemistry Diploma",
            issuingOrganization = "Saylor Academy / Open Chemical Sciences",
            category = EducationalCategory.NATURAL_SCIENCES,
            credentialType = "College-Level Verifiable Certificate",
            realWorldValue = "Demonstrates deep competency in organic synthesis, stereochemistry, electrophilic/nucleophilic reaction mechanisms, and spectroscopy (NMR, IR, Mass Spec).",
            freeAccessProvider = "Saylor Academy (100% Free certified exam)",
            examFormat = "Final exam covering chemical synthesis mechanisms and spectroscopy",
            officialUrl = "https://www.saylor.org/courses/chem101/",
            keySkillsCovered = listOf(
                "SN1, SN2, E1, and E2 reaction mechanisms and stereochemical inversion",
                "Chirality, enantiomers, diastereomers, and optical rotation",
                "Aromaticity, Huckel's rule & electrophilic aromatic substitution",
                "Spectroscopic structure elucidation: 1H-NMR, 13C-NMR, IR absorption bands"
            ),
            matchingKeywords = listOf("chemistry", "organic chemistry", "reaction", "molecular", "chemical", "molecules")
        ),

        // === HISTORY & CIVILIZATIONS ===
        FreeCertification(
            id = "smithsonian-history",
            title = "Smithsonian Institution Historical Inquiry & Artifacts",
            shortName = "Smithsonian History Credential",
            issuingOrganization = "Smithsonian Institution / edX",
            category = EducationalCategory.HISTORY_CIVILIZATION,
            credentialType = "Verified Museum & Archival Credential",
            realWorldValue = "Validates historical research rigor, archival primary source critique, material culture analysis, and museum curatorial standards.",
            freeAccessProvider = "Smithsonian Learning Lab & edX Free Portal",
            examFormat = "Primary source historiographical analysis and capstone research paper",
            officialUrl = "https://learninglab.si.edu",
            keySkillsCovered = listOf(
                "Primary vs secondary historical source critique & provenance validation",
                "Material artifact analysis & cultural conservation ethics",
                "Historiographical debate synthesis & counter-narrative evaluation",
                "Archival curation, digital humanities & exhibit pedagogy"
            ),
            matchingKeywords = listOf("history", "industrial revolution", "ancient civilizations", "cold war", "geopolitics", "civilization")
        ),
        FreeCertification(
            id = "saylor-world-history",
            title = "World History: Ancient Civilizations to the Modern World",
            shortName = "Saylor World History Cert",
            issuingOrganization = "Saylor Academy",
            category = EducationalCategory.HISTORY_CIVILIZATION,
            credentialType = "College-Level Accredited Certificate",
            realWorldValue = "Provides formal academic validation of world civilizations, commercial trade routes, political philosophy, and global geopolitical shifts.",
            freeAccessProvider = "Saylor Academy (100% Free certificate)",
            examFormat = "Comprehensive 50-question timed examination",
            officialUrl = "https://www.saylor.org/courses/hist101/",
            keySkillsCovered = listOf(
                "Neolithic revolution, river valley civilizations (Nile, Tigris, Indus)",
                "Classical antiquity: Athenian democracy, Roman Republic & legal codes",
                "Industrial Revolution: technological mechanization & global labor migrations",
                "20th-century Cold War geopolitics, nuclear diplomacy & post-colonial shifts"
            ),
            matchingKeywords = listOf("world history", "ancient", "mediterranean", "industrial revolution", "modern", "cold war")
        ),
        FreeCertification(
            id = "unesco-heritage",
            title = "UNESCO World Cultural Heritage & Historic Site Stewardship",
            shortName = "UNESCO Heritage Credential",
            issuingOrganization = "UNESCO / United Nations Open Learning",
            category = EducationalCategory.HISTORY_CIVILIZATION,
            credentialType = "Official UN Cultural Agency Credential",
            realWorldValue = "Recognized internationally by heritage preservation agencies, municipal cultural boards, and archaeological conservation teams.",
            freeAccessProvider = "UNESCO Open Learning Campus (Free certified training)",
            examFormat = "Case study assessment on historic site preservation and intangible cultural heritage",
            officialUrl = "https://whc.unesco.org",
            keySkillsCovered = listOf(
                "1972 World Heritage Convention standards & Outstanding Universal Value (OUV)",
                "Archaeological monument preservation & non-destructive stabilization",
                "Cultural landscape management & sustainable heritage tourism",
                "Emergency salvage & antiquities trafficking prevention"
            ),
            matchingKeywords = listOf("heritage", "unesco", "monument", "archaeology", "ancient civilizations", "culture")
        ),

        // === ART & HUMANITIES ===
        FreeCertification(
            id = "moma-modern-art",
            title = "MoMA Modern Art & Visual Ideas Specialization",
            shortName = "MoMA Modern Art Credential",
            issuingOrganization = "The Museum of Modern Art (MoMA)",
            category = EducationalCategory.ARTS_HUMANITIES,
            credentialType = "World-Leading Museum Certified Credential",
            realWorldValue = "Issued by the preeminent modern art institution globally; confirms mastery in 20th-century artistic movements, visual analysis, and contemporary art theory.",
            freeAccessProvider = "MoMA Coursera Portal (Free open learning and verified digital cert)",
            examFormat = "Curatorial visual analysis assignments and final artwork critique",
            officialUrl = "https://www.moma.org/learn",
            keySkillsCovered = listOf(
                "Avant-garde movements: Cubism, Dada, Bauhaus, Surrealism, Abstract Expressionism",
                "Formal analysis: line, mass, texture, color resonance, and spatial dynamics",
                "Art in public spaces, site-specificity & conceptual art manifestos",
                "Curatorial interpretation & visual literacy pedagogy"
            ),
            matchingKeywords = listOf("art", "modern art", "moma", "painting", "visual", "sculpture", "contemporary")
        ),
        FreeCertification(
            id = "calarts-graphic-design",
            title = "Graphic Design & Visual Composition Foundations",
            shortName = "CalArts Visual Arts Cert",
            issuingOrganization = "California Institute of the Arts (CalArts)",
            category = EducationalCategory.ARTS_HUMANITIES,
            credentialType = "Top Art Conservatory Credential",
            realWorldValue = "Recognized across creative studios and agencies for formal excellence in color harmony, typography hierarchy, grid systems, and visual composition.",
            freeAccessProvider = "CalArts Open Arts & Coursera Audit Track",
            examFormat = "Visual composition portfolio review and peer-assessed design briefs",
            officialUrl = "https://calarts.edu",
            keySkillsCovered = listOf(
                "Johannes Itten color contrasts: temperature, complementary, saturation",
                "Gestalt principles: proximity, closure, continuity, figure-ground",
                "Typography anatomy, kerning, leading & typographic rhythm",
                "Grid systems: Rule of Thirds, Golden Ratio & asymmetric dynamic balance"
            ),
            matchingKeywords = listOf("color theory", "composition", "design", "graphic", "renaissance", "visual", "art history")
        ),
        FreeCertification(
            id = "saylor-art-history",
            title = "Renaissance to Contemporary Art History Survey",
            shortName = "Art History Survey Diploma",
            issuingOrganization = "Saylor Academy / Global Art Consortium",
            category = EducationalCategory.ARTS_HUMANITIES,
            credentialType = "Academic Survey Credential",
            realWorldValue = "Formal academic credential validating knowledge of Italian and Northern Renaissance masters, Baroque chiaroscuro, and 19th-century salon revolutions.",
            freeAccessProvider = "Saylor Academy (100% Free credential)",
            examFormat = "Comprehensive proctored final exam with slide identification and stylistic analysis",
            officialUrl = "https://www.saylor.org/courses/arth101/",
            keySkillsCovered = listOf(
                "Brunelleschi's linear perspective, orthogonal lines & vanishing points",
                "Chiaroscuro and sfumato in Leonardo da Vinci & Caravaggio",
                "Northern Renaissance oil glaze techniques (Jan van Eyck, Dürer)",
                "Impressionist plein air lighting & color divisionism"
            ),
            matchingKeywords = listOf("renaissance", "masters", "art history", "perspective", "chiaroscuro", "paintings")
        ),

        // === SKILLED TRADES ===
        FreeCertification(
            id = "epa-608",
            title = "EPA Section 608 Universal Certification",
            shortName = "EPA 608 Universal",
            issuingOrganization = "U.S. Environmental Protection Agency (EPA) / ESCO & SkillCat",
            category = EducationalCategory.SKILLED_TRADES,
            credentialType = "Federal Legal Requirement (Clean Air Act)",
            realWorldValue = "Mandatory by federal law for technicians buying, handling, or servicing regulated refrigerants (CFC, HCFC, HFC, HFO) in HVAC/R equipment nationwide.",
            freeAccessProvider = "SkillCat App (Free EPA 608 Universal testing & training platform)",
            examFormat = "Online proctored 4-part exam: Core, Type I (Small Appliances), Type II (High-Pressure), Type III (Low-Pressure)",
            officialUrl = "https://www.skillcat.app",
            keySkillsCovered = listOf(
                "Ozone depletion & global warming potential metrics",
                "Refrigerant recovery, evacuation & vacuum level standards",
                "Leak detection methods & mandatory repair thresholds",
                "Safe cylinder storage, transport & DOT regulations"
            ),
            matchingKeywords = listOf("hvac", "refrigeration", "epa", "epa 608", "cooling", "ac", "air conditioning")
        ),
        FreeCertification(
            id = "schneider-energy",
            title = "Schneider Electric Energy University Associate Diploma",
            shortName = "Schneider Electric Cert",
            issuingOrganization = "Schneider Electric Global Training Institute",
            category = EducationalCategory.SKILLED_TRADES,
            credentialType = "Global Industry Credential (Vendor-Neutral)",
            realWorldValue = "Globally recognized credential in electrical distribution, power quality, circuit protection, motor controls, and modern energy efficiency.",
            freeAccessProvider = "Schneider Electric Energy University (100% Free online courses and exams)",
            examFormat = "Online self-paced module tests with digital verifiable diploma and certificate",
            officialUrl = "https://www.schneider-electric.com/energyuniversity",
            keySkillsCovered = listOf(
                "Circuit breaker selection, trip curves & selectivity",
                "Harmonics, power factor correction & grounding standards",
                "Industrial motor control, variable frequency drives (VFDs)",
                "Electrical safety standards & NFPA 70E compliance"
            ),
            matchingKeywords = listOf("electrician", "electrical", "nec", "circuit", "wiring", "energy", "power")
        ),
        FreeCertification(
            id = "osha-10-general",
            title = "OSHA 10-Hour General Industry Safety Certification Pathway",
            shortName = "OSHA 10 General",
            issuingOrganization = "U.S. Dept of Labor (OSHA) / Alison Open Training",
            category = EducationalCategory.SKILLED_TRADES,
            credentialType = "Workplace Safety Standard",
            realWorldValue = "Preferred or mandatory entry qualification across industrial plants, manufacturing facilities, maintenance shops, and machine operations.",
            freeAccessProvider = "Alison Open Workforce Training & OSHA Standards Library",
            examFormat = "Continuous module checkpoints with final cumulative safety assessment",
            officialUrl = "https://www.osha.gov",
            keySkillsCovered = listOf(
                "Lockout/Tagout (LOTO) 29 CFR 1910.147 energy isolation",
                "Machine guarding, pinch points & rotating equipment safety",
                "Hazard communication (GHS) & chemical safety data sheets",
                "Personal protective equipment (PPE) selection & inspection"
            ),
            matchingKeywords = listOf("millwright", "industrial", "maintenance", "osha", "safety", "machinery", "manufacturing")
        ),
        FreeCertification(
            id = "osha-10-construction",
            title = "OSHA 10-Hour Construction Safety & Framing Standards",
            shortName = "OSHA 10 Construction",
            issuingOrganization = "U.S. Dept of Labor (OSHA) / Workforce Career Portal",
            category = EducationalCategory.SKILLED_TRADES,
            credentialType = "Construction Jobsite Standard",
            realWorldValue = "Required by commercial general contractors and union/non-union jobsites before beginning structural framing or on-site carpentry work.",
            freeAccessProvider = "Free OSHA Construction Safety Training via CareerOneStop & Alison",
            examFormat = "Online interactive safety modules & certification evaluation",
            officialUrl = "https://www.osha.gov/training",
            keySkillsCovered = listOf(
                "Fall protection standards (Subpart M) & scaffold safety",
                "Pneumatic nail gun safety & portable power tool inspection",
                "Excavation, structural trenching & ladder safety protocols",
                "Stairway and floor opening framing safety guardrails"
            ),
            matchingKeywords = listOf("carpenter", "carpentry", "framing", "construction", "woodworking", "building")
        ),
        FreeCertification(
            id = "alison-welding",
            title = "Diploma in Welding Engineering & AWS Standards",
            shortName = "Welding & Metallurgy Diploma",
            issuingOrganization = "Alison / American Welding Society (AWS) Curriculum Alignment",
            category = EducationalCategory.SKILLED_TRADES,
            credentialType = "Accredited Vocational Diploma",
            realWorldValue = "Demonstrates mastery of SMAW, GMAW, GTAW processes, metallurgy, weld symbol reading, and non-destructive inspection techniques.",
            freeAccessProvider = "Alison Open Learning & Miller Open Educational Portals",
            examFormat = "Module assessments and final comprehensive metallurgy & welding test (80%+ to pass)",
            officialUrl = "https://alison.com",
            keySkillsCovered = listOf(
                "Base metal metallurgy & Heat-Affected Zone (HAZ) properties",
                "AWS joint designs, groove welds, fillet welds & joint prep",
                "Shielding gas mixtures (Ar, CO2, He) and electrode classifications",
                "Visual inspection, dye penetrant & magnetic particle testing"
            ),
            matchingKeywords = listOf("welding", "welder", "metallurgy", "mig", "tig", "stick", "smaw", "gmaw")
        ),
        FreeCertification(
            id = "alison-electrician",
            title = "Advanced Diploma in Electrical Studies & Circuits",
            shortName = "Electrical Studies Diploma",
            issuingOrganization = "Continuing Professional Development (CPD) / Alison",
            category = EducationalCategory.SKILLED_TRADES,
            credentialType = "Accredited Vocational Diploma",
            realWorldValue = "Validates rigorous knowledge of residential and commercial wiring, NEC branch circuit rules, grounding systems, and three-phase transformers.",
            freeAccessProvider = "Alison Vocational Academy (Free learning & certification)",
            examFormat = "Cumulative scored final exam with CPD-certified digital credential",
            officialUrl = "https://alison.com",
            keySkillsCovered = listOf(
                "Ohm's Law, Joule's heating & Kirchhoff's circuit laws",
                "Single-phase and three-phase delta/wye transformer configurations",
                "Ground fault (GFCI) and arc fault (AFCI) circuit protection",
                "Conduit fill calculations, derating factors & wire gauge selection"
            ),
            matchingKeywords = listOf("electrician", "electrical", "circuits", "wiring", "voltage", "current")
        ),

        // === HEALTHCARE & SAFETY ===
        FreeCertification(
            id = "fema-ics-100",
            title = "FEMA Incident Command System (ICS-100 & IS-700)",
            shortName = "FEMA ICS-100",
            issuingOrganization = "Federal Emergency Management Agency (FEMA) / EMI",
            category = EducationalCategory.HEALTHCARE_SAFETY,
            credentialType = "Official U.S. Federal Government Credential",
            realWorldValue = "Official federal credential recognized nationwide by emergency services, industrial safety directors, municipal utility departments, and disaster contractors.",
            freeAccessProvider = "FEMA Emergency Management Institute (100% Free federal training & transcript)",
            examFormat = "Online self-paced course with official FEMA final exam; certificate emailed directly from FEMA EMI",
            officialUrl = "https://training.fema.gov/is/courseoverview.aspx?code=IS-100.c",
            keySkillsCovered = listOf(
                "Unified command structure & multi-agency incident management",
                "Hazardous material incident containment & isolation zones",
                "Interoperable communications & operational chain of command",
                "Resource management, staging areas & emergency response plans"
            ),
            matchingKeywords = listOf("fema", "ics", "incident", "disaster", "emergency management", "hazard")
        ),
        FreeCertification(
            id = "cpr-firstaid",
            title = "National CPR, AED & Emergency First Aid Life Support",
            shortName = "CPR & First Aid Life Support",
            issuingOrganization = "National Health Care Provider Solutions (NHCPS) / SaveaLife",
            category = EducationalCategory.HEALTHCARE_SAFETY,
            credentialType = "Life-Safety Workplace Qualification",
            realWorldValue = "Essential qualification required for job site safety leads, school staff, fitness instructors, manufacturing workers, and daycare operators.",
            freeAccessProvider = "NHCPS Disque Foundation SaveaLife Initiative (100% Free digital certification)",
            examFormat = "Self-paced video modules followed by 25-question online certification exam",
            officialUrl = "https://nhcps.com",
            keySkillsCovered = listOf(
                "Adult, child & infant high-yield chest compressions and rescue breaths",
                "Automated External Defibrillator (AED) pad placement & cadence",
                "Choking relief: abdominal thrusts & unconscious airway clearance",
                "Arterial bleeding control, windlass tourniquet application & shock"
            ),
            matchingKeywords = listOf("first aid", "cpr", "aed", "choking", "bleeding", "medical", "paramedic")
        )
    )

    fun findCertificationForTopic(topic: String, title: String): FreeCertification? {
        val normalized = " $topic $title ".lowercase().replace(Regex("[^a-z0-9]"), " ")
        val words = normalized.split(Regex("\\s+")).filter { it.isNotBlank() }.toSet()
        return allCertifications.firstOrNull { cert ->
            cert.matchingKeywords.any { keyword ->
                val kw = keyword.lowercase().trim()
                if (kw.contains(" ")) {
                    normalized.contains(" $kw ")
                } else {
                    words.contains(kw)
                }
            }
        }
    }

    fun findMatchingCertification(topic: String, title: String): FreeCertification? =
        findCertificationForTopic(topic, title)

    fun categorizeTopic(topic: String, title: String): EducationalCategory =
        EducationalCategory.detectCategory(topic, title)

    fun getCertificationsForCategory(category: EducationalCategory): List<FreeCertification> {
        if (category == EducationalCategory.ALL) return allCertifications
        return allCertifications.filter { it.category == category }
    }
}
