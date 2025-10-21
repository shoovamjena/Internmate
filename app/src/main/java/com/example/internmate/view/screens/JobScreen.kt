package com.example.internmate.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internmate.R
import com.example.internmate.data.JobPosting
import com.example.internmate.data.allJobPostings
import com.example.internmate.ui.theme.romalio
import com.example.internmate.view.elements.BgCircles
import com.example.internmate.view.elements.StaticBackground

@OptIn(ExperimentalMaterial3Api::class,) // Added OptIns
@Composable
fun JobScreen(){

    // ... (allSkills and jobTypes lists remain the same) ...
    val allSkills = listOf(
        "Kotlin", "Jetpack Compose", "Java", "XML", "Firebase",
        "Git", "UI/UX Design", "REST APIs", "Room DB", "MVVM",
        "Python", "TensorFlow", "Node.js", "React"
    )
    val jobTypes = listOf("Internship", "Full-time", "Part-time")

    var selectedSkills by remember { mutableStateOf(emptySet<String>()) }
    var selectedJobTypeIndex by remember { mutableIntStateOf(0) }
    var experienceLevel by remember { mutableFloatStateOf(1f) }

    // --- NEW STATES FOR BOTTOM SHEET AND RESULTS ---
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var recommendedJobs by remember { mutableStateOf(listOf<JobPosting>()) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),

        ){paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .blur(200.dp)
            .background(Color.Transparent.copy(0.2f))
        ){
            StaticBackground(BgCircles)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "JOB",
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 48.sp
                ),
                color = Color.White,
                fontFamily = romalio,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "FINDER",
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 78.sp
                ),
                color = MaterialTheme.colorScheme.primary.copy(0.7f),
                fontFamily = romalio,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Build Your Profile".uppercase(),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = romalio
            )
            Text(
                text = "Help us find jobs tailored for you!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp),

            )

            // --- 2. Skills Selection ---
            Text(
                text = "Select Your Superpowers".uppercase(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                allSkills.forEach { skill ->
                    val isSelected = selectedSkills.contains(skill)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedSkills = if (isSelected) {
                                selectedSkills - skill
                            } else {
                                selectedSkills + skill
                            }
                        },
                        label = { Text(
                            skill,
                            fontFamily = romalio,
                            color =if(isSelected) Color.White.copy(0.7f) else  MaterialTheme.colorScheme.primary
                        ) },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    // Used standard icon, change back if you need
                                    imageVector = ImageVector.vectorResource(R.drawable.outline_check_24),
                                    contentDescription = "Selected",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                                    tint = Color.White.copy(0.7f)
                                )
                            }
                        } else {
                            null
                        },
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = Color.Transparent,
                            borderWidth = 1.dp,
                            enabled = true,
                            selected = isSelected,
                            borderColor = MaterialTheme.colorScheme.primary,
                        ),
                        shape = CircleShape,
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primaryContainer),
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. Job Preferences ---
            Text(
                text = "What are you looking for?".uppercase(),
                style = MaterialTheme.typography.titleLarge,
                fontFamily = romalio,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                jobTypes.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = jobTypes.size
                        ),
                        onClick = { selectedJobTypeIndex = index },
                        selected = index == selectedJobTypeIndex,
                        colors = SegmentedButtonDefaults.colors(MaterialTheme.colorScheme.primaryContainer),

                    ) {
                        Text(
                            label,
                            fontFamily = romalio,
                            color = if(selectedJobTypeIndex == index) Color.White.copy(0.7f) else MaterialTheme.colorScheme.primary,

                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Your Experience Level".uppercase(),
                style = MaterialTheme.typography.titleLarge,
                fontFamily = romalio,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            val experienceLabel = when (experienceLevel.toInt()) {
                0 -> "🌱 Beginner"
                1 -> "🧑‍💻 Intermediate"
                else -> "🏆 Expert"
            }
            Text(
                text = experienceLabel,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFA40CDB),
                fontFamily = romalio
            )

            Slider(
                value = experienceLevel,
                onValueChange = { experienceLevel = it },
                valueRange = 0f..2f,
                steps = 1,
            )

            // --- 4. SUBMIT BUTTON (LOGIC ADDED) ---
            Button(
                onClick = {
                    val jobType = jobTypes[selectedJobTypeIndex]
                    val experience = when (experienceLevel.toInt()) {
                        0 -> "Beginner"
                        1 -> "Intermediate"
                        else -> "Expert"
                    }

                    // --- "FAKE AI" FILTERING LOGIC ---
                    val filteredJobs = allJobPostings.filter { job ->
                        val typeMatch = job.type == jobType
                        val expMatch = job.experienceLevel == experience
                        // Recommend if the job requires at least one of the user's skills
                        val skillMatch = job.requiredSkills.intersect(selectedSkills).isNotEmpty()

                        // Must match all criteria
                        typeMatch && expMatch && skillMatch
                    }

                    recommendedJobs = filteredJobs
                    showBottomSheet = true // --- THIS SHOWS THE SHEET ---
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Changed to just 16.dp
                    .height(50.dp)
            ) {
                Text(text = "✨ Find My Perfect Job!", fontSize = 16.sp,fontFamily = romalio)
            }
            Spacer(modifier = Modifier.height(106.dp)) // Added spacer at the bottom
        }

        // --- 5. MODAL BOTTOM SHEET FOR RESULTS ---
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                // Sheet content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Recommended Jobs",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    if (recommendedJobs.isEmpty()) {
                        Text(
                            text = "No jobs found matching your criteria. Try adjusting your selections! 🧑‍🚀",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 32.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(recommendedJobs, key = { it.id }) { job ->
                                // --- THIS IS THE ONLY LINE THAT CHANGES ---
                                JobPostingItem(
                                    job = job,
                                    userSelectedSkills = selectedSkills // <-- Pass the skills here
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp)) // Space at the bottom of the sheet
                }
            }
        }
    }
}

