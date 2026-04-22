package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.kime.seminar.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayData()
        setupAnimations()
        setupClickListeners()
    }

    private fun displayData() {
        val nama = intent.getStringExtra("NAMA") ?: "-"
        val email = intent.getStringExtra("EMAIL") ?: "-"
        val noHp = intent.getStringExtra("NO_HP") ?: "-"
        val jenisKelamin = intent.getStringExtra("JENIS_KELAMIN") ?: "-"
        val seminar = intent.getStringExtra("SEMINAR") ?: "-"
        val tanggal = intent.getStringExtra("TANGGAL") ?: "-"
        val versiSoal = intent.getStringExtra("VERSI_SOAL") ?: "-"
        val halaman = intent.getStringExtra("HALAMAN") ?: "-"

        binding.tvNama.text = nama
        binding.tvEmail.text = email
        binding.tvNoHp.text = noHp
        binding.tvJenisKelamin.text = jenisKelamin
        binding.tvSeminar.text = seminar
        binding.tvTanggal.text = tanggal
        binding.tvVersiSoal.text = versiSoal
        binding.tvHalaman.text = halaman

        // Inisial avatar
        val initials = nama.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
            .joinToString("")
        binding.tvAvatarResult.text = initials.ifEmpty { "?" }
    }

    private fun setupAnimations() {
        val bounceIn = AnimationUtils.loadAnimation(this, R.anim.bounce_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        binding.ivSuccess.startAnimation(bounceIn)
        binding.tvSuccessTitle.startAnimation(fadeIn)
        binding.tvSuccessSubtitle.startAnimation(fadeIn)
        binding.cardResult.startAnimation(slideUp)
    }

    private fun setupClickListeners() {
        binding.btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        binding.btnDaftarLagi.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }
}
