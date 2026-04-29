package com.kime.seminar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kime.seminar.databinding.FragmentScheduleBinding

import android.content.Intent
import android.util.Log
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

import androidx.navigation.fragment.findNavController

import com.kime.seminar.database.SeminarRepository

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var seminarAdapter: SeminarAdapter
    private lateinit var seminarRepository: SeminarRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seminarRepository = SeminarRepository(requireContext())
        setupRecyclerView()
        loadSeminars()
    }

    private fun setupRecyclerView() {
        seminarAdapter = SeminarAdapter { seminar ->
            val bundle = Bundle().apply {
                putString("seminarId", seminar.id.toString())
            }
            findNavController().navigate(R.id.eventDetailFragment, bundle)
        }
        binding.rvSeminars.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = seminarAdapter
        }
    }

    private fun loadSeminars() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Fetch from Supabase and Sync to Room
                val remoteSeminars = SupabaseHelper.client.postgrest["seminars"]
                    .select()
                    .decodeList<Seminar>()
                seminarRepository.syncSeminars(remoteSeminars)
                
                // Display from Room
                val localSeminars = seminarRepository.getAllSeminars().map { local ->
                    Seminar(
                        id = local.id,
                        title = local.title,
                        speaker = local.speaker,
                        eventDate = local.date,
                        capacity = local.quota,
                        location = local.location,
                        time = local.time,
                        summary = local.summary,
                        materials = local.materials
                    )
                }
                seminarAdapter.submitList(localSeminars)
            } catch (e: Exception) {
                Log.e("ScheduleFragment", "Error loading seminars", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
