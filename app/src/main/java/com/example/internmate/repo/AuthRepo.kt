package com.example.internmate.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception

/**
 * The Model layer for authentication.
 * This class is the single source of truth for all authentication-related data operations.
 * It directly communicates with Firebase.
 */
class AuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    /**
     * Gets the current logged-in user from Firebase.
     * @return FirebaseUser object if a user is logged in, null otherwise.
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Signs in a user with their email and password.
     * This is a suspend function because it performs an asynchronous network call.
     * @param email The user's email.
     * @param pass The user's password.
     * @return The AuthResult from Firebase.
     * @throws Exception if the sign-in fails.
     */
    suspend fun signIn(email: String, pass: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()
        } catch (e: Exception) {
            // Rethrow the exception to be handled in the ViewModel.
            throw e
        }
    }

    /**
     * Creates a new user account with an email and password.
     * This is a suspend function because it performs an asynchronous network call.
     * @param email The new user's email.
     * @param pass The new user's password.
     * @return The AuthResult from Firebase.
     * @throws Exception if the sign-up fails.
     */
    suspend fun signUp(email: String, pass: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(email, pass).await()
        } catch (e: Exception) {
            // Rethrow the exception to be handled in the ViewModel.
            throw e
        }
    }

    /**
     * Signs out the currently authenticated user.
     */
    fun logout() {
        auth.signOut()
    }
}