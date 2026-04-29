package com.kime.seminar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registrations")
data class Registration(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val seminarId: Long,
    val registrationDate: String,
    val status: String = "Registered" // e.g., Registered, Cancelled
)
