package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.database.RegistrationRepository
import com.kime.seminar.database.RegistrationWithSeminar
import com.kime.seminar.databinding.ActivityRegistrationHistoryBinding
import kotlinx.coroutines.launch

class RegistrationHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationHistoryBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var registrationRepository: RegistrationRepository
    private lateinit var adapter: RegistrationHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        registrationRepository = RegistrationRepository(this)

        if (!sessionManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupRecyclerView()
        setupClickListeners()
        loadRegistrationHistory()
    }

    private fun setupRecyclerView() {
        adapter = RegistrationHistoryAdapter()
        binding.rvRegistrations.layoutManager = LinearLayoutManager(this)
        binding.rvRegistrations.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadRegistrationHistory()
        }
    }

    private fun loadRegistrationHistory() {
        val userId = sessionManager.getCurrentUserId()
        if (userId == -1L) return

        binding.swipeRefresh.isRefreshing = true

        lifecycleScope.launch {
            try {
                // First, try to sync with Supabase
                registrationRepository.syncRegistrationsWithSupabase(userId)

                val registrations = registrationRepository.getRegistrationsWithSeminarDetails(userId)
                binding.swipeRefresh.isRefreshing = false

                if (registrations.isEmpty()) {
                    binding.rvRegistrations.visibility = android.view.View.GONE
                    binding.llEmptyState.visibility = android.view.View.VISIBLE
                } else {
                    binding.rvRegistrations.visibility = android.view.View.VISIBLE
                    binding.llEmptyState.visibility = android.view.View.GONE
                    adapter.submitList(registrations)
                }
            } catch (e: Exception) {
                binding.swipeRefresh.isRefreshing = false
                Snackbar.make(binding.root, "Gagal memuat riwayat: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
