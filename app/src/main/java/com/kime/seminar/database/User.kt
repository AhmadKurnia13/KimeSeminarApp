package com.kime.seminar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val gender: String,
    val hobbies: String,
    val city: String,
    val profilePictureUrl: String? = null,
    val password: String, // Note: In production, hash passwords
    val relationshipStatus: String = "Single",
    val occupation: String = "",
    val age: Int = 0
)
