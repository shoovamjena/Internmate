package com.example.internmate.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internmate.ui.theme.romalio
import com.example.internmate.view.elements.InfiniteScrollingChips
import com.example.internmate.view.elements.StaticBackground
import com.example.internmate.view.elements.StaticCircles


val CarrerCircles = listOf(
    StaticCircles(Color(0xFF9849F1), 500f, 1f, 0f),
    StaticCircles(Color(0xFF87A1FF), 400f, 0f, 0.4f),
    StaticCircles(Color(0xFFCD43E8), 800f, 0f, 1f),
    StaticCircles(Color(0xFFBEA4FC), 300f, 1f, 0.8f)
)
@Composable
fun CareerScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .blur(200.dp)
            .background(Color.Transparent.copy(0.2f))
        ){
            StaticBackground(CarrerCircles)
        }
        val careerChips = listOf(
            // Core Computer & IT
            "Software Developer",
            "Full Stack Developer",
            "Frontend Developer",
            "Backend Developer",
            "Mobile App Developer",
            "Game Developer",
            "DevOps Engineer",
            "Cloud Architect",
            "Cybersecurity Analyst",
            "Blockchain Developer",
            "AI/ML Engineer",
            "Data Scientist",
            "Data Engineer",
            "Data Analyst",
            "Database Administrator",
            "UI/UX Designer",
            "Product Manager",
            "Systems Analyst",
            "QA Engineer",
            "AR/VR Developer",

            // Electronics, Electrical, IoT
            "Embedded Systems Engineer",
            "IoT Engineer",
            "VLSI Engineer",
            "Control Systems Engineer",
            "Electronics Design Engineer",
            "Power Systems Engineer",
            "Robotics Engineer",
            "Automation Engineer",

            // Mechanical & Civil
            "Mechanical Design Engineer",
            "Automobile Engineer",
            "Thermal Engineer",
            "Mechatronics Engineer",
            "Civil Site Engineer",
            "Structural Engineer",
            "Construction Project Manager",
            "Environmental Engineer",

            // Chemical, Industrial & Material
            "Chemical Process Engineer",
            "Material Scientist",
            "Process Design Engineer",
            "Petroleum Engineer",
            "Industrial Engineer",
            "Manufacturing Engineer",

            // Business & Management
            "Operations Manager",
            "Technical Consultant",
            "Management Consultant",
            "HR Manager",
            "Entrepreneur",
            "MBA",
            "Project Manager",
            "Marketing Analyst",

            // Research & Innovation
            "Research Scientist",
            "Academic Researcher",
            "R&D Engineer",
            "Space Technology Engineer",

            // Creative & Interdisciplinary
            "Tech Content Creator",
            "3D Artist",
            "Game Designer",
            "Video Editor",
            "AR/VR Designer",
            "Product Designer"
        )

        val skillChips = listOf(
            // Programming Languages
            "C",
            "C++",
            "Java",
            "Kotlin",
            "Python",
            "JavaScript",
            "TypeScript",
            "Go",
            "Rust",
            "Swift",
            "Dart",
            "R",
            "MATLAB",

            // Web Development
            "HTML",
            "CSS",
            "React",
            "Next.js",
            "Angular",
            "Vue.js",
            "Node.js",
            "Express.js",
            "Django",
            "Flask",
            "Spring Boot",

            // Mobile Development
            "Flutter",
            "React Native",
            "Jetpack Compose",
            "SwiftUI",

            // Data Science & AI
            "Pandas",
            "NumPy",
            "TensorFlow",
            "PyTorch",
            "Scikit-learn",
            "Matplotlib",
            "Keras",
            "Data Visualization",
            "Power BI",
            "Tableau",
            "Machine Learning",
            "Deep Learning",
            "Natural Language Processing",
            "Computer Vision",

            // Cloud & DevOps
            "AWS",
            "Azure",
            "Google Cloud Platform",
            "Docker",
            "Kubernetes",
            "Jenkins",
            "CI/CD",
            "Terraform",

            // Cybersecurity & Networks
            "Ethical Hacking",
            "Penetration Testing",
            "Network Security",
            "Linux Administration",
            "Firewalls & IDS",

            // Embedded & IoT
            "Arduino",
            "Raspberry Pi",
            "Embedded C",
            "Microcontrollers",
            "PCB Design",
            "IoT Protocols (MQTT, CoAP)",
            "Sensor Integration",

            // Electrical/Mechanical/Civil
            "AutoCAD",
            "SolidWorks",
            "ANSYS",
            "MATLAB Simulink",
            "CATIA",
            "Thermal Analysis",
            "Structural Analysis",
            "PLC Programming",
            "SCADA",

            // Tools & Version Control
            "Git",
            "GitHub",
            "Figma",
            "Canva",
            "Blender",
            "Unity",
            "Unreal Engine",
            "VS Code",
            "Android Studio",
            "Postman",

            // Business & Soft Skills
            "Project Management",
            "Agile Methodology",
            "Scrum",
            "Leadership",
            "Communication",
            "Critical Thinking",
            "Problem Solving",
            "Team Collaboration",
            "Public Speaking",
            "UI/UX Prototyping"
        )

        InfiniteScrollingChips(modifier = Modifier.align(Alignment.Center), chipItems = careerChips, slantAngle = -30f, textColor = Color.White, surfaceColor = MaterialTheme.colorScheme.primary)
        InfiniteScrollingChips(modifier = Modifier.align(Alignment.Center),chipItems = skillChips, slantAngle = 20f, textColor = Color.Black, surfaceColor = MaterialTheme.colorScheme.primaryContainer)

        Column(
            modifier = Modifier
                .padding(top = 70.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "CAREER",
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 48.sp
                ),
                color = Color.White,
                fontFamily = romalio
            )
            Text(
                text = "ASSISTANT",
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 98.sp
                ),
                color = MaterialTheme.colorScheme.primary.copy(0.7f),
                fontFamily = romalio
            )
            Box(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .shadow(15.dp, RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.5f))
            ){
                Text(
                    text = "Find Your Dream Career",
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(
                        maxFontSize = 36.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = romalio,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(bottom = 80.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.2f)
                .align(Alignment.BottomCenter)
                .innerShadow(
                    shape = RoundedCornerShape(50.dp),
                    shadow = Shadow(
                        radius = 25.dp,
                        color = Color.White
                    )
                )
                .background(Color.White.copy(0.1f), shape = RoundedCornerShape(50.dp)),
        ){
            Text(
                "Personalised Career Engine".uppercase(),
                fontFamily = romalio,
                maxLines = 1,
                modifier = Modifier.align(Alignment.Center).padding(horizontal = 30.dp).padding(bottom = 50.dp),
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 34.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(bottom = 20.dp).align(Alignment.BottomCenter)
            ) {
                Text(
                    "START",
                    fontFamily = romalio
                )
            }
        }

    }
}