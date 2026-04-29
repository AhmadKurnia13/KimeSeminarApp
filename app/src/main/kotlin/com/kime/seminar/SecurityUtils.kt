package com.kime.seminar

import java.security.MessageDigest

object SecurityUtils {
    /**
     * Hashes a password using SHA-256 algorithm.
     */
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
