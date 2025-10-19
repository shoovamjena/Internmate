package com.example.internmate.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.internmate.data.UserPreferencesRepository
import com.example.internmate.view.screens.HomeScreen
import com.example.internmate.view.screens.OnboardingScreens
import com.example.internmate.view.screens.WelcomeScreen
import com.example.internmate.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Create instances that can be passed down to screens
    val userPreferencesRepository = remember { UserPreferencesRepository(context) }
    val authViewModel: AuthViewModel = viewModel()

    // State to hold the start destination route
    var startDestination by remember { mutableStateOf<String?>(null) }

    // Use LaunchedEffect to check the login state once
    LaunchedEffect(key1 = Unit) {
        // Check Firebase first for the most reliable state
        val isLoggedIn = Firebase.auth.currentUser != null
        userPreferencesRepository.saveLoginState(isLoggedIn) // Sync DataStore
        startDestination = if (isLoggedIn) "home" else "welcome"
    }

    // Show a loading indicator while determining the start destination
    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!
        ) {
            composable("welcome") {
                WelcomeScreen(navController)
            }
            composable("onboarding") {
                // Pass the repository to your sign-in function inside OnboardingScreens
                OnboardingScreens(navController, userPreferencesRepository)
            }
            composable("home") {
                HomeScreen(navController, userPreferencesRepository)
            }
        }
    } else {
        // Loading Screen
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}