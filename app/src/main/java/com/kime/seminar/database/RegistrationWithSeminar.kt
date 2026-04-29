package com.kime.seminar.database

data class RegistrationWithSeminar(
    val id: Long,
    val userId: Long,
    val seminarId: Long,
    val registrationDate: String,
    val status: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val speaker: String
)
