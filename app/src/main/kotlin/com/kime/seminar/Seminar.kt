package com.kime.seminar

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
@Serializable
data class Seminar(
        val id: String,
        val title: String,
        val speaker: String,
        @SerialName("event_date") val eventDate: String,
        val capacity: Int
)