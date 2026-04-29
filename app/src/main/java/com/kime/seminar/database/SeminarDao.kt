package com.kime.seminar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SeminarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeminar(seminar: Seminar): Long

    @Query("SELECT * FROM seminars")
    suspend fun getAllSeminars(): List<Seminar>

    @Query("SELECT * FROM seminars WHERE id = :id LIMIT 1")
    suspend fun getSeminarById(id: Long): Seminar?

    @Query("SELECT * FROM seminars WHERE title = :title LIMIT 1")
    suspend fun getSeminarByTitle(title: String): Seminar?

    @Query("UPDATE seminars SET quota = quota - 1 WHERE id = :id AND quota > 0")
    suspend fun decrementQuota(id: Long): Int
}
