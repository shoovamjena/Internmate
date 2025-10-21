package com.example.internmate.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.internmate.R
import com.example.internmate.data.UserPreferencesRepository
import com.example.internmate.ui.theme.inspDoc
import com.example.internmate.ui.theme.romalio
import com.example.internmate.view.elements.AnimatedBackground
import com.example.internmate.view.elements.StaticBackground
import com.example.internmate.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

@Composable
fun HomeScreen(
    navController: NavController,
    userPreferencesRepository: UserPreferencesRepository,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser = Firebase.auth.currentUser

    var username by remember { mutableStateOf("...") }

    // 2. Launch an effect to fetch the username from Firestore
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            // Get the firestore instance
            val firestore = Firebase.firestore
            try {
                // Find the document in the "users" collection with the user's UID
                val document = firestore.collection("users")
                    .document(currentUser.uid)
                    .get()
                    .await() // Wait for the network request to complete

                username = if (document.exists()) {
                    // Get the "username" field from the document
                    document.getString("username") ?: "Welcome!"
                } else {
                    // Fallback for Google users who might not have a doc yet
                    // or if the doc doesn't have the field
                    currentUser.displayName ?: "Welcome!"
                }
            } catch (e: Exception) {
                // Handle any errors
                username = "Error"
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {  ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(200.dp)
                .background(Color.Transparent.copy(0.2f))
        ){
            StaticBackground()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.4f)


                // ----- DASHED STROKE LINE ----
                /*.drawBehind {
                    val strokeWidth = 5.dp.toPx()
                    val dashArray = floatArrayOf(80f, 30f)
                    val pathEffect = PathEffect.dashPathEffect(dashArray, 100f)
                    drawRoundRect(
                        color = Color(0xFF5E1386).copy(0.5f),
                        style = Stroke(
                            width = strokeWidth,
                            pathEffect = pathEffect
                        ),
                        cornerRadius = CornerRadius(50.dp.toPx())
                    )
                }*/


                .align(Alignment.Center)
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
                "Find your next\nInternship",
                maxLines = 2,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 24.sp
                ),
                fontFamily = romalio,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding( 30.dp).align(Alignment.TopCenter),
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Text(
                "Upload your resume here",
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 24.sp
                ),
                fontFamily = romalio,
                color = MaterialTheme.colorScheme.primary.copy(0.5f),
                modifier = Modifier.padding( 30.dp).align(Alignment.BottomCenter),
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary.copy(0.5f)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_file_upload_24),
                        contentDescription = "Upload Icon",
                        tint = Color.White
                    )
                    Text(
                        "UPLOAD",
                        fontFamily = romalio
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "WELCOME",
                fontFamily = romalio,
                fontSize = 46.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = username,
                fontFamily = inspDoc,
                fontSize = 76.sp,
                modifier = Modifier.padding(start = 10.dp),
                color = Color.White
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary.copy(0.7f), shape = RoundedCornerShape(50))
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Resume Analyzer",
                    fontFamily = romalio,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(bottom = 120.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .align(Alignment.BottomCenter),
            onClick = {
                authViewModel.logout(userPreferencesRepository)
                // Navigate back to the welcome screen after logging out
                navController.navigate("welcome") {
                    // Clear the entire back stack
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }) {
            Text("Logout")
        }
    }
}