// --- 3. COMPOSABLE FOR A SINGLE JOB ITEM ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobPostingItem(
    job: JobPosting,
    userSelectedSkills: Set<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = job.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = romalio,
            )
            Text(
                text = job.company,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = romalio
            )

            // --- NEWLY ADDED CHIP FOR JOB TYPE ---
           Row {
               SuggestionChip(
                   onClick = { /* Not clickable */ },
                   label = { Text(job.type.uppercase(),fontFamily = romalio) },
                   colors = SuggestionChipDefaults.suggestionChipColors(
                       containerColor = MaterialTheme.colorScheme.primary,
                       labelColor = Color.White
                   ),
                   border = null,
                   modifier = Modifier.padding(top = 8.dp)
               )
               Spacer(modifier = Modifier.width(8.dp))
               SuggestionChip(
                   onClick = { /* Not clickable */ },
                   label = { Text(job.experienceLevel.uppercase(),fontFamily = romalio) },
                   colors = SuggestionChipDefaults.suggestionChipColors(
                       containerColor = MaterialTheme.colorScheme.primary,
                       labelColor = Color.White
                   ),
                   border = null,
                   modifier = Modifier.padding(top = 8.dp)
               )
           }
            // --- END OF NEW CODE ---

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Skills:",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,fontFamily = romalio
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                // Logic for matched/unmatched skills
                job.requiredSkills.forEach { skill ->
                    val isMatched = userSelectedSkills.contains(skill)

                    val chipColors = if (isMatched) {
                        SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color(0xFFA0F6AB),
                            labelColor = Color(0xFF013E09)
                        )
                    } else {
                        SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color(0xFFF85757),
                            labelColor = Color.White
                        )
                    }

                    SuggestionChip(
                        onClick = { /* Not clickable */ },
                        label = { Text(skill,fontFamily = romalio) },
                        colors = chipColors,
                        border = null
                    )
                }
            }
        }
    }
}