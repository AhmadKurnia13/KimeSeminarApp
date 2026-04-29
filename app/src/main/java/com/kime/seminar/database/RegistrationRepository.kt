package com.kime.seminar.database

import android.content.Context
import com.kime.seminar.SupabaseHelper
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationRepository(context: Context) {
    private val registrationDao = AppDatabase.getDatabase(context).registrationDao()

    suspend fun insertRegistration(registration: Registration): Long = withContext(Dispatchers.IO) {
        registrationDao.insertRegistration(registration)
    }

    suspend fun getRegistrationsByUserId(userId: Long): List<Registration> = withContext(Dispatchers.IO) {
        registrationDao.getRegistrationsByUserId(userId)
    }

    suspend fun getRegistrationsWithSeminarDetails(userId: Long): List<RegistrationWithSeminar> = withContext(Dispatchers.IO) {
        registrationDao.getRegistrationsWithSeminarDetails(userId)
    }

    suspend fun syncRegistrationsWithSupabase(userId: Long) = withContext(Dispatchers.IO) {
        try {
            // Placeholder for sync logic
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
