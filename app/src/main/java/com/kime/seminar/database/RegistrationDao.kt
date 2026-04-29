package com.kime.seminar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RegistrationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistration(registration: Registration): Long

    @Query("SELECT * FROM registrations WHERE userId = :userId")
    suspend fun getRegistrationsByUserId(userId: Long): List<Registration>

    @Query("""
        SELECT r.*, s.title, s.date, s.time, s.location, s.speaker
        FROM registrations r
        INNER JOIN seminars s ON r.seminarId = s.id
        WHERE r.userId = :userId
        ORDER BY r.registrationDate DESC
    """)
    suspend fun getRegistrationsWithSeminarDetails(userId: Long): List<RegistrationWithSeminar>
}
