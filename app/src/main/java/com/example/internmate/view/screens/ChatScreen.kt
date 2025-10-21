package com.example.internmate.view.screens

// --- NEW IMPORTS ---
// --- END NEW IMPORTS ---

// --- NEW LOTTIE IMPORTS ---
// Make sure you have: implementation("com.airbnb.android:lottie-compose:6.x.x") in your build.gradle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.internmate.R
import com.example.internmate.ui.theme.romalio
import com.example.internmate.view.elements.BgCircles
import com.example.internmate.view.elements.StaticBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- END LOTTIE IMPORTS ---


// --- 1. DATA MODELS FOR CHAT (Unchanged) ---

// To identify who sent the message
enum class Author {
    USER, AI
}

// Represents a single message in the chat
data class Message(
    val text: String,
    val author: Author,
    // Using a timestamp for a simple unique ID for the LazyColumn key
    val id: Long = System.currentTimeMillis()
)

// --- 2. "FAKE AI" RESPONSE LOGIC (Unchanged) ---

// This function simulates the AI's brain.
fun getAiResponse(userInput: String): String {
    val lowercasedInput = userInput.lowercase()
    return when {
        "resume" in lowercasedInput -> "Of course! For a strong resume, focus on the STAR method (Situation, Task, Action, Result) for each project. Quantify your achievements, like 'Improved app performance by 15%.' Do you want tips on a specific section, like projects or skills?"
        "interview" in lowercasedInput -> "Great question! Common interview questions for Android roles include explaining the Activity/Fragment lifecycle, dependency injection, and architectural patterns like MVVM. Practice coding challenges on platforms like LeetCode. Would you like a sample behavioral question?"
        "android" in lowercasedInput || "kotlin" in lowercasedInput || "compose" in lowercasedInput -> "Android development is a fantastic field. Key skills to master include Kotlin, Jetpack Compose for modern UI, understanding MVVM architecture, and using libraries like Retrofit for networking and Room for local storage. What part of Android interests you most?"
        // I noticed you're working on InternMate, so I added a response for it!
        "internmate" in lowercasedInput -> "InternMate sounds like a great project! When you talk about it, you can emphasize how it uses AI to personalize the internship search, saving students time and effort."
        "evantra" in lowercasedInput -> "Ah, EVANTRA, your event management app! That's a brilliant project for your portfolio. When talking about it, highlight the different user roles (admin, jury, user) and the technologies you used. Mentioning challenges you overcame, like managing state for the leaderboard, shows problem-solving skills."
        "career path" in lowercasedInput || "career" in lowercasedInput -> "There are many career paths! You could specialize in UI/UX, become a core platform engineer, or even move into cross-platform development with technologies like Kotlin Multiplatform. What are your long-term goals?"
        "skills" in lowercasedInput -> "For a student, the most important skills are a strong foundation in a programming language (like Kotlin), understanding core concepts like data structures, and having 1-2 significant projects to showcase your abilities. Your EVANTRA app is a perfect example of this!"
        "event management" in lowercasedInput -> "Connecting your skills to a field like event management is smart. You could discuss how technology streamlines event registration, scoring, and engagement, using your EVANTRA app as a case study."
        "hello" in lowercasedInput || "hi" in lowercasedInput -> "Hello there! How can I help you with your career questions today?"
        else -> "That's an interesting question. I'm still learning about that specific topic. Could you perhaps rephrase it, or ask me about resumes, interviews, or specific technologies like Android development?"
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(){
    // --- 3. STATE MANAGEMENT (Unchanged) ---
    val messages = remember { mutableStateListOf<Message>() }
    var isChatActive by remember { mutableStateOf(false) }
    val initialAiMessage = remember {
        Message("Hello! I'm your INTERNBOT. Ask me anything about resumes, interviews, or career paths.", Author.AI)
    }
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // --- 4. UI LAYOUT (Modified) ---
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(200.dp)
                .background(Color.Transparent.copy(0.2f))
        ) {
            StaticBackground(BgCircles)
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // This is the key part
            ) {
                // --- FIX 1: Add explicit receiver 'this@Column' ---
                this@Column.AnimatedVisibility(
                    visible = !isChatActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    InitialView()
                }

                // --- FIX 2: Add explicit receiver 'this@Column' ---
                this@Column.AnimatedVisibility(
                    visible = isChatActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ActiveChatView(messages = messages, listState = listState)
                }
            }

            // The input bar at the bottom
            ChatInputBar(
                inputText = inputText,
                onValueChange = { inputText = it },
                onSendClicked = {
                    if (inputText.isNotBlank()) {
                        val userMessage = Message(inputText, Author.USER)
                        val currentInput = inputText
                        inputText = ""

                        if (!isChatActive) {
                            isChatActive = true
                        }

                        messages.add(userMessage)

                        coroutineScope.launch {
                            delay(1000L)
                            val aiResponse = getAiResponse(currentInput)
                            messages.add(Message(aiResponse, Author.AI))
                        }
                    }
                },
            )
        }
    }

    LaunchedEffect(messages.size, isChatActive) {
        if (isChatActive && messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
}



// --- 5. UI COMPONENTS (MessageBubble & ChatInputBar are Unchanged) ---

@Composable
fun MessageBubble(message: Message) {
    // Determine alignment and colors based on who the author is
    val isUser = message.author == Author.USER
    val alignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
    val textColor = if (isUser) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Text(
            text = message.text,
            color = textColor,
            fontFamily = romalio,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(bubbleColor)
                .padding(12.dp)
                .widthIn(max = 300.dp) // Max width for bubbles
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBar(
    inputText: String,
    onValueChange: (String) -> Unit,
    onSendClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(bottom = 70.dp)
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = onValueChange,
            placeholder = { Text("Ask a question...", fontFamily = romalio) },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = onSendClicked,
            enabled = inputText.isNotBlank(),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.rounded_send_24), contentDescription = "Send Message")
        }
    }
}

// --- 6. NEW COMPOSABLES ---

/**
 * Shows the large, centered Lottie animation and title before the chat starts.
 */
@Composable
fun InitialView() {
    // Get the Lottie composition
    // Place your Lottie file in res/raw/ai_bot_animation.json
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ai_bot_animation))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "INTERNBOT",
            fontFamily = romalio,
            fontSize = 52.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(300.dp)
        )
        Text(
            text = "I am your AI career assistant",
            fontFamily = romalio,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Shows the active chat list, with a small header at the top.
 */
@Composable
fun ActiveChatView(
    messages: SnapshotStateList<Message>,
    listState: androidx.compose.foundation.lazy.LazyListState
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        // Add padding to the whole list
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // The small header that scrolls with the list
        item {
            SmallHeader()
        }

        // The messages
        items(messages, key = { it.id }) { message ->
            MessageBubble(message = message)
        }
    }
}

/**
 * The small header shown at the top of the ActiveChatView.
 */
@Composable
fun SmallHeader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ai_bot_animation))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "INTERNBOT",
            fontFamily = romalio,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}