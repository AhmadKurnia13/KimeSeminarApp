package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.database.SeminarRepository
import com.kime.seminar.databinding.ActivitySeminarDetailBinding
import kotlinx.coroutines.launch

class SeminarDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeminarDetailBinding
    private lateinit var seminarRepository: SeminarRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeminarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seminarRepository = SeminarRepository(this)

        setupToolbar()
        loadSeminarDetails()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Detail Seminar"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    private fun loadSeminarDetails() {
        val seminarId = intent.getStringExtra("SEMINAR_ID") ?: ""
        
        if (seminarId.isNotEmpty()) {
            lifecycleScope.launch {
                val seminar = seminarRepository.getSeminarById(seminarId.toLong())
                seminar?.let {
                    binding.apply {
                        tvSeminarTitle.text = it.title
                        tvSeminarSpeaker.text = "👨‍🏫 ${it.speaker}"
                        tvSeminarDate.text = "📅 Tanggal: ${it.date}"
                        tvSeminarTime.text = "⏰ Waktu: ${it.time}"
                        tvSeminarLocation.text = "📍 Lokasi: ${it.location}"
                        tvSeminarCapacity.text = "👥 Kuota: ${it.quota} peserta"
                        tvSeminarSummary.text = it.summary
                        tvSeminarMaterials.text = it.materials
                    }
                    return@launch
                }
            }
        }

        // Fallback to intent extras if ID not found or empty
        val title = intent.getStringExtra("SEMINAR_TITLE") ?: ""
        val speaker = intent.getStringExtra("SEMINAR_SPEAKER") ?: ""
        val date = intent.getStringExtra("SEMINAR_DATE") ?: ""
        val capacity = intent.getIntExtra("SEMINAR_CAPACITY", 0)
        val location = intent.getStringExtra("SEMINAR_LOCATION") ?: "Belum ditentukan"
        val time = intent.getStringExtra("SEMINAR_TIME") ?: "Belum ditentukan"
        val summary = intent.getStringExtra("SEMINAR_SUMMARY") ?: "Deskripsi belum tersedia"
        val materials = intent.getStringExtra("SEMINAR_MATERIALS") ?: "Materi belum tersedia"

        binding.apply {
            tvSeminarTitle.text = title
            tvSeminarSpeaker.text = speaker
            tvSeminarDate.text = date
            tvSeminarTime.text = time
            tvSeminarLocation.text = location
            tvSeminarCapacity.text = "$capacity peserta"
            tvSeminarSummary.text = summary
            tvSeminarMaterials.text = materials
        }
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            // Navigate to FormActivity with pre-selected seminar
            val intent = Intent(this, FormActivity::class.java).apply {
                putExtra("PRESELECTED_SEMINAR", binding.tvSeminarTitle.text.toString())
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
