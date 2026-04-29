package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kime.seminar.databinding.FragmentHomeBinding
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())

        setupUserInfo()
        setupAnimations()
        setupClickListeners()
        setupPopularSeminars()
        fetchLastRegistration()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val listSeminar = fetchSeminars()
                for (seminar in listSeminar) {
                    Log.d("DATA_SEMINAR", "Judul: ${seminar.title}, Pembicara: ${seminar.speaker}")
                }
            } catch (e: Exception) {
                Log.e("ERROR_SUPABASE", "Gagal mengambil data: ${e.message}")
            }
        }
    }

    private suspend fun fetchSeminars(): List<Seminar> {
        return withContext(Dispatchers.IO) {
            SupabaseHelper.client.postgrest["seminars"]
                .select()
                .decodeList<Seminar>()
        }
    }

    private fun setupUserInfo() {
        val name = sessionManager.getUserName()
        val email = sessionManager.getUserEmail()
        
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val timeGreeting = when (hour) {
            in 0..11 -> "Selamat Pagi"
            in 12..15 -> "Selamat Siang"
            in 16..18 -> "Selamat Sore"
            else -> "Selamat Malam"
        }
        
        val firstName = name.split(" ").firstOrNull() ?: name
        binding.tvGreeting.text = "$timeGreeting, $firstName! 👋"
        binding.tvUserEmail.text = email

        val initials = name.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
            .joinToString("")
        binding.tvAvatar.text = initials.ifEmpty { "U" }
    }

    private fun setupAnimations() {
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.tvGreeting.startAnimation(fadeIn)
    }

    private fun setupClickListeners() {
        binding.cardDaftarSeminar.setOnClickListener {
            findNavController().navigate(R.id.scheduleFragment)
        }

        binding.btnDaftarSeminar.setOnClickListener {
            findNavController().navigate(R.id.scheduleFragment)
        }
        
        binding.btnViewLastRegistration.setOnClickListener {
            // Logic for viewing last registration
        }
        
        binding.tvAvatar.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun setupPopularSeminars() {
        val adapter = SeminarAdapter { seminar ->
            val bundle = Bundle().apply {
                putString("seminarId", seminar.id.toString())
            }
            findNavController().navigate(R.id.eventDetailFragment, bundle)
        }

        binding.rvPopularSeminars.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.d("HOME_DATA", "Starting to fetch seminars from Supabase...")
                
                val result = SupabaseHelper.client.postgrest["seminars"].select()
                Log.d("HOME_DATA", "Raw JSON Received: ${result.data}")
                
                val seminars = result.decodeList<Seminar>()
                
                if (seminars.isNotEmpty()) {
                    adapter.submitList(seminars)
                    Log.d("HOME_DATA", "Successfully loaded ${seminars.size} seminars")
                } else {
                    Log.w("HOME_DATA", "Database is connected but returned 0 rows.")
                    // If empty, it might still be an RLS issue or no data matching the query
                }
            } catch (e: Exception) {
                Log.e("HOME_DATA", "ERROR: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun fetchLastRegistration() {
        val userEmail = sessionManager.getUserEmail()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val registrations = withContext(Dispatchers.IO) {
                    SupabaseHelper.client.postgrest["registrations"]
                        .select {
                            filter {
                                eq("participant_email", userEmail)
                            }
                        }
                        .decodeList<Registration>()
                }

                if (registrations.isNotEmpty()) {
                    val lastRegistration = registrations.last()
                    val seminar = withContext(Dispatchers.IO) {
                        SupabaseHelper.client.postgrest["seminars"]
                            .select {
                                filter {
                                    eq("id", lastRegistration.seminarId)
                                }
                            }
                            .decodeList<Seminar>()
                            .firstOrNull()
                    }

                    seminar?.let {
                        binding.tvLastSeminarTitle.text = it.title
                        binding.tvLastSeminarDate.text = "📅 ${it.eventDate}"
                        binding.cardLastRegistration.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR_LAST_REGISTRATION", "Failed to fetch last registration: ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
