package com.example.internmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internmate.data.UserPreferencesRepository
import com.example.internmate.model.User
import com.example.internmate.repo.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class PasswordStrength{
    WEAK,
    MEDIUM,
    STRONG,
    VERY_STRONG
}

data class SignUpState(
    val isUsernameTaken: Boolean = false,
    val passwordStrength: PasswordStrength? = null,
    val signUpError: String? = null,
    val isSignUpSuccess: Boolean = false,
    val loginError: String? = null,
    val isLoginSuccess: Boolean = false,
    val passwordResetEmailSent: Boolean = false
)
class AuthViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore

    private val _uiState = MutableStateFlow(SignUpState())
    val uiState = _uiState.asStateFlow()

    // 1. Check for Username Uniqueness
    fun checkUsername(username: String) {
        if (username.length < 3) {
            _uiState.value = _uiState.value.copy(isUsernameTaken = false)
            return
        }

        viewModelScope.launch {
            try {
                val result = firestore.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .await()

                _uiState.value = _uiState.value.copy(isUsernameTaken = !result.isEmpty)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isUsernameTaken = false)
            }
        }
    }

    // 2. Check Password Strength
    fun validatePassword(password: String) {
        val strength = when {
            password.length < 8 -> PasswordStrength.WEAK
            !password.any { it.isDigit() } -> PasswordStrength.WEAK
            !password.any { it.isUpperCase() } -> PasswordStrength.MEDIUM
            !password.any { it.isLowerCase() } -> PasswordStrength.MEDIUM
            !password.any { !it.isLetterOrDigit() } -> PasswordStrength.STRONG
            else -> PasswordStrength.VERY_STRONG
        }
        _uiState.value = _uiState.value.copy(passwordStrength = strength)
    }

    // 3. Create User (Sign Up)
    fun signUp(
        email: String,
        password: String,
        username: String,
        userPreferencesRepository: UserPreferencesRepository // <-- Add parameter
    ) {
        if (_uiState.value.isUsernameTaken) {
            _uiState.value = _uiState.value.copy(signUpError = "Username is already taken")
            return
        }
        if (_uiState.value.passwordStrength == PasswordStrength.WEAK) {
            _uiState.value = _uiState.value.copy(signUpError = "Password is too weak")
            return
        }

        viewModelScope.launch {
            try {
                // 1. Create user in Firebase Auth
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = authResult.user

                if (firebaseUser != null) {
                    // 2. Send Email Verification
                    firebaseUser.sendEmailVerification().await()

                    // 3. Save user data to Firestore
                    val user = User(
                        uid = firebaseUser.uid,
                        username = username,
                        email = email
                    )
                    firestore.collection("users").document(firebaseUser.uid)
                        .set(user)
                        .await()

                    // --- KEY ADDITION ---
                    // 4. Save login state to DataStore
                    userPreferencesRepository.saveLoginState(true)
                    // --- END ADDITION ---

                    _uiState.value = _uiState.value.copy(isSignUpSuccess = true, signUpError = null)
                }
            } catch (e: Exception) {
                // ... (your existing error handling)
                val errorMessage = when (e) {
                    is FirebaseAuthUserCollisionException -> "This email is already in use."
                    else -> "Sign up failed: ${e.message}"
                }
                _uiState.value = _uiState.value.copy(signUpError = errorMessage)
            }
        }
    }

    // 4. Sign In (for the toast message)
    fun signIn(email: String, password: String, userPreferencesRepository: UserPreferencesRepository) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()

                // --- KEY ADDITION: Save login state on success ---
                userPreferencesRepository.saveLoginState(true)

                // You might want a new state here to trigger navigation
                _uiState.value = _uiState.value.copy(loginError = null, isLoginSuccess = true)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(loginError = "Error: Invalid email or password.")
            }
        }
    }

    // Helper to reset error messages
    fun resetErrorMessages() {
        _uiState.value = _uiState.value.copy(
            signUpError = null,
            loginError = null,
            isLoginSuccess = false,
            isSignUpSuccess = false,
            passwordResetEmailSent = false
        )
    }
    fun sendPasswordResetEmail(email: String) {
        // Reset previous states
        _uiState.value = _uiState.value.copy(loginError = null, passwordResetEmailSent = false)

        if (email.isBlank()) {
            _uiState.value = _uiState.value.copy(loginError = "Please enter your email address first.")
            return
        }

        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email.trim()).await()
                _uiState.value = _uiState.value.copy(passwordResetEmailSent = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(loginError = "Error: ${e.message}")
            }
        }
    }
    fun logout(userPreferencesRepository: UserPreferencesRepository) {
        viewModelScope.launch {
            auth.signOut()
            userPreferencesRepository.saveLoginState(false)
        }
    }

    fun signInWithGoogle(
        idToken: String,
        username: String?, // Nullable: null for login, provided for sign-up
        userPreferencesRepository: UserPreferencesRepository
    ) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(credential).await()

                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    val isNewUser = authResult.additionalUserInfo?.isNewUser == true

                    // If it's a new user AND we are in the sign-up flow (username is not null)
                    if (isNewUser && username != null) {
                        // Save the user data with the chosen username
                        val user = User(
                            uid = firebaseUser.uid,
                            username = username.trim(),
                            email = firebaseUser.email ?: ""
                        )
                        firestore.collection("users").document(firebaseUser.uid)
                            .set(user)
                            .await()
                    }

                    // Log them in and save state
                    userPreferencesRepository.saveLoginState(true)

                    // Trigger navigation (use a shared success state)
                    _uiState.value = _uiState.value.copy(
                        isLoginSuccess = true,
                        isSignUpSuccess = true, // Set both so either screen can react
                        loginError = null,
                        signUpError = null
                    )
                }
            } catch (e: Exception) {
                // Handle errors
                _uiState.value = _uiState.value.copy(
                    loginError = "Google Sign-In failed: ${e.message}",
                    signUpError = "Google Sign-In failed: ${e.message}"
                )
            }
        }
    }
}