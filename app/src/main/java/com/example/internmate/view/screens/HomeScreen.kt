package com.example.internmate.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.internmate.data.UserPreferencesRepository
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

    var username by remember { mutableStateOf("Loading...") }

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

                if (document.exists()) {
                    // Get the "username" field from the document
                    username = document.getString("username") ?: "Welcome!"
                } else {
                    // Fallback for Google users who might not have a doc yet
                    // or if the doc doesn't have the field
                    username = currentUser.displayName ?: "Welcome!"
                }
            } catch (e: Exception) {
                // Handle any errors
                username = "Error"
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome!",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = username,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )
            Button(onClick = {
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
}