package com.kime.seminar

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Seminar(
        val id: Long,
        val title: String,
        val speaker: String,
        @SerialName("event_date") val eventDate: String,
        val capacity: Int,
        val quota: Int? = 0,
        val location: String? = null,
        val time: String? = null,
        val summary: String? = null,
        val materials: String? = null
)