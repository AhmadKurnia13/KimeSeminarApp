package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kime.seminar.databinding.ActivitySeminarDetailBinding

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kime.seminar.database.SeminarRepository
import kotlinx.coroutines.launch

class EventDetailFragment : Fragment() {

    private var _binding: ActivitySeminarDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var seminarRepository: SeminarRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivitySeminarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seminarRepository = SeminarRepository(requireContext())

        val seminarId = arguments?.getString("seminarId") ?: return
        loadSeminarDetails(seminarId)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(requireContext(), FormActivity::class.java).apply {
                putExtra("PRESELECTED_SEMINAR", binding.tvSeminarTitle.text.toString())
            }
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.fabShare.setOnClickListener {
            shareSeminar()
        }
    }

    private fun shareSeminar() {
        val shareText = """
            🎓 Seminar: ${binding.tvSeminarTitle.text}
            👨‍🏫 Pembicara: ${binding.tvSeminarSpeaker.text}
            📅 Tanggal: ${binding.tvSeminarDate.text}
            ⏰ Waktu: ${binding.tvSeminarTime.text}
            📍 Lokasi: ${binding.tvSeminarLocation.text}

            Daftar sekarang di KimeSeminarApp!
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, "Bagikan Seminar"))
    }

    private fun loadSeminarDetails(id: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Fetch from Room or Supabase
                val seminar = seminarRepository.getSeminarById(id.toLong())
                seminar?.let {
                    binding.tvSeminarTitle.text = it.title
                    binding.tvSeminarSpeaker.text = "👨‍🏫 ${it.speaker}"
                    binding.tvSeminarDate.text = "📅 Tanggal: ${it.date}"
                    binding.tvSeminarTime.text = "⏰ Waktu: ${it.time}"
                    binding.tvSeminarLocation.text = "📍 Lokasi: ${it.location}"
                    binding.tvSeminarCapacity.text = "👥 Kuota: ${it.quota} peserta"
                    binding.tvSeminarSummary.text = it.summary
                    binding.tvSeminarMaterials.text = it.materials
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
