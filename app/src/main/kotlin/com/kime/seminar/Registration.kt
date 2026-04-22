package com.kime.seminar

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Registration(
    // Kita tidak memasukkan 'id' dan 'registration_date' karena Supabase akan membuatnya secara otomatis
    @SerialName("seminar_id") val seminarId: String,
    @SerialName("participant_name") val participantName: String,
    @SerialName("participant_email") val participantEmail: String,
    @SerialName("participant_phone") val participantPhone: String
)