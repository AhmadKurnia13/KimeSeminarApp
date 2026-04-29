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
import com.kime.seminar.databinding.FragmentSeminarListBinding
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.kime.seminar.database.SeminarRepository

class SeminarListFragment : Fragment() {

    private var _binding: FragmentSeminarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var seminarAdapter: SeminarAdapter
    private lateinit var seminarRepository: SeminarRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeminarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seminarRepository = SeminarRepository(requireContext())

        setupRecyclerView()
        loadSeminars()
        setupSearch()
    }

    private fun setupRecyclerView() {
        seminarAdapter = SeminarAdapter { seminar ->
            // Navigate to seminar detail
            val bundle = Bundle().apply {
                putString("seminarId", seminar.id.toString())
            }
            try {
                findNavController().navigate(R.id.eventDetailFragment, bundle)
            } catch (e: Exception) {
                // Fallback for Activity-based navigation if nav host not found
                val intent = Intent(requireContext(), SeminarDetailActivity::class.java).apply {
                    putExtra("SEMINAR_ID", seminar.id.toString())
                    putExtra("SEMINAR_TITLE", seminar.title)
                    putExtra("SEMINAR_SPEAKER", seminar.speaker)
                    putExtra("SEMINAR_DATE", seminar.eventDate)
                    putExtra("SEMINAR_CAPACITY", seminar.capacity)
                    putExtra("SEMINAR_LOCATION", seminar.location)
                    putExtra("SEMINAR_TIME", seminar.time)
                    putExtra("SEMINAR_SUMMARY", seminar.summary)
                    putExtra("SEMINAR_MATERIALS", seminar.materials)
                }
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        binding.recyclerViewSeminars.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = seminarAdapter
        }
    }

    private fun loadSeminars() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE

        lifecycleScope.launch {
            try {
                // Try to fetch from Supabase and sync to Room
                val remoteSeminars = SupabaseHelper.client.postgrest["seminars"]
                    .select()
                    .decodeList<Seminar>()
                seminarRepository.syncSeminars(remoteSeminars)

                if (remoteSeminars.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    seminarAdapter.submitList(remoteSeminars)
                }
            } catch (e: Exception) {
                // If network fails, show local seminars
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
                if (localSeminars.isNotEmpty()) {
                    seminarAdapter.submitList(localSeminars)
                } else {
                    Snackbar.make(binding.root, "Gagal memuat seminar: ${e.message}", Snackbar.LENGTH_LONG).show()
                    binding.tvEmpty.visibility = View.VISIBLE
                }
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterSeminars(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSeminars(newText)
                return true
            }
        })
    }

    private fun filterSeminars(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            seminarAdapter.currentList
        } else {
            seminarAdapter.currentList.filter { seminar ->
                seminar.title.contains(query, ignoreCase = true) ||
                seminar.speaker.contains(query, ignoreCase = true) ||
                seminar.location?.contains(query, ignoreCase = true) == true
            }
        }
        seminarAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
