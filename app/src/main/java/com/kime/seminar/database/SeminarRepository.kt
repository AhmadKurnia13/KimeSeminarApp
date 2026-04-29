package com.kime.seminar.database

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeminarRepository(private val context: Context) {
    private val seminarDao = AppDatabase.getDatabase(context).seminarDao()

    suspend fun insertSeminar(seminar: Seminar): Long = withContext(Dispatchers.IO) {
        seminarDao.insertSeminar(seminar)
    }

    suspend fun getAllSeminars(): List<Seminar> = withContext(Dispatchers.IO) {
        seminarDao.getAllSeminars()
    }

    suspend fun getSeminarById(id: Long): Seminar? = withContext(Dispatchers.IO) {
        seminarDao.getSeminarById(id)
    }

    suspend fun getSeminarByTitle(title: String): Seminar? = withContext(Dispatchers.IO) {
        seminarDao.getSeminarByTitle(title)
    }

    suspend fun decrementQuota(id: Long): Int = withContext(Dispatchers.IO) {
        seminarDao.decrementQuota(id)
    }

    suspend fun syncSeminars(remoteSeminars: List<com.kime.seminar.Seminar>) = withContext(Dispatchers.IO) {
        remoteSeminars.forEach { remote ->
            val local = Seminar(
                id = remote.id.toLong(),
                title = remote.title,
                date = remote.eventDate,
                time = remote.time ?: "",
                location = remote.location ?: "",
                quota = remote.capacity,
                summary = remote.summary ?: "",
                speaker = remote.speaker,
                materials = remote.materials ?: ""
            )
            seminarDao.insertSeminar(local)
        }
    }
}
