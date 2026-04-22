package com.kime.seminar

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

class SessionManager(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "KimeSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "name"
        private const val KEY_USERS = "users"
    }

    fun createLoginSession(email: String, name: String) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_EMAIL, email)
            putString(KEY_NAME, name)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun getUserEmail(): String = prefs.getString(KEY_EMAIL, "") ?: ""

    fun getUserName(): String = prefs.getString(KEY_NAME, "") ?: ""

    fun logout() {
        prefs.edit().apply {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_EMAIL)
            remove(KEY_NAME)
            apply()
        }
    }

    fun registerUser(name: String, email: String, password: String): Boolean {
        return try {
            val usersJson = prefs.getString(KEY_USERS, "{}") ?: "{}"
            val usersObj = JSONObject(usersJson)

            if (usersObj.has(email)) return false // Email sudah terdaftar

            val userObj = JSONObject().apply {
                put("name", name)
                put("email", email)
                put("password", password)
            }
            usersObj.put(email, userObj)
            prefs.edit().putString(KEY_USERS, usersObj.toString()).apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUser(email: String): Map<String, String>? {
        return try {
            val usersJson = prefs.getString(KEY_USERS, "{}") ?: "{}"
            val usersObj = JSONObject(usersJson)
            if (usersObj.has(email)) {
                val userObj = usersObj.getJSONObject(email)
                mapOf(
                    "name" to userObj.getString("name"),
                    "email" to userObj.getString("email"),
                    "password" to userObj.getString("password")
                )
            } else null
        } catch (e: Exception) {
            null
        }
    }
}
