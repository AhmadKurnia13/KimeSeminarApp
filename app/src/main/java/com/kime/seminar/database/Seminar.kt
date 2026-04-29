package com.kime.seminar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seminars")
data class Seminar(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val quota: Int,
    val summary: String,
    val speaker: String,
    val materials: String // Could be JSON string or separated by commas
)
