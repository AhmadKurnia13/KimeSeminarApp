package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.database.RegistrationRepository
import com.kime.seminar.databinding.ActivityRegistrationHistoryBinding
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var _binding: ActivityRegistrationHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private lateinit var registrationRepository: RegistrationRepository
    private lateinit var adapter: RegistrationHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityRegistrationHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        registrationRepository = RegistrationRepository(requireContext())

        setupRecyclerView()
        setupClickListeners()
        loadRegistrationHistory()
    }

    private fun setupRecyclerView() {
        adapter = RegistrationHistoryAdapter()
        binding.rvRegistrations.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRegistrations.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnBack.visibility = View.GONE

        binding.swipeRefresh.setOnRefreshListener {
            loadRegistrationHistory()
        }
    }

    private fun loadRegistrationHistory() {
        val userId = sessionManager.getCurrentUserId()
        if (userId == -1L) return

        binding.swipeRefresh.isRefreshing = true

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // First, try to sync with Supabase
                registrationRepository.syncRegistrationsWithSupabase(userId)

                val registrations = registrationRepository.getRegistrationsWithSeminarDetails(userId)
                binding.swipeRefresh.isRefreshing = false

                if (registrations.isEmpty()) {
                    binding.rvRegistrations.visibility = View.GONE
                    binding.llEmptyState.visibility = View.VISIBLE
                } else {
                    binding.rvRegistrations.visibility = View.VISIBLE
                    binding.llEmptyState.visibility = View.GONE
                    adapter.submitList(registrations)
                }
            } catch (e: Exception) {
                binding.swipeRefresh.isRefreshing = false
                Snackbar.make(binding.root, "Gagal memuat riwayat: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
