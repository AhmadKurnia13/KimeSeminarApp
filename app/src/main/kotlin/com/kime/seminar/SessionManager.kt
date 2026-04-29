package com.kime.seminar

import android.content.Context
import android.content.SharedPreferences
import com.kime.seminar.database.User
import com.kime.seminar.database.UserRepository
import kotlinx.coroutines.runBlocking

class SessionManager(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val userRepository = UserRepository(context)

    companion object {
        private const val PREF_NAME = "KimeSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ID = "userId"
    }

    fun createLoginSession(user: User) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putLong(KEY_USER_ID, user.id)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun getCurrentUserId(): Long = prefs.getLong(KEY_USER_ID, -1)

    fun getCurrentUser(): User? {
        val userId = getCurrentUserId()
        return if (userId != -1L) {
            runBlocking { userRepository.getUserById(userId) }
        } else null
    }

    fun logout() {
        prefs.edit().apply {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_USER_ID)
            apply()
        }
    }

    fun getUserName(): String {
        return getCurrentUser()?.name ?: "User"
    }

    fun getUserEmail(): String {
        return getCurrentUser()?.email ?: ""
    }

    suspend fun registerUser(name: String, email: String, password: String, gender: String, hobbies: String, city: String): User? {
        // Check if user already exists
        val existingUser = userRepository.getUserByEmail(email)
        if (existingUser != null) return null

        val hashedPassword = SecurityUtils.hashPassword(password)

        val user = User(
            name = name,
            email = email,
            password = hashedPassword,
            gender = gender,
            hobbies = hobbies,
            city = city
        )
        val userId = userRepository.insertUser(user)
        return user.copy(id = userId)
    }

    suspend fun loginUser(email: String, password: String): User? {
        val user = userRepository.getUserByEmail(email)
        if (user == null) return null

        val inputHash = SecurityUtils.hashPassword(password)
        
        return if (user.password == inputHash || user.password == password) { 
            // Also checking plain password for backward compatibility with existing data
            if (user.password == password) {
                // Auto-upgrade to hash on login
                updateUser(user.copy(password = inputHash))
            }
            user
        } else null
    }

    suspend fun updateUser(user: User) {
        userRepository.updateUser(user)
    }
}
