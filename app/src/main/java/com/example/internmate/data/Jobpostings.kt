package com.example.internmate.data

// --- 1. DATA MODEL ---
data class JobPosting(
    val id: Int,
    val title: String,
    val company: String,
    val type: String, // "Internship", "Full-time", "Part-time"
    val experienceLevel: String, // "Beginner", "Intermediate", "Expert"
    val requiredSkills: Set<String>
)

// --- 2. SAMPLE DATA (Your "Fake" AI/Backend) ---
val allJobPostings = listOf(
    // --- ANDROID INTERNS (BEGINNER) ---
    JobPosting(
        id = 1,
        title = "Android Developer Intern (Compose)",
        company = "TechCorp",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "XML", "Git")
    ),
    JobPosting(
        id = 2,
        title = "Android App Intern (Legacy)",
        company = "OldGuard Solutions",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Java", "XML", "Git")
    ),
    JobPosting(
        id = 3,
        title = "Mobile App Intern (Firebase)",
        company = "StartupFast",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Kotlin", "Java", "Firebase")
    ),
    JobPosting(
        id = 4,
        title = "Kotlin Developer Intern",
        company = "CodeBase",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Kotlin", "Git")
    ),
    JobPosting(
        id = 5,
        title = "Part-time Android UI Developer",
        company = "PixelPerfect",
        type = "Part-time",
        experienceLevel = "Beginner",
        requiredSkills = setOf("XML", "UI/UX Design")
    ),
    JobPosting(
        id = 6,
        title = "Summer Android Intern",
        company = "MobileFirst",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Kotlin", "XML", "REST APIs")
    ),
    JobPosting(
        id = 7,
        title = "Java Mobile Intern",
        company = "Legacy Apps",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Java", "Git", "XML")
    ),
    JobPosting(
        id = 8,
        title = "Compose UI Intern",
        company = "DesignForward",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "UI/UX Design")
    ),

    // --- ANDROID JUNIOR/INTERMEDIATE ---
    JobPosting(
        id = 9,
        title = "Jr. Android Developer (MVVM)",
        company = "MobileFirst",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "MVVM", "Room DB", "REST APIs")
    ),
    JobPosting(
        id = 10,
        title = "Android Engineer",
        company = "BankApp Co.",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Java", "Kotlin", "Git", "REST APIs", "XML")
    ),
    JobPosting(
        id = 11,
        title = "Android UI Developer (Compose)",
        company = "DesignForward",
        type = "Part-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Jetpack Compose", "UI/UX Design", "XML", "Kotlin")
    ),
    JobPosting(
        id = 12,
        title = "Junior Kotlin Developer",
        company = "Fintech Innovators",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "REST APIs", "Coroutines", "Git")
    ),
    JobPosting(
        id = 13,
        title = "Android Developer (Hilt)",
        company = "ScaleUp",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Hilt", "MVVM", "Jetpack Compose")
    ),
    JobPosting(
        id = 14,
        title = "Mobile Developer (Firebase)",
        company = "CloudCore",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Java", "Firebase", "REST APIs")
    ),
    JobPosting(
        id = 15,
        title = "Android Engineer (Java/Kotlin)",
        company = "HealthTech",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Java", "Kotlin", "XML", "MVVM", "Room DB")
    ),
    JobPosting(
        id = 16,
        title = "Part-time Android Developer",
        company = "GigWork",
        type = "Part-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "Firebase")
    ),
    JobPosting(
        id = 17,
        title = "Android Developer (Ktor)",
        company = "ServerSolutions",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Ktor", "Coroutines", "MVVM", "Jetpack Compose")
    ),
    JobPosting(
        id = 18,
        title = "Android Developer (Legacy Code)",
        company = "OldGuard Solutions",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Java", "XML", "Git", "REST APIs")
    ),
    JobPosting(
        id = 19,
        title = "Mid-Level Android Engineer",
        company = "TechCorp",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Coroutines", "Flow", "Jetpack Compose", "MVVM")
    ),
    JobPosting(
        id = 20,
        title = "Android App Developer",
        company = "E-Commerce World",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "XML", "REST APIs", "UI/UX Design", "Firebase")
    ),
    JobPosting(
        id = 21,
        title = "Android (Compose + UI/UX)",
        company = "PixelPerfect",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "UI/UX Design", "Git")
    ),
    JobPosting(
        id = 22,
        title = "Junior Android (Testing)",
        company = "QualityFirst",
        type = "Part-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "JUnit", "Espresso", "XML")
    ),

    // --- ANDROID SENIOR/EXPERT ---
    JobPosting(
        id = 23,
        title = "Senior Android Engineer (Architecture)",
        company = "ScaleUp",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "MVVM", "Hilt", "Room DB", "CI/CD")
    ),
    JobPosting(
        id = 24,
        title = "Android Tech Lead",
        company = "LeadMobile",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Java", "MVVM", "REST APIs", "Coroutines", "CI/CD")
    ),
    JobPosting(
        id = 25,
        title = "Part-time Android Dev (Maintenance)",
        company = "Legacy Apps",
        type = "Part-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Java", "Git", "XML", "JUnit")
    ),
    JobPosting(
        id = 26,
        title = "Senior Android (Firebase Expert)",
        company = "CloudCore",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Firebase", "MVVM", "REST APIs", "Jetpack Compose")
    ),
    JobPosting(
        id = 27,
        title = "Principal Android Engineer",
        company = "TechCorp",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Coroutines", "Flow", "Ktor", "MVVM", "Hilt")
    ),
    JobPosting(
        id = 28,
        title = "Senior Android (Java Specialist)",
        company = "BankApp Co.",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Java", "XML", "MVVM", "JUnit", "Espresso", "Git")
    ),
    JobPosting(
        id = 29,
        title = "Android Team Lead (Compose)",
        company = "DesignForward",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Jetpack Compose", "Kotlin", "MVVM", "UI/UX Design", "Hilt")
    ),
    JobPosting(
        id = 30,
        title = "Senior Android (CI/CD & Testing)",
        company = "QualityFirst",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "CI/CD", "JUnit", "Espresso", "Git")
    ),
    JobPosting(
        id = 31,
        title = "Staff Android Engineer",
        company = "MobileFirst",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Java", "Room DB", "MVVM", "Hilt", "Coroutines", "Flow")
    ),
    JobPosting(
        id = 32,
        title = "Android Architect",
        company = "Fintech Innovators",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "MVVM", "Hilt", "CI/CD", "Ktor", "REST APIs")
    ),
    JobPosting(
        id = 33,
        title = "Part-time Senior Android Consultant",
        company = "GigWork",
        type = "Part-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "Firebase", "MVVM")
    ),
    JobPosting(
        id = 34,
        title = "Senior Android (TFLite)",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "TensorFlow", "Java", "MVVM")
    ),

    // --- OTHER TECH JOBS (FOR FILTERING CONTRAST) ---
    JobPosting(
        id = 35,
        title = "Jr. Backend Developer (Node)",
        company = "ServerSolutions",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Node.js", "REST APIs", "Git")
    ),
    JobPosting(
        id = 36,
        title = "Frontend Developer (React)",
        company = "WebWeavers",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("React", "UI/UX Design", "Git")
    ),
    JobPosting(
        id = 37,
        title = "Data Scientist",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Python", "TensorFlow", "Git")
    ),
    JobPosting(
        id = 38,
        title = "Senior Data Engineer (Python)",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Python", "REST APIs")
    ),
    JobPosting(
        id = 39,
        title = "React Native Developer",
        company = "CrossPlatform Inc.",
        type = "Part-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("React", "Node.js", "UI/UX Design")
    ),
    JobPosting(
        id = 40,
        title = "DevOps Intern (Python/Git)",
        company = "CloudRun",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Git", "Python")
    ),
    JobPosting(
        id = 41,
        title = "Full-Stack Developer (Node/React)",
        company = "StartupFast",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Node.js", "React", "REST APIs", "Git")
    ),
    JobPosting(
        id = 42,
        title = "Python Developer (Backend)",
        company = "ServerSolutions",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Python", "REST APIs", "Git")
    ),
    JobPosting(
        id = 43,
        title = "Machine Learning Intern",
        company = "DataDrive",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Python", "TensorFlow")
    ),
    JobPosting(
        id = 44,
        title = "Lead Backend Engineer (Node)",
        company = "E-Commerce World",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Node.js", "REST APIs", "CI/CD", "Git")
    ),
    JobPosting(
        id = 45,
        title = "Senior React Developer",
        company = "WebWeavers",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("React", "UI/UX Design", "Git", "REST APIs")
    ),
    JobPosting(
        id = 46,
        title = "Part-time React Developer",
        company = "GigWork",
        type = "Part-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("React", "Git")
    ),
    JobPosting(
        id = 47,
        title = "Junior UI/UX Designer",
        company = "PixelPerfect",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("UI/UX Design")
    ),
    JobPosting(
        id = 48,
        title = "Senior UI/UX Designer",
        company = "DesignForward",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("UI/UX Design", "Git")
    ),
    JobPosting(
        id = 49,
        title = "Cloud Engineer (Firebase)",
        company = "CloudCore",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Firebase", "Node.js", "Git")
    ),
    JobPosting(
        id = 50,
        title = "QA Tester (Mobile)",
        company = "QualityFirst",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("JUnit", "Espresso", "Git")
    ),
    JobPosting(
        id = 51,
        title = "TensorFlow Developer",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Python", "TensorFlow")
    ),

    // --- EXPANSION TO 100 (IDs 52-100) ---

    // --- More Beginner ---
    JobPosting(
        id = 52,
        title = "Android Database Intern",
        company = "HealthTech",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Kotlin", "Room DB", "Git")
    ),
    JobPosting(
        id = 53,
        title = "Android Architecture Intern",
        company = "ScaleUp",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Kotlin", "MVVM", "XML")
    ),
    JobPosting(
        id = 54,
        title = "Python Scripting Intern",
        company = "AutomationCo",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Python", "Git")
    ),
    JobPosting(
        id = 55,
        title = "Backend Intern (Node.js)",
        company = "E-Commerce World",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Node.js", "REST APIs")
    ),
    JobPosting(
        id = 56,
        title = "React UI Intern",
        company = "WebWeavers",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("React", "UI/UX Design", "Git")
    ),
    JobPosting(
        id = 57,
        title = "Junior API Developer",
        company = "ServerSolutions",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("REST APIs", "Git", "Node.js")
    ),
    JobPosting(
        id = 58,
        title = "Firebase Admin Intern",
        company = "StartupFast",
        type = "Part-time",
        experienceLevel = "Beginner",
        requiredSkills = setOf("Firebase", "Git")
    ),
    JobPosting(
        id = 59,
        title = "Part-time XML Designer",
        company = "Legacy Apps",
        type = "Part-time",
        experienceLevel = "Beginner",
        requiredSkills = setOf("XML", "UI/UX Design")
    ),
    JobPosting(
        id = 60,
        title = "TFLite Intern",
        company = "DataDrive",
        type = "Internship",
        experienceLevel = "Beginner",
        requiredSkills = setOf("TensorFlow", "Python", "Kotlin")
    ),

    // --- More Intermediate ---
    JobPosting(
        id = 61,
        title = "Android SDK Developer",
        company = "TechCorp",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Java", "Git", "REST APIs")
    ),
    JobPosting(
        id = 62,
        title = "Full-Stack (React/Node)",
        company = "CrossPlatform Inc.",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("React", "Node.js", "REST APIs", "Git")
    ),
    JobPosting(
        id = 63,
        title = "ML Engineer (TensorFlow)",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Python", "TensorFlow", "REST APIs")
    ),
    JobPosting(
        id = 64,
        title = "Android Developer (Room & API)",
        company = "Fintech Innovators",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Room DB", "REST APIs", "MVVM")
    ),
    JobPosting(
        id = 65,
        title = "Java Maintenance Developer",
        company = "BankApp Co.",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Java", "XML", "Git", "JUnit")
    ),
    JobPosting(
        id = 66,
        title = "UI/UX Designer (Mobile)",
        company = "PixelPerfect",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("UI/UX Design", "Jetpack Compose", "XML")
    ),
    JobPosting(
        id = 67,
        title = "Part-time Kotlin Developer",
        company = "GigWork",
        type = "Part-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Coroutines", "REST APIs")
    ),
    JobPosting(
        id = 68,
        title = "Android Performance Engineer",
        company = "MobileFirst",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Java", "MVVM", "Git")
    ),
    JobPosting(
        id = 69,
        title = "Firebase Cloud Developer",
        company = "CloudCore",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Firebase", "Kotlin", "Node.js")
    ),
    JobPosting(
        id = 70,
        title = "React Developer (Part-time)",
        company = "StartupFast",
        type = "Part-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("React", "REST APIs", "Git")
    ),
    JobPosting(
        id = 71,
        title = "Backend Developer (Python/Flask)",
        company = "ServerSolutions",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Python", "REST APIs", "Git")
    ),
    JobPosting(
        id = 72,
        title = "Android Hilt/Dagger Developer",
        company = "ScaleUp",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Hilt", "MVVM", "Room DB")
    ),
    JobPosting(
        id = 73,
        title = "Android (XML to Compose)",
        company = "OldGuard Solutions",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "XML", "MVVM")
    ),
    JobPosting(
        id = 74,
        title = "Mid-level Node.js Developer",
        company = "E-Commerce World",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Node.js", "REST APIs", "Git", "Firebase")
    ),
    JobPosting(
        id = 75,
        title = "Android (Ktor & Compose)",
        company = "TechCorp",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "Ktor", "MVVM")
    ),
    JobPosting(
        id = 76,
        title = "QA Engineer (Android)",
        company = "QualityFirst",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Java", "Kotlin", "Espresso", "JUnit")
    ),
    JobPosting(
        id = 77,
        title = "Full-Stack (Firebase)",
        company = "StartupFast",
        type = "Full-time",
        experienceLevel = "Intermediate",
        requiredSkills = setOf("Firebase", "React", "Node.js")
    ),

    // --- More Expert ---
    JobPosting(
        id = 78,
        title = "Senior Android (Performance)",
        company = "MobileFirst",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Java", "MVVM", "CI/CD", "JUnit")
    ),
    JobPosting(
        id = 79,
        title = "Lead UI/UX Architect (Mobile)",
        company = "PixelPerfect",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("UI/UX Design", "Jetpack Compose", "XML", "Git")
    ),
    JobPosting(
        id = 80,
        title = "Senior Full-Stack (React/Node)",
        company = "CrossPlatform Inc.",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("React", "Node.js", "REST APIs", "CI/CD")
    ),
    JobPosting(
        id = 81,
        title = "Principal ML Engineer (TF)",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Python", "TensorFlow", "CI/CD", "REST APIs")
    ),
    JobPosting(
        id = 82,
        title = "Head of Mobile (Android)",
        company = "LeadMobile",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Java", "MVVM", "Hilt", "CI/CD")
    ),
    JobPosting(
        id = 83,
        title = "Senior Backend (Python/Node.js)",
        company = "ServerSolutions",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Python", "Node.js", "REST APIs", "CI/CD")
    ),
    JobPosting(
        id = 84,
        title = "Android Architect (Database)",
        company = "HealthTech",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Room DB", "MVVM", "Hilt")
    ),
    JobPosting(
        id = 85,
        title = "Senior Java Engineer (Android)",
        company = "BankApp Co.",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Java", "XML", "MVVM", "JUnit", "CI/CD")
    ),
    JobPosting(
        id = 86,
        title = "Senior Firebase Architect",
        company = "CloudCore",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Firebase", "Kotlin", "Node.js", "REST APIs")
    ),
    JobPosting(
        id = 87,
        title = "Part-time Android Mentor",
        company = "CodeBase",
        type = "Part-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Jetpack Compose", "MVVM", "Hilt")
    ),
    JobPosting(
        id = 88,
        title = "Senior API Architect (Ktor/Node)",
        company = "Fintech Innovators",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("REST APIs", "Ktor", "Node.js", "CI/CD")
    ),
    JobPosting(
        id = 89,
        title = "Senior React Architect",
        company = "WebWeavers",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("React", "UI/UX Design", "Node.js", "REST APIs")
    ),
    JobPosting(
        id = 90,
        title = "Lead Android (TFLite/ML)",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "TensorFlow", "MVVM", "Java")
    ),
    JobPosting(
        id = 91,
        title = "Staff Android (Compose Performance)",
        company = "ScaleUp",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Jetpack Compose", "Kotlin", "MVVM", "Hilt")
    ),
    JobPosting(
        id = 92,
        title = "Senior Android (Legacy Migration)",
        company = "OldGuard Solutions",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Java", "Kotlin", "XML", "Jetpack Compose", "MVVM")
    ),
    JobPosting(
        id = 93,
        title = "Senior Python (ML Ops)",
        company = "DataDrive",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Python", "TensorFlow", "CI/CD", "Git")
    ),
    JobPosting(
        id = 94,
        title = "React Native Lead",
        company = "CrossPlatform Inc.",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("React", "Node.js", "Git", "UI/UX Design")
    ),
    JobPosting(
        id = 95,
        title = "Part-time Senior Java Dev",
        company = "Legacy Apps",
        type = "Part-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Java", "XML", "Git")
    ),
    JobPosting(
        id = 96,
        title = "Senior Android (Room Expert)",
        company = "MobileFirst",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Room DB", "Hilt", "MVVM", "Coroutines")
    ),
    JobPosting(
        id = 97,
        title = "Head of QA (Mobile)",
        company = "QualityFirst",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("JUnit", "Espresso", "CI/CD", "Git", "Kotlin", "Java")
    ),
    JobPosting(
        id = 98,
        title = "Senior Node.js (E-commerce)",
        company = "E-Commerce World",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Node.js", "REST APIs", "Firebase", "Git")
    ),
    JobPosting(
        id = 99,
        title = "Part-time UI/UX Consultant",
        company = "GigWork",
        type = "Part-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("UI/UX Design", "Jetpack Compose", "React")
    ),
    JobPosting(
        id = 100,
        title = "Senior Android (Ktor/Coroutines)",
        company = "TechCorp",
        type = "Full-time",
        experienceLevel = "Expert",
        requiredSkills = setOf("Kotlin", "Ktor", "Coroutines", "MVVM", "Hilt")
    )
)