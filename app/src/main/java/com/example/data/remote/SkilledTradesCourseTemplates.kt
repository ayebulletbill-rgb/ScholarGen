package com.example.data.remote

import com.example.data.model.ActiveRecallCheckpoint
import com.example.data.model.Course
import com.example.data.model.CourseSection
import com.example.data.model.ExamQuestion
import com.example.data.model.Flashcard
import com.example.data.model.ScopeTier
import java.util.UUID

object SkilledTradesCourseTemplates {

    fun createMillwrightCourse(): Course {
        val courseId = "trade-millwright-01"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Industrial Workplace Safety & Lockout/Tagout (OSHA 1910.147)",
                content = """
                    Millwrights construct, install, dismantle, and maintain heavy industrial machinery. Working around high-torque equipment necessitates strict adherence to Zero Energy State verification.

                    1. Lockout/Tagout (LOTO - 29 CFR 1910.147):
                    Before servicing any motor, pump, gearbox, or conveyor, every energy source (electrical, hydraulic pressure, pneumatic, gravity, chemical, thermal) must be isolated.
                    
                    2. Sequence of Energy Isolation:
                    - Preparation: Identify all hazardous energy sources and shut-down procedures.
                    - Shutdown & Isolation: Operate disconnect switches, shut valves, and secure mechanical blocking.
                    - Lock and Tag Application: Affix standardized individual padlock and danger tag.
                    - Stored Energy Dissipation: Bleed accumulators, vent pneumatic lines, insert pins, and drop counterweights.
                    - Verification of Isolation: Attempt restart at machine controls and test with calibrated multi-meter to prove 0.0V.
                """.trimIndent(),
                takeaways = listOf(
                    "Never trust an indicator lamp alone—always perform an active zero-voltage and zero-pressure verification.",
                    "Each tradesperson must apply their own personal padlock to multi-lock hasps; group locks require signed master accountability.",
                    "Gravity and trapped hydraulic fluid are lethal stored energy vectors that require physical blocking."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Under OSHA 1910.147, what is the crucial final step before commencing maintenance work under LOTO?",
                    options = listOf(
                        "Verifying the zero energy state by attempting to cycle the equipment and testing with calibrated instruments",
                        "Assuming the line is dead because the breaker handle is in the down position",
                        "Asking a coworker if they remember turning the power off",
                        "Removing personal locks to test if the motor rotates freely"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Active verification (testing controls and meters) ensures stored energy has been fully eliminated before physical work begins."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Precision Shaft Alignment: Rim-and-Face & Reverse Dial",
                content = """
                    Over 50% of premature rotating equipment failures (bearing seizure, seal leakage, coupling wear) stem from shaft misalignment between driver (motor) and driven (pump/gearbox) units.

                    1. Types of Misalignment:
                    - Angular Misalignment: Shaft centerlines intersect at an angle.
                    - Parallel (Offset) Misalignment: Shaft centerlines are parallel but offset in height or side position.
                    - Combined Misalignment: Both angular and parallel errors occur simultaneously in horizontal and vertical planes.

                    2. Dial Indicator Alignment Techniques:
                    - Rim-and-Face Method: One indicator measures radial runout (rim/offset) while another measures axial gap (face/angularity).
                    - Reverse Indicator Method: Measures radial offset from two points on adjacent shafts, mathematically eliminating shaft end-play errors.

                    3. Soft Foot Correction:
                    Prior to final alignment, check for 'soft foot' (uneven machine foot contact) using a feeler gauge or dial indicator. Maximum permissible deflection is typically under 0.002 inches (0.05 mm).
                """.trimIndent(),
                takeaways = listOf(
                    "Soft foot must be identified and corrected with precision stainless steel shims before tightening hold-down bolts.",
                    "The Reverse Indicator method is preferred for sleeve-bearing machines because it eliminates axial float errors.",
                    "Thermal growth calculation is vital: cold-aligned hot machinery will misalign during steady-state thermal expansion."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why must 'soft foot' be corrected before performing precision shaft alignment?",
                    options = listOf(
                        "Because tightening bolts on an unsupported foot twists the machine casing and misaligns the internal bearings",
                        "Because it makes the electric motor consume 500% more voltage",
                        "Because soft foot only matters on stationary non-rotating piping",
                        "Because dial indicators cannot turn if machine feet are made of steel"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Torquing down an uneven foot induces mechanical casing distortion, skewing internal bearing raceways and invalidating alignment readings."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "Industrial Bearings, Fits, Tolerances & Lubrication",
                content = """
                    Bearings support rotating loads and reduce friction. Proper mounting fits, internal clearance, and lubricant selection determine operational lifespan.

                    1. Anti-Friction Bearings (Rolling Element):
                    - Deep Groove Ball: High speed, light-to-moderate radial and thrust loads.
                    - Spherical Roller: High radial loads with self-aligning capability for heavy industrial shafts.
                    - Cylindrical Roller: Extremely high pure radial load capacity.
                    - Tapered Roller: Combined radial and heavy axial thrust in gearbox pinion shafts.

                    2. Bearing Fits & Mounting:
                    - Interference Fit: Inner ring pressed onto rotating shaft; outer ring sliding fit in housing.
                    - Induction Heating: Heat bearings uniformly up to 230°F (110°C) with an induction heater. Never use an open torch or exceed 250°F, which tempers bearing steel.

                    3. Lubrication Modes:
                    Over-greasing is the #1 cause of bearing overheating. Churning of excess grease causes friction, thermal breakdown, and seal blowout.
                """.trimIndent(),
                takeaways = listOf(
                    "Never heat bearings with an open torch; controlled induction heating prevents localized metallurgy damage.",
                    "Match bearing internal clearance (C2, Normal, C3, C4) to the operational operating temperature and press-fit expansion.",
                    "Excess grease causes hydrodynamic churning and elevated temperatures leading to rapid oil bleed."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the recommended tool and temperature threshold when expanding an anti-friction bearing for shaft mounting?",
                    options = listOf(
                        "Induction heater up to 230°F (110°C), never exceeding 250°F (120°C)",
                        "Oxy-acetylene cutting torch heated until the bearing turns glowing red",
                        "Submerging the bearing in boiling water for 48 hours",
                        "Hammering the outer ring directly with a hardened steel sledgehammer"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Induction heaters provide uniform, demagnetized heating up to 230°F without softening the bearing steel or degrading metallurgy."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-4",
                courseId = courseId,
                orderIndex = 3,
                title = "Mechanical Drives: Couplings, V-Belts, Chains & Gearboxes",
                content = """
                    Power transmission systems transfer mechanical energy from electric motors and prime movers to production machinery.

                    1. Couplings:
                    - Rigid: Requires flawless collinear alignment (rarely used except on line shafts).
                    - Flexible: Elastomeric jaw (Lovejoy), grid (Falk), gear, and disc pack couplings accommodate minimal axial/angular deflection while dampening shock loads.

                    2. V-Belt & Timing Belt Drives:
                    - V-Belts wedged into sheaves multiply traction through sidewall friction. Sheave groove wear exceeding 1/32 inch requires replacement.
                    - Belt tension: Use a spring plunger deflection tester. Under-tension causes slippage and glazing; over-tension ruins motor shaft bearings.

                    3. Gearbox Topologies:
                    - Helical: High speed, quiet, angled teeth with axial thrust.
                    - Bevel/Spiral Bevel: 90-degree directional power transfer.
                    - Worm: High single-stage reduction ratio, non-reversing backdrive safety.
                """.trimIndent(),
                takeaways = listOf(
                    "V-belts drive from the sidewalls of the sheave, never the bottom floor of the groove.",
                    "Replace belts in matched sets; mixing new and stretched worn belts unevenly concentrates load.",
                    "Check oil viscosity (ISO VG grade) and magnetic drain plugs during gearbox preventative inspections."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "When inspecting a V-belt drive, where should the belt make contact in the sheave groove?",
                    options = listOf(
                        "On both angled sidewalls only, leaving clearance at the bottom of the groove",
                        "Riding firmly against the bottom of the groove with loose sidewalls",
                        "Only on one single edge while twisted sideways",
                        "Covered in oil and grease to reduce surface friction"
                    ),
                    correctOptionIndex = 0,
                    explanation = "V-belts operate via the wedging action against the two sidewalls; bottoming out results in immediate slippage and rapid failure."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-5",
                courseId = courseId,
                orderIndex = 4,
                title = "Heavy Rigging, Sling Angles & Hoisting Dynamics",
                content = """
                    Millwrights rig and hoist industrial equipment weighing dozens of tons into place using cranes, gantry systems, and chain falls.

                    1. Sling Angles & Tension Multiplication:
                    As horizontal sling angle decreases, tension in each sling leg increases exponentially!
                    - At 60 degrees: Tension = Load / (2 * sin 60°) = 0.577 * Load per leg.
                    - At 45 degrees: Tension = 0.707 * Load per leg.
                    - At 30 degrees: Tension = 1.000 * Load per leg (each leg carries the full load weight!).
                    - NEVER rig with a horizontal angle less than 30 degrees.

                    2. Rigging Hardware Safety:
                    - Shackles: Screw pin anchor shackles must have pins seated and backed off 1/4 turn to avoid pin binding under load. Never side-load a shackle.
                    - Eyebolts: Plain (non-shoulder) eyebolts are strictly limited to pure vertical inline lifts. Shouldered eyebolts must be fully seated against the workpiece when angular loading occurs.
                    - Center of Gravity (COG): The crane hook must be positioned directly over the load's center of gravity before hoisting to prevent dangerous swinging.
                """.trimIndent(),
                takeaways = listOf(
                    "Low sling angles drastically multiply leg tension—never allow horizontal sling angles below 30 degrees.",
                    "Non-shoulder eyebolts are strictly prohibited from angular pulls; use rated swivel hoist rings for angled lifts.",
                    "Position the crane hook directly over the center of gravity to ensure a vertical, stable pick."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What happens to the tension in a two-legged bridle sling as the sling angle to the horizontal decreases toward 30 degrees?",
                    options = listOf(
                        "Tension increases dramatically, reaching double the vertical load per leg",
                        "Tension decreases to near zero because the slings are wider",
                        "Tension remains unchanged regardless of sling angle",
                        "Tension only increases if the wire rope is unlubricated"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Sling tension is inversely proportional to the sine of the horizontal angle; at 30 degrees each leg bears equal to the entire suspended weight."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-6",
                courseId = courseId,
                orderIndex = 5,
                title = "Predictive Maintenance: Vibration Analysis & Thermography",
                content = """
                    Predictive Maintenance (PdM) detects machinery deterioration long before catastrophic failure occurs, saving millions in industrial plant downtime.

                    1. Vibration Analysis (FFT Spectrum):
                    Piezoelectric accelerometers capture vibration velocity (in/sec RMS or mm/sec).
                    - 1X RPM Peak: Unbalance in rotating assembly.
                    - 2X RPM Peak: Misalignment or loose foundation mounting bolts.
                    - High-Frequency Non-Synchronous Peaks: Inner/outer bearing raceway defects (BPFI, BPFO).
                    - Vane/Gear Mesh Frequencies: Chipped teeth or blade erosion.

                    2. Infrared Thermography:
                    Thermal cameras identify elevated resistance in electrical switchgear (hot terminals) and friction in under-lubricated bearing housings. Baseline delta-T measurements highlight impending failure.

                    3. Oil Condition Monitoring:
                    Spectrometric analysis evaluates wear metals (iron, copper, lead) and detects particle contamination (silica) and moisture content.
                """.trimIndent(),
                takeaways = listOf(
                    "A dominant 1X vibration peak signifies mass unbalance, while strong 2X harmonics indicate shaft misalignment.",
                    "High frequency bearing defect frequencies (BPFO/BPFI) provide weeks of advance notice before bearing seizure.",
                    "Infrared thermography non-invasively locates high-resistance loose lugs in 480V motor control centers."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In rotating machinery vibration analysis, which signature frequency harmonic typically indicates shaft misalignment?",
                    options = listOf(
                        "A prominent 2X running speed harmonic along with high axial vibration",
                        "A pure 100X sub-synchronous frequency",
                        "Complete absence of all vibrations across all axes",
                        "A random frequency that only appears during machine shutdown"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Shaft misalignment characteristically generates high 2X running speed harmonics alongside significant axial vibration."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Zero Energy State", "The absolute condition where all electrical, pneumatic, hydraulic, thermal, and mechanical stored forces have been dissipated and locked out.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Soft Foot", "The mechanical condition where machine mounting feet do not evenly contact the baseplate, causing casing distortion when torqued down.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Reverse Dial Alignment", "An alignment method measuring radial runout across two shaft faces simultaneously, mathematically canceling axial float.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Bearing Induction Heater", "An electromagnetic device that rapidly and uniformly heats bearing inner rings up to 230°F (110°C) without metallurgical damage.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Sling Angle De-Rating", "The physical law where sling leg tension increases exponentially as the horizontal angle between the load and sling drops.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Vibration 2X Harmonic", "The frequency spectrum peak twice the shaft running speed that reliably indicates shaft coupling misalignment.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(UUID.randomUUID().toString(), courseId, 0, "What is the mandatory OSHA procedure to confirm zero energy under Lockout/Tagout?", listOf("Cycle local start/stop controls and measure zero voltage with a verified meter", "Visually look at the main switch from across the shop floor", "Ask the previous shift operator if the motor was running", "Assume safety if breaker switches are painted red"), 0, "Active testing of controls and voltage verifies absence of residual power."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 1, "What is the primary danger of operating rotating machinery with uncorrected soft foot?", listOf("Torquing hold-down bolts distorts the machine casing, skewing internal bearings", "The motor instantly reverses rotation direction", "It causes electrical current to leak into the cooling fan", "Dial indicators will permanently shatter"), 0, "Casing distortion misaligns internal bearing races and seals."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 2, "What is the maximum safe heating temperature for mounting standard rolling-element bearings?", listOf("230°F (110°C), never exceeding 250°F (120°C)", "650°F (343°C) until red heat appears", "100°F (38°C) maximum", "1000°F (538°C) with an oxy-acetylene torch"), 0, "Exceeding 250°F alters the heat treatment and hardness of bearing steel."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 3, "Where must a V-belt make physical contact inside a sheave to transfer torque efficiently?", listOf("Exclusively against both angled sidewalls, clearing the bottom floor", "Only flat against the bottom floor of the groove", "Lubricated with grease on all three sides", "Loose on one sidewall only"), 0, "V-belt torque relies strictly on the wedging friction against groove sidewalls."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 4, "What is the minimum safe horizontal sling angle permitted for industrial hoisting rigging?", listOf("30 degrees to the horizontal", "5 degrees to the horizontal", "0 degrees (completely flat)", "No minimum angle exists"), 0, "Below 30 degrees sling tension doubles and poses extreme structural failure risks."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 5, "In predictive maintenance vibration analysis, what does a high 1X running speed frequency peak indicate?", listOf("Mass unbalance in the rotor assembly", "Loose coupling guard bolt", "Low oil level in the reservoir", "Electrical phase imbalance"), 0, "Dynamic rotor unbalance produces a classic 1X running frequency vibration.")
        )

        return Course(
            id = courseId,
            title = "Millwright: Industrial Maintenance & Alignment",
            topic = "Millwright",
            description = "Master industrial machinery installation, precision shaft alignment, bearing fits, rigging physics, LOTO zero-energy safety, and predictive vibration diagnostics.",
            tier = ScopeTier.MEDIUM,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }

    fun createHvacCourse(): Course {
        val courseId = "trade-hvac-01"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Thermodynamics & The Vapor-Compression Refrigeration Cycle",
                content = """
                    All modern air conditioning, heat pump, and commercial refrigeration systems operate on the closed vapor-compression cycle.

                    1. The Four Primary Components:
                    - Compressor: The heart of the system. Pumps low-pressure, low-temperature superheated vapor and compresses it into high-pressure, high-temperature superheated vapor.
                    - Condenser: Rejects heat to outdoor ambient air (or water). Desuperheats, condenses the vapor into high-pressure liquid, and subcools the liquid below its saturation temperature.
                    - Metering Device (TXV / EEV / Piston): Restricts liquid flow, producing a drastic pressure drop. Flashes high-pressure subcooled liquid into low-pressure, low-temperature liquid-vapor mixture (flash gas).
                    - Evaporator: Absorbs heat from the indoor air. Boils the low-pressure liquid into vapor and superheats the vapor before entering the compressor suction port.

                    2. Superheat & Subcooling Calculations:
                    - Superheat = Suction Line Temperature - Evaporator Saturation Temperature (from P/T chart). Protects compressor from damaging liquid slugging.
                    - Subcooling = Condenser Saturation Temperature (from P/T chart) - Liquid Line Temperature. Ensures a solid column of pure liquid reaches the metering device.
                """.trimIndent(),
                takeaways = listOf(
                    "Compressors can only compress vapor—liquid refrigerant entering the suction inlet causes catastrophic hydraulic slugging.",
                    "Target superheat confirms evaporator coil efficiency; target subcooling indicates proper refrigerant charge on TXV systems.",
                    "Latent heat absorption during boiling inside the evaporator accounts for the vast majority of cooling capacity."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why is ensuring proper superheat at the compressor suction inlet critical to system longevity?",
                    options = listOf(
                        "It guarantees that all liquid refrigerant has fully evaporated, preventing liquid slugging into compressor cylinders",
                        "It makes the indoor air temperature drop to sub-zero within seconds",
                        "It stops the condenser fan from rotating too quickly",
                        "It increases electrical resistance in the blower motor"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Liquid cannot be compressed; superheat ensures only vapor enters the compressor, preventing valve and piston damage."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "EPA Section 608 Core Requirements & Clean Air Act Standards",
                content = """
                    Under Section 608 of the federal Clean Air Act, it is illegal for any person to knowingly vent or release ozone-depleting substances (ODS) and non-exempt substitutes (HFCs, HFOs) into the atmosphere.

                    1. Refrigerant Classifications:
                    - CFCs (R-12, R-11, R-502): Contain chlorine, fluorine, and carbon. High Ozone Depletion Potential (ODP). Banned from production.
                    - HCFCs (R-22): Contain hydrogen, chlorine, fluorine, carbon. Moderate ODP. Phased out of manufacture.
                    - HFCs (R-410A, R-134a, R-404A): Zero ODP, but extremely high Global Warming Potential (GWP). Regulated under the AIM Act.
                    - HFOs / A2L Low-GWP Refrigerants (R-32, R-454B): Mildly flammable, zero ODP, ultra-low GWP standard in modern equipment.

                    2. Recovery Requirements:
                    - Must use certified recovery equipment with low-loss fittings.
                    - Recovery cylinders: Yellow tops and gray bodies. Never fill beyond 80% capacity by weight (liquid expansion hydro-hazard).
                    - High-pressure appliances with more than 200 lbs of R-22 must be evacuated to 10 inches of vacuum (Hg) before opening for service.
                """.trimIndent(),
                takeaways = listOf(
                    "Knowingly venting refrigerant during servicing or repair violates federal law with fines exceeding $44,000/day.",
                    "Recovery cylinders must have a gray body and yellow top, re-certified every 5 years, and never filled over 80% liquid volume.",
                    "R-410A operates at pressures 50-60% higher than R-22 and requires dedicated manifold gauges and vacuum hoses."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the maximum safe fill capacity for a DOT-certified refrigerant recovery cylinder?",
                    options = listOf(
                        "80% of its liquid capacity by weight to allow room for thermal expansion",
                        "100% full to maximize transportation efficiency",
                        "50% only regardless of cylinder rating",
                        "Any capacity as long as the cylinder is painted bright green"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Refrigerant expands rapidly with temperature; filling over 80% risks hydrostatic pressure rupture."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "System Evacuation, Triple Evacuation & Micron Levels",
                content = """
                    Non-condensable gases (air) and moisture are the mortal enemies of HVAC/R systems. Moisture reacts with POE synthetic oils and refrigerants to form hydrofluoric and hydrochloric acids, which strip motor winding insulation and burn out compressors.

                    1. The Deep Vacuum Standard:
                    A mechanical gauge manifold cannot measure deep vacuums accurately. Technicians must use a calibrated digital micron gauge.
                    - Target: Pull the system down below 500 microns (0.5 mm Hg).
                    - Decay Test (Blank-off): Close the isolation valve and observe for 10 minutes. If pressure rises and levels off under 1,000 microns, moisture remains. If pressure rises continuously to atmospheric pressure, an active leak exists!

                    2. Triple Evacuation Method:
                    - Pull system to 1,500 microns.
                    - Break vacuum with dry nitrogen to 2-3 psig. Nitrogen acts as a sponge absorbing moisture.
                    - Pull second vacuum to 1,000 microns; sweep with dry nitrogen again.
                    - Pull final vacuum down below 500 microns and hold blank-off.
                """.trimIndent(),
                takeaways = listOf(
                    "Standard manifold compound gauges cannot measure microns; always use an electronic micron gauge connected directly to the system.",
                    "Never run a hermetic compressor while in a deep vacuum—the electrical arc will immediately short the internal windings.",
                    "A successful blank-off test that stays below 500 microns proves the system is tight, leak-free, and dehydrated."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "During a deep vacuum decay test, what does it mean if the micron gauge rises steadily all the way to atmospheric pressure?",
                    options = listOf(
                        "An active leak exists in the system allowing outside air to enter",
                        "The system is perfectly dehydrated and ready to charge",
                        "The vacuum pump oil has reached peak efficiency",
                        "The TXV bulb has frozen solid"
                    ),
                    correctOptionIndex = 0,
                    explanation = "A continuous rise to atmosphere indicates an open leak path admitting outside air."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-4",
                courseId = courseId,
                orderIndex = 3,
                title = "HVAC Electrical Schematics: Capacitors, Contactors & Controls",
                content = """
                    Over 75% of residential HVAC service calls are electrical in nature. Understanding single-phase power, 24V control circuits, and motor starting components is paramount.

                    1. Low Voltage Control Circuit (24VAC):
                    The step-down transformer converts 120V/240V high voltage to 24VAC.
                    - R (Red): 24VAC power supply.
                    - C (Common): 24VAC return line to complete the relay circuit.
                    - Y (Yellow): Cooling call to the outdoor contactor coil.
                    - W (White): Heating call to gas valve or electric heat sequencer.
                    - G (Green): Indoor blower fan relay.
                    - O/B (Orange/Blue): Reversing valve solenoid for heat pumps.

                    2. Motor Capacitors:
                    - Run Capacitor: Continuously introduces a 90-degree phase shift between start and run windings to create motor rotating torque. Rated in microfarads (uF) and AC voltage (370V/440V). Must test within ±6% of rated microfarad value.
                    - Always discharge capacitors through a 20k ohm 5-watt resistor before touching terminals.
                """.trimIndent(),
                takeaways = listOf(
                    "Always safely discharge capacitors before testing capacitance or resistance with a meter.",
                    "A blown 3A or 5A low-voltage fuse on the furnace board indicates a short circuit in the 24V thermostat wiring or contactor coil.",
                    "Run capacitors can be replaced with equal or higher voltage ratings, but microfarad ratings must match within manufacturer tolerance."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In standard HVAC 24V thermostat control wiring, which terminal letter commands the outdoor compressor contactor to close for cooling?",
                    options = listOf(
                        "Terminal Y",
                        "Terminal W",
                        "Terminal G",
                        "Terminal R"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Terminal Y energizes the 24V contactor coil to start the outdoor compressor and condenser fan."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-5",
                courseId = courseId,
                orderIndex = 4,
                title = "Airflow, Psychrometrics & Static Pressure Diagnostics",
                content = """
                    Refrigerant absorbs heat from the air; if airflow is inadequate, cooling fails regardless of refrigerant charge.

                    1. The Rule of Thumb for Airflow:
                    Standard residential air conditioning requires 350 to 400 CFM (Cubic Feet per Minute) of airflow per ton of cooling capacity (1 Ton = 12,000 BTU/hr).
                    - A 3-ton system requires 1,050 to 1,200 CFM.

                    2. Total External Static Pressure (TESP):
                    Measured with a dual-port digital manometer and static pressure probes.
                    - Supply Static + Return Static = Total External Static Pressure.
                    - Typical residential systems are designed for 0.5 inches of water column (in. w.c.) TESP. High static (>0.8 in. w.c.) indicates restricted dirty filters, undersized ducts, or plugged evaporator coils, causing frozen coils and tripped limits.

                    3. Psychrometric Chart:
                    Connects dry-bulb temperature, wet-bulb temperature, relative humidity, enthalpy, and dew point. Used to measure sensible vs. latent cooling load.
                """.trimIndent(),
                takeaways = listOf(
                    "Always verify 350-400 CFM per ton of airflow before attempting to adjust refrigerant charge.",
                    "Excessive Total External Static Pressure (>0.8 in. w.c.) starves the evaporator coil, causing freeze-ups and blower motor failure.",
                    "Temperature drop (delta T) across an air conditioning evaporator coil should normally range between 16°F and 22°F."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "How much airflow in CFM (Cubic Feet per Minute) is required per ton of residential air conditioning capacity under standard conditions?",
                    options = listOf(
                        "350 to 400 CFM per ton",
                        "50 to 100 CFM per ton",
                        "1,500 CFM per ton",
                        "Airflow is irrelevant as long as the refrigerant pressure is high"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Standard residential cooling designs require 350-400 CFM/ton to achieve proper heat transfer across the coil."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-6",
                courseId = courseId,
                orderIndex = 5,
                title = "Heat Pumps, Inverter Compressors & Reversing Valve Operation",
                content = """
                    Heat pumps provide both cooling and heating by reversing refrigerant flow using a 4-way reversing valve.

                    1. The 4-Way Reversing Valve:
                    - In Cooling Mode: Indoor coil acts as evaporator; outdoor coil acts as condenser.
                    - In Heating Mode: The sliding spool redirects hot compressor discharge gas directly into the indoor coil, making the indoor coil the condenser! The outdoor coil becomes the evaporator, extracting heat from cold outside air.
                    - Defrost Cycle: In winter, ice accumulates on the outdoor coil. The control board energizes the reversing valve into cooling mode, shuts off the outdoor fan, and activates supplemental electric heat strips to melt outdoor frost without blowing cold air indoors.

                    2. Inverter-Driven Variable Capacity Systems:
                    Modern heat pumps convert AC line voltage into DC, driving brushless DC inverter compressors from 20% to 120% speed. Modulating output drastically cuts energy consumption and maintains stable indoor temperatures.
                """.trimIndent(),
                takeaways = listOf(
                    "The reversing valve reverses the roles of the indoor and outdoor coils to provide heat in winter.",
                    "During the defrost cycle, supplemental heat strips energize to temper indoor air while outdoor frost melts.",
                    "Inverter systems run longer cycles at low speeds, providing superior dehumidification and SEER2 energy efficiency."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In a heat pump system in heating mode, what role does the indoor coil play?",
                    options = listOf(
                        "It acts as the condenser, condensing hot gas and releasing heat into the conditioned living space",
                        "It acts as an expansion valve",
                        "It acts as the evaporator, absorbing cold air from the basement",
                        "It shuts off completely while the outdoor coil heats the ductwork"
                    ),
                    correctOptionIndex = 0,
                    explanation = "In heating mode, hot discharge vapor is directed to the indoor coil where it condenses, rejecting heat into the home."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Vapor-Compression Cycle", "A thermodynamic loop moving heat from cold spaces to warm spaces using a compressor, condenser, metering device, and evaporator.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "EPA Section 608", "Federal Clean Air Act regulation mandating technician certification for handling, recovering, and servicing regulated refrigerants.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Deep Vacuum Standard", "Dehydrating an HVAC system to below 500 microns of mercury using an electronic vacuum gauge and rotary vane pump.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Reversing Valve (4-Way)", "A pilot-operated valve that redirects hot compressor discharge vapor to switch a heat pump between cooling and heating modes.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "TESP (Total External Static Pressure)", "The combined resistance to airflow in supply and return ductwork, normally designed around 0.5 in. w.c.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Superheat vs. Subcooling", "Superheat verifies vapor safety at the compressor; subcooling verifies solid liquid delivery to the expansion valve.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(UUID.randomUUID().toString(), courseId, 0, "What is the primary thermodynamic function of the metering device in an air conditioning system?", listOf("To drop refrigerant pressure, causing subcooled liquid to flash into a low-temperature mixture", "To pump high-pressure liquid directly into the compressor", "To filter dust particles out of the outdoor air", "To convert electrical alternating current to direct current"), 0, "The metering device restricts flow, creating the pressure drop necessary for boiling."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 1, "Under EPA Section 608 regulations, what color scheme is mandated for refrigerant recovery cylinders?", listOf("Gray body with a yellow top and shoulder", "Solid red cylinder with blue stripes", "Solid black cylinder", "White cylinder with green handle"), 0, "DOT-certified recovery cylinders must have a gray body and yellow top."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 2, "What is the industry target micron level when performing a deep evacuation before charging refrigerant?", listOf("Below 500 microns", "50,000 microns", "10,000 microns", "Zero microns is easily reached with a bicycle pump"), 0, "500 microns ensures complete moisture dehydration and removal of non-condensables."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 3, "On a residential furnace or air handler control board, what does the 24V 'W' terminal energize?", listOf("Heating call (gas valve or heat sequencer)", "Outdoor air conditioning compressor", "Indoor continuous blower fan only", "Thermostat display backlight"), 0, "Terminal W carries the call for primary heating."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 4, "A 4-ton residential air conditioner requires approximately how much airflow across its coil?", listOf("1,400 to 1,600 CFM", "400 CFM total", "8,000 CFM", "Airflow cannot be measured in cubic feet"), 0, "4 tons multiplied by 350-400 CFM/ton equals 1,400 to 1,600 CFM."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 5, "What component in a heat pump switches the refrigeration cycle between heating and cooling?", listOf("4-way reversing valve", "TXV bulb", "Dual-element run capacitor", "Low-pressure cutoff switch"), 0, "The reversing valve redirects discharge gas to invert indoor and outdoor coil functions.")
        )

        return Course(
            id = courseId,
            title = "HVAC/R Systems & EPA Section 608 Universal",
            topic = "HVAC",
            description = "Comprehensive technician curriculum covering vapor-compression thermodynamics, EPA 608 certification rules, 500-micron deep evacuations, 24V schematics, airflow TESP, and heat pumps.",
            tier = ScopeTier.MEDIUM,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }

    fun createElectricianCourse(): Course {
        val courseId = "trade-electrician-01"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "National Electrical Code (NEC) Architecture & Article 90 Fundamentals",
                content = """
                    The National Electrical Code (NFPA 70) is the benchmark for safe electrical design, installation, and inspection in the United States and internationally.

                    1. Purpose & Scope (NEC Article 90):
                    The purpose of the NEC is the practical safeguarding of persons and property from hazards arising from the use of electricity. It is not an instruction manual, but a legal standard enforceable when adopted by local jurisdictions (AHJ - Authority Having Jurisdiction).

                    2. Organization of the NEC:
                    - Chapters 1-4: General rules applicable to all installations (Definitions, Wiring & Protection, Wiring Methods, Equipment for General Use).
                    - Chapters 5-7: Special Occupancies (hazardous classified areas, gas stations, healthcare), Special Equipment (elevators, EV chargers, solar PV), and Special Conditions (emergency standby power).
                    - Chapter 8: Communications Systems (isolated chapter).
                    - Chapter 9: Tables (conduit dimensions, conductor properties, percent conduit fill limits).
                """.trimIndent(),
                takeaways = listOf(
                    "The NEC is a minimum safety standard enforceable by the local Authority Having Jurisdiction (AHJ).",
                    "Chapters 1 through 4 apply generally to all installations unless modified by Chapters 5 through 7.",
                    "Chapter 9 Tables govern conductor physical dimensions, wire fill percentages, and AC resistance."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "According to NEC Article 90, what is the primary purpose of the National Electrical Code?",
                    options = listOf(
                        "The practical safeguarding of persons and property from hazards arising from electricity",
                        "To teach beginners how to do basic home wiring tutorials",
                        "To guarantee that electrical systems operate at peak thermodynamic efficiency",
                        "To enforce maximum profit margins for utility companies"
                    ),
                    correctOptionIndex = 0,
                    explanation = "NEC 90.1 explicitly states the code's purpose is safeguarding life and property from electrical hazards."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Ohm's Law, Electrical Math & Single-Phase vs. Three-Phase Power",
                content = """
                    Mastery of electrical calculations is required for wire sizing, breaker ratings, transformer connections, and voltage drop mitigation.

                    1. Core Equations:
                    - Ohm's Law: Voltage (E) = Current (I) * Resistance (R).
                    - Power Formula (Single-Phase): Power (P, Watts) = Volts (E) * Amps (I) * Power Factor (PF).
                    - Three-Phase Power: Power (Watts) = Volts * Amps * sqrt(3) * PF = V * I * 1.732 * PF.
                    - Apparent Power (Volt-Amps, VA): S = V * I (single-phase) or S = V * I * 1.732 (three-phase). Transformers are rated in kVA because heating is dictated by current and voltage regardless of power factor.

                    2. Voltage Drop:
                    NEC 210.19(A) Informational Note recommends that voltage drop on branch circuits should not exceed 3%, and combined feeder plus branch circuit voltage drop should not exceed 5%.
                    Formula (Single-Phase): VD = (2 * K * I * L) / Circular Mils (CM). Where K = 12.9 for copper, 21.2 for aluminum.
                """.trimIndent(),
                takeaways = listOf(
                    "Three-phase calculations require multiplying by the square root of 3 (1.732) due to the 120-degree phase offset.",
                    "Keep combined feeder and branch circuit voltage drop under 5% to protect motors and electronic devices from under-voltage.",
                    "Apparent power (kVA) determines transformer and generator sizing; real power (kW) determines actual work done."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In a 3-phase, 480V electrical system supplying a balanced resistive load drawing 20 Amps, what is the apparent power in kVA?",
                    options = listOf(
                        "Approximately 16.6 kVA (480 * 20 * 1.732 / 1000)",
                        "Exactly 9.6 kVA (480 * 20 / 1000)",
                        "100 kVA",
                        "480 kVA"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Three-phase power formula is V * I * sqrt(3): 480 * 20 * 1.732 = 16,627 VA = 16.6 kVA."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "Conduit Bending Math (EMT/IMC): 90s, Saddles & 4-Bend Offsets",
                content = """
                    Electricians bend Electrical Metallic Tubing (EMT), Rigid Metal Conduit (RMC), and Intermediate Metal Conduit (IMC) using hand benders, mechanical benders, and hydraulic benders.

                    1. 90-Degree Stub-Up & Deduct (Take-Up):
                    When making a 90-degree bend, the bender shoe consumes conduit length known as the deduct or take-up.
                    - 1/2 inch EMT: Deduct 5 inches.
                    - 3/4 inch EMT: Deduct 6 inches.
                    - 1 inch EMT: Deduct 8 inches.
                    - Subtract deduct from desired stub height and align mark with arrow on bender.

                    2. Offsets (Obstacle Clearance):
                    To route conduit around beams or enter junction boxes at an offset:
                    - Multipliers by Bend Angle:
                      * 10°: Multiplier = 6.0 | Shrink per inch of offset = 1/16 in.
                      * 22.5°: Multiplier = 2.6 | Shrink = 3/16 in.
                      * 30°: Multiplier = 2.0 | Shrink = 1/4 in. (Most common field offset!).
                      * 45°: Multiplier = 1.414 | Shrink = 3/8 in.
                    - Example: To clear a 4-inch obstruction using 30-degree bends: Distance between bend marks = 4 inches * 2.0 = 8 inches. Conduit will shrink by 4 * 1/4 = 1 inch.
                """.trimIndent(),
                takeaways = listOf(
                    "Using 30-degree bends produces a 2.0 multiplier: distance between bend marks is exactly double the obstacle depth.",
                    "Always account for conduit shrink when running conduit toward a fixed wall or junction box.",
                    "NEC 358.26 limits total bends between pull points (boxes/fittings) to 360 degrees (four quarter bends maximum)."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "When fabricating an offset to clear a 5-inch obstruction using standard 30-degree bends, what is the distance between the two bend marks?",
                    options = listOf(
                        "10 inches (5 inches * 2.0 multiplier)",
                        "5 inches",
                        "20 inches",
                        "15 inches"
                    ),
                    correctOptionIndex = 0,
                    explanation = "For 30-degree bends, the cosecant multiplier is 2.0. Distance = 5 in * 2.0 = 10 inches."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-4",
                courseId = courseId,
                orderIndex = 3,
                title = "Service Panels, Grounding vs. Bonding & NEC Article 250",
                content = """
                    Article 250 is the most critical and scrutinized section of the NEC. Misunderstanding grounding versus bonding leads to deadly shock hazards and failed electrical inspections.

                    1. Grounding vs. Bonding:
                    - Grounding: Connecting electrical systems to the physical earth (dirt) via grounding electrode conductors (ground rods, concrete-encased Ufer ground, water pipes). Protects against lightning, surges, and stabilizes line-to-earth voltage.
                    - Bonding: Mechanically and electrically joining all non-current-carrying metal parts (metal boxes, conduit, equipment enclosures) to form an effective, low-impedance ground-fault current path back to the electrical source. This allows high current to flow during a fault, tripping the circuit breaker instantly!

                    2. The Main Bonding Jumper:
                    - Grounded neutral conductor and equipment grounding conductors must ONLY be bonded together at the main service disconnect!
                    - In subpanels (downstream distribution panels), the neutral bus must be isolated (floated) from the equipment grounding bus. Bonding neutral to ground in a subpanel causes dangerous objectional parallel neutral currents on conduit and metal pipes.
                """.trimIndent(),
                takeaways = listOf(
                    "Bonding is what trips the breaker during a ground fault; earth grounding alone cannot trip a breaker due to high soil resistance.",
                    "The neutral bus must NEVER be bonded to ground inside a subpanel—bond only at the main service equipment.",
                    "Ufer concrete-encased electrodes (rebar in footings) provide one of the lowest resistance grounding connections required by modern code."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In an electrical subpanel downstream from the main service disconnect, how must the neutral and ground buses be configured?",
                    options = listOf(
                        "Separated and isolated: neutral floating, grounding bus bonded to the metal enclosure",
                        "Tied together with a green bonding screw",
                        "Connected to each other using bare copper wire",
                        "Neither bus connected to any conductors"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Neutral must be isolated in subpanels to prevent return neutral currents from traveling across metal enclosures and grounding paths."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-5",
                courseId = courseId,
                orderIndex = 4,
                title = "Branch Circuit Protection: GFCI, AFCI & Dual-Function Breakers",
                content = """
                    Modern residential and commercial electrical codes require advanced electronic protection to eliminate electrocutions and structure fires.

                    1. Ground-Fault Circuit Interrupters (GFCI - NEC 210.8):
                    - Senses difference in current between hot (ungrounded) and neutral (grounded) conductors.
                    - If difference exceeds 4 to 6 milliamps (mA), GFCI opens within 25 milliseconds, preventing ventricular fibrillation.
                    - Required in kitchens, bathrooms, garages, outdoors, basements, crawlspaces, and laundry areas.

                    2. Arc-Fault Circuit Interrupters (AFCI - NEC 210.12):
                    - Monitors the current waveform for characteristic electrical signatures of arcing (parallel arcs from punctured wires, series arcs from loose screw terminals).
                    - Required in all residential living rooms, bedrooms, hallways, closets, dining rooms, and similar rooms to prevent electrical structural fires.

                    3. Dual-Function Breakers:
                    Combine both AFCI and GFCI protection into a single plug-in miniature circuit breaker in the panel.
                """.trimIndent(),
                takeaways = listOf(
                    "GFCIs protect human life from electrocution by tripping at a 4-6 mA current leakage.",
                    "AFCIs protect structures from fire caused by arcing loose wires or rodent-chewed cables.",
                    "Tamper-Resistant (TR) receptacles are mandated in all residential dwelling units to protect children from inserting objects."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "At what current leakage threshold is a standard Class A GFCI device calibrated to trip to protect human life?",
                    options = listOf(
                        "4 to 6 milliamps (0.004 to 0.006 A)",
                        "15 to 20 Amperes",
                        "500 milliamps",
                        "100 microamperes"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Class A GFCIs interrupt power at 4 to 6 mA leakage to earth to prevent fatal shock."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-6",
                courseId = courseId,
                orderIndex = 5,
                title = "Motor Branch Circuits, Magnetic Starters & Troubleshooting",
                content = """
                    Industrial and commercial facilities utilize three-phase induction motors controlled by magnetic contactors and Motor Control Centers (MCCs).

                    1. Motor Branch Circuit Components (NEC Article 430):
                    - Disconnect: Lockout-capable switch within sight of the motor.
                    - Branch Circuit Short-Circuit & Ground-Fault Protection: Inverse-time circuit breaker or dual-element time-delay fuses sized to handle high locked-rotor inrush current (typically 175% to 250% of Full Load Amps).
                    - Motor Controller: Magnetic contactor with electromagnetic coil.
                    - Overload Protection: Thermal bimetallic or solid-state overload relay sized to protect motor windings from sustained over-current (typically 115% to 125% of motor nameplate FLA).

                    2. 3-Wire Control Circuit (Start/Stop Pushbuttons):
                    - Normally Closed (NC) Stop button wired in series.
                    - Normally Open (NO) Start button wired in parallel with a holding/seal-in auxiliary contact (M contact) on the contactor.
                    - Low Voltage Release (LVR): If power fails, contactor drops out and will not restart automatically when power restores until Start is pressed again.
                """.trimIndent(),
                takeaways = listOf(
                    "Overload relays protect motor windings from burning out under sustained mechanical overload; breakers protect against short circuits.",
                    "In 3-wire control, the seal-in auxiliary contact across the Start button maintains coil power after releasing the momentary button.",
                    "Never size motor branch circuit breakers based on wire ampacity alone; inrush current requires Article 430 sizing rules."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In a standard magnetic motor starter control circuit, what is the role of the seal-in (holding) auxiliary contact?",
                    options = listOf(
                        "To maintain power to the contactor coil after the momentary Start pushbutton is released",
                        "To shut down the motor if the voltage exceeds 10,000 volts",
                        "To physically lock the motor shaft when stopped",
                        "To step down the voltage from 480V to 12V"
                    ),
                    correctOptionIndex = 0,
                    explanation = "The holding contact latches around the momentary Start contact, sustaining current to the coil until Stop is pressed."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "NEC Article 90", "The introductory article establishing the legal scope, intent, and enforcement authority of the National Electrical Code.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Ohm's & Power Law", "E = I * R and P = E * I. In three-phase, total power equals Volts * Amps * 1.732 * Power Factor.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Conduit 30° Offset", "A pipe bend with a 2.0 multiplier; distance between marks is twice the offset depth, shrinking by 1/4 in. per inch.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Bonding vs. Grounding", "Grounding connects to the earth; bonding creates an effective low-impedance path to trip breakers during faults.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "GFCI vs. AFCI", "GFCI protects people from electrocution at 4-6 mA; AFCI protects structures from fire by detecting waveform arcing.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Motor Overload Relay", "Protective device calibrated to 115-125% FLA to guard motor windings against sustained running over-current.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(UUID.randomUUID().toString(), courseId, 0, "What is the maximum total number of conduit bends permitted by the NEC between pull points?", listOf("360 degrees (the equivalent of four 90-degree bends)", "180 degrees only", "720 degrees", "There is no limit if wire pulling lubricant is used"), 0, "NEC 358.26 limits bends to 360 degrees between pull boxes to prevent conductor damage."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 1, "Under the National Electrical Code, where is the neutral conductor permitted to be bonded to ground?", listOf("Only at the service disconnecting means (main panel)", "In every subpanel throughout the building", "Inside every wall receptacle junction box", "Nowhere in the building"), 0, "Bonding neutral to ground at subpanels creates dangerous parallel neutral currents on grounds."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 2, "How is the distance between bend marks calculated for an offset using 30-degree bends?", listOf("Multiply the offset height by 2.0", "Multiply the offset height by 1.414", "Divide the height by 2.6", "The distance is always 6 inches"), 0, "Cosecant of 30 degrees is 2.0."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 3, "What electrical condition does an Arc-Fault Circuit Interrupter (AFCI) detect?", listOf("Unintended electrical arcing waveforms caused by damaged insulation or loose connections", "Water leakage into outdoor boxes", "Overheating caused by too many light bulbs", "Low frequency vibrations"), 0, "AFCIs recognize the distinctive high-frequency current signatures of dangerous arcs."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 4, "What is the primary function of equipment bonding in an electrical distribution system?", listOf("To provide a low-impedance fault path to allow sufficient current to quickly trip the circuit breaker", "To send stray electricity harmlessly into deep soil", "To make conduit shiny and prevent rust", "To increase power factor"), 0, "Low-impedance bonding allows massive fault current to flow, tripping overcurrent devices immediately."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 5, "In an industrial motor starter, what device protects motor windings from burning under continuous mechanical drag?", listOf("Thermal or electronic overload relay", "Main circuit breaker instantaneous trip magnetic coil", "Disconnect handle fuse clip", "Holding auxiliary contact"), 0, "Overload relays monitor running current to protect windings from sustained thermal damage.")
        )

        return Course(
            id = courseId,
            title = "Residential & Commercial Electrician Principles (NEC)",
            topic = "Electrician",
            description = "Complete electrician trade curriculum covering the National Electrical Code, electrical math, 3-phase power, conduit bending geometry, service bonding, GFCI/AFCI, and motor controls.",
            tier = ScopeTier.MEDIUM,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }

    fun createCarpentryCourse(): Course {
        val courseId = "trade-carpentry-01"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Architectural Blueprint Reading, Scales & Elevation Plans",
                content = """
                    Carpenters translate 2D architectural drawings and structural engineering blueprints into 3D physical structures.

                    1. Types of Architectural Drawings:
                    - Plot / Site Plan: Shows property boundaries, setbacks, building orientation, elevation contour lines, and utility easements.
                    - Foundation Plan: Footing dimensions, stem walls, slab thicknesses, anchor bolt placement, and beam pocket locations.
                    - Floor Plans: Wall dimensions, room layouts, door/window schedules, rough openings, and partition locations.
                    - Elevation Views: Exterior view of each face (North, South, East, West) showing building heights, roof pitches, siding materials, and grade lines.
                    - Section & Detail Drawings: High-magnification cuts through walls, foundations, and eave overhangs revealing framing members, fasteners, and flashing details.

                    2. Architectural Scale Rules:
                    Floor plans typically use 1/4" = 1'-0" scale (one quarter inch on the drawing represents one foot in the field).
                """.trimIndent(),
                takeaways = listOf(
                    "Always cross-reference floor plan dimensions with structural detail drawings and window/door rough opening schedules.",
                    "Verify benchmark elevation heights before laying out mudsills and foundation anchor points.",
                    "Rough openings (R.O.) must provide manufacturer-specified clearance around window and door jambs for squaring and insulation."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "On a standard architectural floor plan drawn to 1/4\" = 1'-0\" scale, what physical distance does a 3-inch measurement on paper represent?",
                    options = listOf(
                        "12 feet in real-world construction (3 / 0.25 = 12)",
                        "3 feet",
                        "24 feet",
                        "6 inches"
                    ),
                    correctOptionIndex = 0,
                    explanation = "At 1/4 inch per foot, 3 inches contains twelve 1/4-inch increments, representing 12 feet."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "Dimensional Lumber, Engineered Wood & Structural Fasteners",
                content = """
                    Understanding wood species, moisture content, and engineered lumber properties ensures structural integrity and code compliance.

                    1. Nominal vs. Actual Lumber Dimensions:
                    Dimensional framing lumber is surfaced and seasoned (S-DRY), reducing dimensions from rough green cuts:
                    - 2x4 nominal = 1-1/2" x 3-1/2" actual.
                    - 2x6 nominal = 1-1/2" x 5-1/2" actual.
                    - 2x8 nominal = 1-1/2" x 7-1/4" actual.
                    - 2x10 nominal = 1-1/2" x 9-1/4" actual.
                    - 2x12 nominal = 1-1/2" x 11-1/4" actual.

                    2. Engineered Lumber:
                    - LVL (Laminated Veneer Lumber): High-strength glued veneer billets for long-span headers and primary carrying beams without warping.
                    - Glulam (Glued Laminated Timber): Layered dimension lumber for massive architectural beams.
                    - I-Joists (TJI): Engineered wood flanges with OSB structural webs, delivering lightweight, flat floor platforms over long spans.

                    3. Fastener Mechanics:
                    Framing nails are sized by 'pennyweight' (d). 16d common nails (0.162" diameter x 3-1/2") or 16d box nails are standard for structural framing. Hot-dip galvanized nails must be used in pressure-treated lumber to resist copper chemical corrosion.
                """.trimIndent(),
                takeaways = listOf(
                    "A nominal 2x4 measures 1-1/2\" x 3-1/2\" actual; always compute framing rough openings from actual dimensions.",
                    "Pressure-treated mudsills require hot-dip galvanized or stainless steel fasteners to prevent ACQ chemical corrosion.",
                    "Engineered LVL beams resist crowning, twisting, and shrinkage inherent in solid sawn green timber."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What are the exact actual dressed dimensions of a standard nominal 2x6 framing stud?",
                    options = listOf(
                        "1-1/2 inches by 5-1/2 inches",
                        "2 inches by 6 inches exact",
                        "1-3/4 inches by 5-3/4 inches",
                        "1-1/4 inches by 5-1/4 inches"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Surfaced dimensional lumber is 1/2 inch smaller in thickness and width (up to 6 inches nominal): 1-1/2\" x 5-1/2\"."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "Floor Framing: Mudsills, Joists, Subflooring & Bridging",
                content = """
                    The floor platform creates a level, square structural diaphragm supporting all upper walls and roof loads.

                    1. Mudsill Installation:
                    - Mudsills must be preservative pressure-treated lumber anchored to the foundation with 1/2" or 5/8" anchor bolts embedded a minimum of 7 inches in concrete.
                    - Sill sealer foam gasket installed beneath mudsill creates an air barrier and capillary break.
                    - Squaring the foundation: Use the 3-4-5 Pythagorean theorem rule or diagonal check (diagonal distances corner-to-corner must match exactly).

                    2. Floor Joists & Layout:
                    - Spaced 16 inches or 24 inches on center (O.C.) so 4x8 subfloor sheets land centered on joist flanges.
                    - Crown all lumber: Place the crowned (bowed) edge facing UP so floor weight flattens the crown.
                    - Solid blocking or bridging installed mid-span prevents joist rotation and distributes point loads.

                    3. Subfloor Sheathing:
                    Install 3/4" tongue-and-groove (T&G) OSB or plywood. Stagger seams in a running brick pattern, leave 1/8" expansion gap at ends, apply polyurethane construction adhesive to joists, and fasten with 8d ring-shank nails to eliminate floor squeaks.
                """.trimIndent(),
                takeaways = listOf(
                    "Always crown framing joists facing upward so gravity deflection settles them flat.",
                    "Apply continuous subfloor polyurethane adhesive along joists before nailing to prevent squeaks.",
                    "Verify squareness of the floor platform by comparing diagonal corner measurements before sheeting."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "Why must dimensional floor joists always be crowned with the natural bow facing UP?",
                    options = listOf(
                        "So that dead and live loads will deflect the crown downward toward a flat, level plane",
                        "To allow water to drain down the center of the house",
                        "Because building codes mandate that curved joists cannot be nailed",
                        "To make the subfloor bounce more when walking"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Crowning upward ensures structural gravity load works against the bow, producing a level floor rather than a sagging dip."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-4",
                courseId = courseId,
                orderIndex = 3,
                title = "Wall Framing: Plates, Studs, Headers & Rough Openings",
                content = """
                    Platform framing erects vertical load-bearing walls that transfer gravity loads and resist lateral wind and seismic shear forces.

                    1. Wall Anatomy:
                    - Bottom Plate (Sole Plate): Nailed flat to the subfloor.
                    - Studs: Common vertical members spaced 16" O.C. (precisely 92-5/8" for standard 8-foot ceilings with double top plates).
                    - Double Top Plate: Upper plate overlaps lower plate by at least 4 feet at joints and laps corners to tie intersecting walls together.
                    - King Studs: Full-height studs flanking window and door rough openings.
                    - Jack Studs (Trimmers): Shortened studs nailed inside king studs directly supporting the load of the header.
                    - Header: Solid or built-up beam bridging the opening. In bearing walls, headers support joists and rafters from above.
                    - Cripple Studs: Short framing studs above headers or below window sills.

                    2. Wall Sheathing & Shear Resistance:
                    Plywood or OSB exterior sheathing fastened with edge nailing creates shear walls, preventing building racking under lateral wind and earthquake loads.
                """.trimIndent(),
                takeaways = listOf(
                    "Jack studs (trimmers) carry the full vertical gravity load of the header down to the bottom plate.",
                    "Double top plates must overlap by at least 4 feet at splices and interlock at all corner intersections.",
                    "Wall sheathing provides the primary structural lateral resistance (shear strength) against wind and seismic racking."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In residential wall framing around a window rough opening, what is the role of the jack stud (trimmer)?",
                    options = listOf(
                        "To sit directly under the header and transfer its vertical structural load down to the bottom plate",
                        "To hold drywall screws on the interior face only",
                        "To act as a temporary brace until the roof is built",
                        "To hold the window glass in place"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Jack studs provide bearing support directly beneath headers, carrying loads down through the framing."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-5",
                courseId = courseId,
                orderIndex = 4,
                title = "Roof Framing: Common Rafters, Hip/Valley Rafters & Trusses",
                content = """
                    Roof framing combines trigonometric geometry with structural carpentry to create pitch, weather shedding, and overhead space.

                    1. Roof Geometry & Terminology:
                    - Span: Total horizontal distance from outside wall to outside wall.
                    - Run: Half the span (for symmetrical gable roofs); the horizontal distance from plate to centerline of ridge.
                    - Rise: Total vertical height the roof ascends.
                    - Pitch / Slope: Ratio of rise per 12 inches of unit run (e.g., 6/12 pitch rises 6 inches vertically for every 12 inches horizontal run).

                    2. Rafter Cuts:
                    - Plumb Cut: Vertical cut at the ridge board.
                    - Birdsmouth: Cutout notch resting on the top wall plate, consisting of the level seat cut (bearing) and the vertical heel cut. Seat cut cannot exceed width of top plate.
                    - Tail Cut: Overhang cut forming eaves.

                    3. Engineered Roof Trusses:
                    Factory-built triangular frameworks connected with metal gang-nail plates. Trusses carry extreme loads over vast spans without interior load-bearing walls. Never notch, cut, or drill truss webs or chords!
                """.trimIndent(),
                takeaways = listOf(
                    "Roof pitch is expressed as inches of vertical rise per 12 inches of horizontal run (e.g., 4/12, 6/12, 8/12).",
                    "The birdsmouth seat cut provides level bearing on the wall top plate without over-cutting and weakening the rafter.",
                    "Never cut, notch, or modify engineered roof trusses without stamped engineering approval."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the notch cut into a common roof rafter to allow it to bear flat on the double top plate called?",
                    options = listOf(
                        "A birdsmouth cut",
                        "A dovetail joint",
                        "A mortise and tenon",
                        "A bevel chamfer"
                    ),
                    correctOptionIndex = 0,
                    explanation = "A birdsmouth cut consists of a level seat cut and vertical heel cut that sits flush on the wall top plate."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-6",
                courseId = courseId,
                orderIndex = 5,
                title = "Exterior Building Envelope: Weather Barriers, Flashing & Insulation",
                content = """
                    Framing must be permanently protected against rain intrusion, wind-driven moisture, and thermal transfer to prevent structural dry rot and mold.

                    1. Water-Resistive Barriers (WRB - Housewrap):
                    Spun-bonded polyolefin wrap installed shingle-fashion (overlapping lower sheets by at least 6 inches, vertical seams by 12 inches). All seams taped with approved acrylic flashing tape. WRBs are vapor-permeable: they stop bulk water while allowing internal wall moisture vapor to escape.

                    2. Window & Door Flashing Integration:
                    Water flows downward; flashing layers must follow shingle-lap drainage planes:
                    - Sill flashing installed first (pan flashing with end dams).
                    - Window installed over sill flashing and bedded in sealant at head and jambs.
                    - Jamb flashing taped over window side fins.
                    - Head flashing (drip cap) installed over window top, with housewrap lapping over the drip cap tape.

                    3. Continuous Exterior Insulation:
                    Rigid foam (XPS, EPS, Polyiso) over sheathing eliminates thermal bridging through wooden studs, meeting modern energy conservation codes.
                """.trimIndent(),
                takeaways = listOf(
                    "Always lap flashing and housewrap shingle-style from bottom to top so gravity sheds water outward.",
                    "Window head flashing (drip cap) must be sealed under the upper housewrap flap to prevent water pooling over windows.",
                    "Continuous insulation stops thermal bridging where wooden studs conduct cold and heat through walls."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What fundamental rule dictates the installation sequence of window flashing and housewrap?",
                    options = listOf(
                        "Shingle-lap drainage: upper layers must always lap over lower layers to direct water outward",
                        "Upper layers must tuck underneath lower layers to trap moisture",
                        "Flashing should only be installed on the interior drywall side",
                        "Nailing housewrap backwards prevents ultraviolet degradation"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Shingle-lap layering ensures water running down by gravity is naturally directed to the exterior of each layer."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Nominal vs. Actual", "Framing lumber is seasoned and surfaced; a 2x4 is actually 1-1/2\" x 3-1/2\", and a 2x6 is 1-1/2\" x 5-1/2\".", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Mudsill Anchor Bolts", "1/2\" or 5/8\" steel bolts embedded minimum 7\" in concrete footings to secure the building against seismic and wind uplift.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Joist Crown", "The natural curvature or upward arch of dimensional lumber; joists must always be installed with the crown pointing UP.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "King & Jack Studs", "King studs are full-height wall members; jack studs (trimmers) sit inside them to directly support the header beam.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Birdsmouth Cut", "The notch cut into rafters providing a flat seat on the wall top plate while maintaining structural integrity.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Shingle-Lap Flashing", "The weatherproofing principle where higher flashing elements lap over lower ones to shed gravity water outward.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(UUID.randomUUID().toString(), courseId, 0, "What is the actual thickness and width of a nominal 2x10 joist?", listOf("1-1/2 inches by 9-1/4 inches", "2 inches by 10 inches exact", "1-3/4 inches by 9-1/2 inches", "1-1/4 inches by 9-3/4 inches"), 0, "Actual lumber dimensions are 1-1/2\" x 9-1/4\"."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 1, "Which fastener type is legally required when framing into pressure-treated lumber?", listOf("Hot-dip galvanized or stainless steel fasteners", "Bright un-galvanized interior finish nails", "Aluminum wire staples", "Plastic dry-wall screws"), 0, "Chemical preservatives in pressure-treated wood corrode standard steel nails."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 2, "How do framing carpenters confirm a rectangular floor platform is perfectly square?", listOf("By measuring opposite diagonal corners; equal diagonal measurements prove the corners are 90 degrees", "By sighting along one edge with one eye closed", "By laying a 6-inch torpedo level across the rim joist", "By measuring wall thickness only"), 0, "Matching diagonal measurements mathematically confirm square corners."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 3, "In a bearing wall, what framing member directly supports the header above a door opening?", listOf("Jack studs (trimmers)", "Cripple studs", "Shear panel clips", "Corner partition backers"), 0, "Jack studs bear the header's downward structural load."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 4, "A roof with a 6/12 pitch rises how many inches vertically for every 12 inches of horizontal run?", listOf("6 inches", "12 inches", "2 inches", "24 inches"), 0, "Pitch indicates rise in inches per 12 inches of unit run."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 5, "Why is housewrap designed to be vapor-permeable while shedding bulk liquid water?", listOf("To allow internal indoor humidity to escape the wall cavity while preventing rain from entering", "To let outside rain soak into the fiberglass insulation", "To make siding slide more easily", "To allow wind to blow directly into the living room"), 0, "Breathable housewraps shed liquid water while releasing trapped moisture vapor to prevent rot.")
        )

        return Course(
            id = courseId,
            title = "Carpentry & Structural Framing Mastery",
            topic = "Carpentry",
            description = "Master residential and commercial carpentry: architectural blueprint reading, dimensional lumber math, floor platforms, wall layout, roof rafter cuts, and weather envelope flashing.",
            tier = ScopeTier.MEDIUM,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }

    fun createWeldingCourse(): Course {
        val courseId = "trade-welding-01"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Welding Metallurgy, Metal ID & Heat-Affected Zone (HAZ)",
                content = """
                    Welding joins metals by applying intense heat to melt base metals and filler alloys into a unified crystalline structure.

                    1. Base Metals & Carbon Content:
                    - Low Carbon (Mild) Steel (<0.30% Carbon): Highly weldable without preheat; used in structural shapes (A36).
                    - Medium & High Carbon Steels: Prone to cracking due to martensite formation during rapid cooling. Requires preheat and controlled interpass temperatures.
                    - Stainless Steels (304, 316): Austenitic, non-magnetic, susceptible to carbide precipitation and sensitization if overheated.

                    2. The Heat-Affected Zone (HAZ):
                    The portion of base metal adjacent to the weld pool that was not melted, but had its microstructure and mechanical properties altered by high heat. Cooling rates dictate whether the HAZ becomes ductile or brittle.
                """.trimIndent(),
                takeaways = listOf(
                    "The Heat-Affected Zone (HAZ) is the most common site of crack initiation due to localized grain growth and residual stress.",
                    "Preheating heavy steel sections slows the cooling rate, preventing brittle martensite formation.",
                    "Low hydrogen electrodes (E7018) prevent hydrogen-induced underbead cracking in structural welds."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the primary danger in the Heat-Affected Zone (HAZ) of a welded high-strength steel joint?",
                    options = listOf(
                        "Rapid cooling can form brittle martensite, leading to delayed stress cracking",
                        "The steel permanently turns into copper",
                        "The joint becomes too flexible and melts at room temperature",
                        "The magnetic field flips direction"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Rapid quenching of high-temperature steel creates hard, brittle martensite prone to cracking under load."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "SMAW (Stick) & GMAW (MIG) Operating Parameters",
                content = """
                    Shielded Metal Arc Welding (SMAW) and Gas Metal Arc Welding (GMAW) are the primary workhorses of structural fabrication, repair, and pipe installation.

                    1. SMAW (Stick Welding):
                    Uses a consumable flux-coated electrode. Flux decomposes in the arc to produce a shielding gas cloud and liquid slag protecting molten weld metal from atmospheric oxygen and nitrogen.
                    - Electrode Classification (e.g., E7018):
                      * E: Electrode.
                      * 70: Minimum tensile strength in thousands of PSI (70,000 PSI).
                      * 1: Welding positions (1 = all positions: flat, horizontal, vertical, overhead).
                      * 8: Coating composition and operating current (low hydrogen iron powder, DCEP or AC).

                    2. GMAW (MIG Welding):
                    Continuously feeds solid wire through a gun with external shielding gas (75% Argon / 25% CO2 for carbon steel; 100% Argon for aluminum).
                    - Short-Circuit Transfer: Low heat, thin gauge, prone to cold lap if used on thick plate.
                    - Spray Transfer: High voltage and wire feed speed; tiny droplets sprayed across arc. Flat/horizontal only; deep penetration on heavy plate.
                """.trimIndent(),
                takeaways = listOf(
                    "In AWS electrode numbering like E7018, the first two digits represent minimum tensile strength (70,000 PSI).",
                    "Store E7018 low-hydrogen electrodes in a heated rod oven at 250°F once opened to prevent moisture absorption.",
                    "Short-circuit GMAW transfer must not be used on critical structural members due to risk of lack of fusion (cold lap)."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In the AWS SMAW electrode classification 'E7018', what does the number '70' signify?",
                    options = listOf(
                        "Minimum tensile strength of 70,000 pounds per square inch (PSI)",
                        "Electrode diameter in millimeters",
                        "Recommended welding current of 70 Amps",
                        "Manufacture date in the year 1970"
                    ),
                    correctOptionIndex = 0,
                    explanation = "The first two digits indicate the minimum tensile strength of the deposited weld metal in thousands of PSI."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "AWS Welding Symbols, Joint Geometry & Non-Destructive Testing (NDT)",
                content = """
                    Blueprints specify weld details using American Welding Society (AWS A2.4) standard symbols.

                    1. Anatomy of an AWS Welding Symbol:
                    - Reference Line: Horizontal backbone. Elements placed BELOW the reference line indicate welds on the ARROW SIDE of the joint. Elements ABOVE the reference line indicate welds on the OTHER SIDE.
                    - Arrow: Points directly to the joint to be welded.
                    - Weld Symbols: Triangle (Fillet weld), Two parallel lines (Square groove), V-shape (V-groove).
                    - Tail: Contains specification details, welding process, or filler metal requirements.

                    2. Non-Destructive Testing (NDT):
                    - Visual Inspection (VT): Most common; checks profile, undercut, porosity, and overlap.
                    - Dye Penetrant Testing (PT): Capillary action draws colored dye into surface-breaking cracks.
                    - Magnetic Particle Testing (MT): Detects surface and near-surface cracks in ferromagnetic steels.
                    - Ultrasonic Testing (UT) & Radiography (RT - X-Ray): Volumetric examination revealing internal lack of penetration, slag inclusions, and subsurface voids.
                """.trimIndent(),
                takeaways = listOf(
                    "Symbols below the reference line mean weld the arrow side; symbols above mean weld the other side.",
                    "Visual inspection (VT) is the primary first-line quality control method before any NDT method.",
                    "Ultrasonic testing (UT) uses high-frequency sound waves to accurately detect internal weld defects without radiation hazard."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "On an AWS standard welding symbol blueprint, what does a fillet weld symbol placed BELOW the horizontal reference line indicate?",
                    options = listOf(
                        "The fillet weld must be made on the arrow side of the joint",
                        "The weld must be made on the opposite (other) side only",
                        "The weld must be done underwater",
                        "The weld is optional and can be omitted"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Symbols below the reference line designate welds applied directly to the arrow side of the joint."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Heat-Affected Zone (HAZ)", "Base metal adjacent to the weld pool whose crystalline microstructure was altered by heat without melting.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "E7018 Electrode", "Low-hydrogen iron powder stick electrode providing 70,000 PSI tensile strength in all positions; must be kept dry.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "GMAW Shielding Gas", "Gas mixture (typically 75% Ar / 25% CO2) that protects the molten puddle from atmospheric oxygen and nitrogen.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "AWS Arrow vs. Other Side", "Symbols below the reference line apply to the arrow side; symbols above the reference line apply to the other side.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Dye Penetrant Testing (PT)", "An NDT method applying liquid penetrant and developer to reveal microscopic surface cracks via capillary action.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Volumetric NDT (UT & RT)", "Ultrasonic and Radiographic methods that peer deep inside weld passes to find internal slag and lack of fusion.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(UUID.randomUUID().toString(), courseId, 0, "Why must E7018 low-hydrogen electrodes be stored in a heated rod oven after opening?", listOf("To prevent atmospheric moisture absorption that causes hydrogen-induced cracking in welds", "To keep the flux from becoming brittle", "To keep the steel core magnetized", "To reduce the required amperage by half"), 0, "Moisture in the flux releases hydrogen into the puddle, causing delayed cold cracking."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 1, "What is the primary function of shielding gas in GMAW (MIG) welding?", listOf("To shield the molten weld puddle from atmospheric oxygen and nitrogen contamination", "To cool the gun handle for operator comfort", "To ignite the electrical arc", "To add carbon into the weld metal"), 0, "Shielding gas prevents atmospheric porosity and weld embrittlement."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 2, "On an AWS welding blueprint, which side of the joint is welded when the symbol appears ABOVE the reference line?", listOf("The other side (opposite the arrow)", "The arrow side", "Both sides equally", "Neither side"), 0, "Above the reference line designates other side welds.")
        )

        return Course(
            id = courseId,
            title = "Welding Processes & Industrial Metallurgy",
            topic = "Welding",
            description = "Master industrial welding: base metal metallurgy, heat-affected zone mechanics, SMAW low-hydrogen techniques, GMAW gas mixtures, AWS blueprint symbols, and NDT inspection.",
            tier = ScopeTier.SHORT,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }

    fun createFirstAidCourse(): Course {
        val courseId = "trade-firstaid-01"
        val sections = listOf(
            CourseSection(
                id = "$courseId-sec-1",
                courseId = courseId,
                orderIndex = 0,
                title = "Emergency Scene Safety, Triage & Primary Assessment",
                content = """
                    Emergency response begins with ensuring rescuer safety before touching any patient.

                    1. Scene Safety & Situational Awareness:
                    - Check for hazards: Downed electrical power lines, chemical vapor spills, moving traffic, fire, structural collapse, or violent bystanders.
                    - Put on Personal Protective Equipment (PPE): Medical nitrile gloves, eye shield, and pocket mask with one-way filter valve.
                    - Shout for help and activate the Emergency Response System (dial 911 or dispatch emergency medical services). Direct a specific bystander: 'You in the blue shirt, call 911 and bring me an AED!'

                    2. Primary Assessment (The ABCs / CAB):
                    - Check responsiveness: Tap shoulders firmly and shout 'Are you okay?'.
                    - Check Breathing & Pulse: Simultaneously scan chest rise and palpate carotid pulse for at least 5 but no more than 10 seconds.
                    - If unresponsive with no normal breathing (or only agonal gasps) and no definite pulse: Immediately initiate High-Quality CPR beginning with chest compressions!
                """.trimIndent(),
                takeaways = listOf(
                    "Never become a secondary victim—always verify scene safety before entering an emergency environment.",
                    "Direct specific individuals to call 911 and retrieve an AED to prevent bystander apathy.",
                    "Agonal gasping is NOT normal breathing; it is a sign of cardiac arrest requiring immediate chest compressions."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "When assessing an unresponsive adult who is only producing occasional abnormal agonal gasps and has no pulse, what must you do immediately?",
                    options = listOf(
                        "Begin chest compressions immediately; agonal gasps indicate sudden cardiac arrest",
                        "Wait 10 minutes to see if normal breathing resumes",
                        "Place the patient in a chair and offer water",
                        "Assume the patient is sleeping"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Agonal gasps are an ineffective reflex seen in cardiac arrest; immediate chest compressions are mandatory."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-2",
                courseId = courseId,
                orderIndex = 1,
                title = "High-Quality CPR & Automated External Defibrillator (AED) Operations",
                content = """
                    Sudden cardiac arrest is an electrical malfunction of the heart causing ventricular fibrillation (VF). Early CPR circulates oxygenated blood to the brain, and early defibrillation resets the heart's rhythm.

                    1. High-Quality CPR Metrics (Adult):
                    - Compression Rate: 100 to 120 compressions per minute (to the beat of 'Stayin' Alive').
                    - Compression Depth: At least 2 inches (5 cm), but not exceeding 2.4 inches (6 cm).
                    - Allow Complete Chest Recoil: Do not lean on the chest between compressions. Full recoil allows blood to refill heart chambers.
                    - Minimize Interruptions: Keep pauses under 10 seconds.
                    - Compression-to-Ventilation Ratio: 30 compressions to 2 rescue breaths (each delivered over 1 second, watching for visible chest rise).

                    2. Automated External Defibrillator (AED):
                    - Turn power ON immediately upon arrival and follow voice prompts.
                    - Apply pads to bare, dry chest: Upper right chest (below collarbone) and lower left lateral ribs (axillary line).
                    - Clear the patient during heart rhythm analysis and prior to delivering shock.
                    - Resume CPR compressions immediately after a shock is delivered—do not pause to check pulse.
                """.trimIndent(),
                takeaways = listOf(
                    "Compress hard and fast: 100-120 compressions/min, at least 2 inches deep, allowing complete chest recoil.",
                    "Apply an AED as soon as available; defibrillation is the only treatment that converts ventricular fibrillation to a normal rhythm.",
                    "Immediately resume CPR compressions after a shock is delivered without delay."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "What is the recommended compression rate and depth for high-quality adult CPR according to AHA and ILCOR guidelines?",
                    options = listOf(
                        "100 to 120 compressions per minute at a depth of at least 2 inches (5 cm)",
                        "50 compressions per minute at a depth of 1 inch",
                        "200 compressions per minute at a depth of 4 inches",
                        "Rate and depth do not matter as long as breaths are given"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Guidelines mandate 100-120 compressions/minute at 2 to 2.4 inches depth for effective perfusion."
                ),
                isCompleted = false
            ),
            CourseSection(
                id = "$courseId-sec-3",
                courseId = courseId,
                orderIndex = 2,
                title = "Severe Bleeding Control, Tourniquets & Shock Management",
                content = """
                    Uncontrolled arterial bleeding can cause exsanguination and death within 3 to 5 minutes.

                    1. Hemorrhage Control Steps (Stop the Bleed):
                    - Direct Pressure: Apply firm, continuous direct pressure over the wound using sterile gauze or clean cloth.
                    - Wound Packing: For deep cavity wounds in junctional areas (groin, armpit), pack hemostatic or standard gauze tightly into the wound to the bone, maintaining direct two-handed pressure.
                    - Commercial Windlass Tourniquet (C-A-T / SOFTT): For life-threatening extremity arterial bleeding (spurting bright red blood):
                      * Apply 2 to 3 inches above the wound (never over a joint like an elbow or knee).
                      * Pull band completely tight before turning windlass.
                      * Twist windlass rod until all arterial bleeding and distal pulse stop completely.
                      * Lock windlass in clip and write the exact application time on the time strap (e.g., '14:32').
                      * Never loosen or remove a tourniquet once applied; only hospital trauma surgeons may remove it.

                    2. Shock (Hypoperfusion) Management:
                    Lay patient flat, cover with a warm blanket to maintain body temperature, and elevate legs 12 inches if no spinal trauma. Do NOT give food or water.
                """.trimIndent(),
                takeaways = listOf(
                    "Apply tourniquets 2-3 inches above bleeding extremity wounds, twist windlass until bleeding stops, and mark the exact time.",
                    "Tourniquets save lives and do not cause limb loss when managed under hospital trauma timelines.",
                    "Hypothermia accelerates blood coagulopathy; always keep bleeding shock patients covered and warm."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "When applying a commercial windlass tourniquet to an arm with life-threatening arterial bleeding, how tight should the windlass be turned?",
                    options = listOf(
                        "Until the bright red arterial bleeding stops and the distal pulse is eliminated",
                        "Just until the patient complains of discomfort",
                        "One half turn only regardless of bleeding",
                        "Loosely enough so two fingers can easily slide under the band"
                    ),
                    correctOptionIndex = 0,
                    explanation = "The windlass must be tightened until arterial blood flow and distal pulse are fully halted."
                ),
                isCompleted = false
            )
        )

        val flashcards = listOf(
            Flashcard(UUID.randomUUID().toString(), courseId, "Agonal Gasps", "Abnormal, ineffective reflexive gasping occurring in sudden cardiac arrest; must be treated with immediate CPR compressions.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "High-Quality CPR", "Chest compressions at 100-120 per minute, at least 2 inches deep, allowing full chest recoil with minimal interruptions.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "AED Operation", "Turn on, attach pads to bare chest (upper right / lower left), clear for analysis, deliver shock if prompted, resume CPR immediately.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Windlass Tourniquet", "A life-saving arterial bleeding strap applied 2-3 inches above extremity wounds, tightened until bleeding stops, marked with time.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Hemostatic Wound Packing", "Tightly packing gauze deep into junctional bleeding wounds (groin, axilla) with sustained 3-minute direct manual pressure.", false),
            Flashcard(UUID.randomUUID().toString(), courseId, "Hypovolemic Shock", "Severe circulatory collapse caused by massive blood loss; treated by stopping bleeding, laying flat, and thermal blanket warmth.", false)
        )

        val examQuestions = listOf(
            ExamQuestion(UUID.randomUUID().toString(), courseId, 0, "What is the very first action a rescuer must take before approaching an injured or collapsed individual?", listOf("Survey the scene to ensure it is completely safe for the rescuer to enter", "Begin immediate mouth-to-mouth ventilations", "Search the patient's pockets for identification", "Drag the patient into a vehicle"), 0, "Scene safety prevents the rescuer from becoming an injured victim."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 1, "What should be done immediately after an AED delivers a shock to a victim in cardiac arrest?", listOf("Immediately resume chest compressions starting with 30 compressions", "Stand back and wait 5 minutes for the AED to speak", "Check for a pulse for 2 minutes", "Remove the AED pads from the chest"), 0, "Guidelines require resuming CPR compressions immediately after shock delivery."),
            ExamQuestion(UUID.randomUUID().toString(), courseId, 2, "Once a tourniquet is applied to an arm to stop catastrophic arterial bleeding, when should it be loosened?", listOf("Never in the field; it should only be removed by a physician in a trauma hospital", "Every 15 minutes to let blood flow back into the hand", "As soon as the patient feels pain", "When the ambulance arrives at the scene"), 0, "Loosening in the field causes fatal hemorrhage and releases toxic shock metabolites.")
        )

        return Course(
            id = courseId,
            title = "Emergency First Aid, CPR & AED Life Support",
            topic = "First Aid",
            description = "Crucial life-safety curriculum: scene assessment, high-quality CPR (100-120 bpm), AED defibrillation, Stop-the-Bleed tourniquet application, and hypovolemic shock treatment.",
            tier = ScopeTier.SHORT,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }
}
