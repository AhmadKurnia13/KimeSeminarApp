package com.kime.seminar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.databinding.ActivityFormBinding
import java.text.SimpleDateFormat
import java.util.*
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private val calendar = Calendar.getInstance()

    private val seminars = listOf(
        "-- Pilih Seminar --",
        "AI & Machine Learning in Industry 4.0",
        "Cybersecurity: Ancaman dan Perlindungan Digital",
        "Cloud Computing & DevOps Modern",
        "Vibe Coding: Era Baru Software Development",
        "Data Science untuk Pemula hingga Mahir",
        "Mobile Development dengan Kotlin & Flutter",
        "Blockchain & Web3 Technology"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupSpinner()
        setupDatePicker()
        setupRealTimeValidation()
        setupClickListeners()
    }

    suspend fun insertRegistrationData(
        seminarId: String,
        name: String,
        email: String,
        phone: String
    ) {
        withContext(Dispatchers.IO) {
            // 1. Bungkus data ke dalam format Data Class
            val newRegistration = Registration(
                seminarId = seminarId,
                participantName = name,
                participantEmail = email,
                participantPhone = phone
            )

            // 2. Kirim ke tabel "registrations" di Supabase
            SupabaseHelper.client.postgrest["registrations"]
                .insert(newRegistration)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Form Pendaftaran"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, R.layout.item_spinner, seminars)
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spinnerSeminar.adapter = adapter
    }

    private fun setupDatePicker() {
        binding.etTanggal.setOnClickListener {
            showDatePicker()
        }
        binding.etTanggal.isFocusable = false
        binding.etTanggal.isClickable = true
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(this, { _, y, m, d ->
            calendar.set(y, m, d)
            val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
            binding.etTanggal.setText(format.format(calendar.time))
            binding.tilTanggal.error = null
        }, year, month, day)

        picker.datePicker.minDate = System.currentTimeMillis()
        picker.show()
    }

    private fun setupRealTimeValidation() {
        // Nama
        binding.etNama.addTextChangedListener(createWatcher { validateNama(it) })

        // Email
        binding.etEmail.addTextChangedListener(createWatcher { validateEmail(it) })

        // Nomor HP
        binding.etNoHp.addTextChangedListener(createWatcher { validateNoHp(it) })

        // Versi Soal
        binding.etVersiSoal.addTextChangedListener(createWatcher { validateVersiSoal(it) })

        // Halaman
        binding.etHalaman.addTextChangedListener(createWatcher { validateHalaman(it) })
    }

    private fun createWatcher(validate: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validate(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun validateNama(value: String): Boolean {
        return when {
            value.isEmpty() -> {
                binding.tilNama.error = "Nama tidak boleh kosong"
                false
            }
            value.length < 3 -> {
                binding.tilNama.error = "Nama minimal 3 karakter"
                false
            }
            else -> {
                binding.tilNama.error = null; binding.tilNama.isErrorEnabled = false; true
            }
        }
    }

    private fun validateEmail(value: String): Boolean {
        return when {
            value.isEmpty() -> {
                binding.tilEmail.error = "Email tidak boleh kosong"
                false
            }
            !value.contains("@") -> {
                binding.tilEmail.error = "Email harus mengandung '@'"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches() -> {
                binding.tilEmail.error = "Format email tidak valid"
                false
            }
            else -> {
                binding.tilEmail.error = null; binding.tilEmail.isErrorEnabled = false; true
            }
        }
    }

    private fun validateNoHp(value: String): Boolean {
        return when {
            value.isEmpty() -> {
                binding.tilNoHp.error = "Nomor HP tidak boleh kosong"
                false
            }
            !value.all { it.isDigit() } -> {
                binding.tilNoHp.error = "Nomor HP hanya boleh angka"
                false
            }
            !value.startsWith("08") -> {
                binding.tilNoHp.error = "Nomor HP harus diawali dengan '08'"
                false
            }
            value.length < 10 || value.length > 13 -> {
                binding.tilNoHp.error = "Nomor HP harus 10–13 digit"
                false
            }
            else -> {
                binding.tilNoHp.error = null; binding.tilNoHp.isErrorEnabled = false; true
            }
        }
    }

    private fun validateVersiSoal(value: String): Boolean {
        return when {
            value.isEmpty() -> {
                binding.tilVersiSoal.error = "Versi soal tidak boleh kosong"
                false
            }
            else -> {
                binding.tilVersiSoal.error = null; binding.tilVersiSoal.isErrorEnabled = false; true
            }
        }
    }

    private fun validateHalaman(value: String): Boolean {
        return when {
            value.isEmpty() -> {
                binding.tilHalaman.error = "Halaman tidak boleh kosong"
                false
            }
            else -> {
                binding.tilHalaman.error = null; binding.tilHalaman.isErrorEnabled = false; true
            }
        }
    }

    private fun validateGender(): Boolean {
        return if (binding.rgJenisKelamin.checkedRadioButtonId == -1) {
            Snackbar.make(binding.root, "⚠️ Pilih jenis kelamin terlebih dahulu", Snackbar.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun validateTanggal(): Boolean {
        return if (binding.etTanggal.text.isNullOrEmpty()) {
            binding.tilTanggal.error = "Tanggal masuk wajib dipilih"
            false
        } else {
            binding.tilTanggal.error = null; true
        }
    }

    private fun validateSeminar(): Boolean {
        return if (binding.spinnerSeminar.selectedItemPosition == 0) {
            Snackbar.make(binding.root, "⚠️ Pilih seminar yang ingin diikuti", Snackbar.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun validateCheckbox(): Boolean {
        return if (!binding.cbPersetujuan.isChecked) {
            Snackbar.make(
                binding.root,
                "⚠️ Anda harus menyetujui bahwa data yang diinput sudah benar",
                Snackbar.LENGTH_LONG
            ).show()
            false
        } else true
    }

    private fun setupClickListeners() {
        binding.btnSubmit.setOnClickListener {
            submitForm()
        }

        binding.btnReset.setOnClickListener {
            resetForm()
        }
    }

    private fun submitForm() {
        val nama = binding.etNama.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val noHp = binding.etNoHp.text.toString().trim()
        val tanggal = binding.etTanggal.text.toString().trim()
        val versiSoal = binding.etVersiSoal.text.toString().trim()
        val halaman = binding.etHalaman.text.toString().trim()

        val v1 = validateNama(nama)
        val v2 = validateEmail(email)
        val v3 = validateNoHp(noHp)
        val v4 = validateGender()
        val v5 = validateTanggal()
        val v6 = validateVersiSoal(versiSoal)
        val v7 = validateHalaman(halaman)
        val v8 = validateSeminar()
        val v9 = validateCheckbox()

        if (!v1 || !v2 || !v3 || !v4 || !v5 || !v6 || !v7 || !v8 || !v9) {
            // Scroll ke atas jika ada error
            binding.scrollView.smoothScrollTo(0, 0)
            return
        }

        // Tampilkan dialog konfirmasi
        showConfirmationDialog(nama, email, noHp, tanggal, versiSoal, halaman)
    }

    private fun showConfirmationDialog(
        nama: String, email: String, noHp: String,
        tanggal: String, versiSoal: String, halaman: String
    ) {
        val jenisKelamin = if (binding.rgJenisKelamin.checkedRadioButtonId == R.id.rbLakiLaki)
            "Laki-laki" else "Perempuan"
        val seminar = binding.spinnerSeminar.selectedItem.toString()

        val message = """
            📋 Ringkasan Data Pendaftaran:
            
            👤 Nama      : $nama
            📧 Email     : $email
            📱 No. HP    : $noHp
            ⚥ Kelamin   : $jenisKelamin
            📅 Tanggal   : $tanggal
            📝 Versi     : $versiSoal
            📄 Halaman   : $halaman
            🎓 Seminar   : $seminar
            
            Apakah data yang Anda isi sudah benar?
        """.trimIndent()

        AlertDialog.Builder(this, R.style.KimeAlertDialog)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage(message)
            .setPositiveButton("✅  Ya, Daftar!") { _, _ ->
                goToResult(nama, email, noHp, jenisKelamin, seminar, tanggal, versiSoal, halaman)
            }
            .setNegativeButton("❌  Periksa Lagi", null)
            .setCancelable(false)
            .show()
    }

    private fun goToResult(
        nama: String, email: String, noHp: String,
        jenisKelamin: String, seminar: String, tanggal: String,
        versiSoal: String, halaman: String
    ) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("NAMA", nama)
            putExtra("EMAIL", email)
            putExtra("NO_HP", noHp)
            putExtra("JENIS_KELAMIN", jenisKelamin)
            putExtra("SEMINAR", seminar)
            putExtra("TANGGAL", tanggal)
            putExtra("VERSI_SOAL", versiSoal)
            putExtra("HALAMAN", halaman)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun resetForm() {
        binding.etNama.text?.clear()
        binding.etEmail.text?.clear()
        binding.etNoHp.text?.clear()
        binding.etTanggal.text?.clear()
        binding.etVersiSoal.text?.clear()
        binding.etHalaman.text?.clear()
        binding.rgJenisKelamin.clearCheck()
        binding.spinnerSeminar.setSelection(0)
        binding.cbPersetujuan.isChecked = false

        listOf(
            binding.tilNama, binding.tilEmail, binding.tilNoHp,
            binding.tilTanggal, binding.tilVersiSoal, binding.tilHalaman
        ).forEach {
            it.error = null
            it.isErrorEnabled = false
        }

        binding.scrollView.smoothScrollTo(0, 0)
        Snackbar.make(binding.root, "🔄 Form telah direset", Snackbar.LENGTH_SHORT).show()
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
