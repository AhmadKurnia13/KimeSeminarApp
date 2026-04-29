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
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.databinding.ActivityFormBinding
import java.text.SimpleDateFormat
import java.util.*
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.kime.seminar.database.RegistrationRepository
import com.kime.seminar.database.SeminarRepository

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private val calendar = Calendar.getInstance()
    private lateinit var sessionManager: SessionManager
    private lateinit var registrationRepository: RegistrationRepository
    private lateinit var seminarRepository: SeminarRepository

    private val seminars = mutableListOf(
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

        sessionManager = SessionManager(this)
        registrationRepository = RegistrationRepository(this)
        seminarRepository = SeminarRepository(this)

        setupToolbar()
        loadSeminarsFromDb()
        setupDatePicker()
        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun loadSeminarsFromDb() {
        lifecycleScope.launch {
            try {
                val localSeminars = seminarRepository.getAllSeminars()
                if (localSeminars.isNotEmpty()) {
                    val currentPreselected = intent.getStringExtra("PRESELECTED_SEMINAR")
                    
                    seminars.clear()
                    seminars.add("-- Pilih Seminar --")
                    localSeminars.forEach { seminars.add(it.title) }
                    
                    // Re-add preselected if it's not in DB yet
                    if (!currentPreselected.isNullOrEmpty() && !seminars.contains(currentPreselected)) {
                        seminars.add(currentPreselected)
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("FormActivity", "Error loading seminars from DB", e)
            } finally {
                setupSpinner()
            }
        }
    }

    suspend fun insertRegistrationData(
        seminarId: String,
        name: String,
        email: String,
        phone: String,
        jenisKelamin: String
    ) {
        withContext(Dispatchers.IO) {
            // 1. Bungkus data ke dalam format Data Class
            val newRegistration = Registration(
                seminarId = seminarId,
                participantName = name,
                participantEmail = email,
                participantPhone = phone,
                jenisKelamin = jenisKelamin
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

        // Set selection if preselected
        val preselectedSeminar = intent.getStringExtra("PRESELECTED_SEMINAR")
        if (!preselectedSeminar.isNullOrEmpty()) {
            val index = seminars.indexOf(preselectedSeminar)
            if (index != -1) {
                binding.spinnerSeminar.setSelection(index)
            }
        }
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

        val v1 = validateNama(nama)
        val v2 = validateEmail(email)
        val v3 = validateNoHp(noHp)
        val v4 = validateGender()
        val v5 = validateTanggal()
        val v6 = validateSeminar()
        val v7 = validateCheckbox()

        if (!v1 || !v2 || !v3 || !v4 || !v5 || !v6 || !v7) {
            // Scroll ke atas jika ada error
            binding.scrollView.smoothScrollTo(0, 0)
            return
        }

        // Tampilkan dialog konfirmasi
        showConfirmationDialog(nama, email, noHp, tanggal)
    }

    private fun showConfirmationDialog(
        nama: String, email: String, noHp: String,
        tanggal: String
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
            🎓 Seminar   : $seminar
            
            Apakah data yang Anda isi sudah benar?
        """.trimIndent()

        AlertDialog.Builder(this, R.style.KimeAlertDialog)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage(message)
            .setPositiveButton("✅  Ya, Daftar!") { _, _ ->
                lifecycleScope.launch {
                    val userId = sessionManager.getCurrentUserId()
                    var seminarDetails = seminarRepository.getSeminarByTitle(seminar)
                    
                    // Fuzzy match fallback if not found exactly
                    if (seminarDetails == null) {
                        val allSeminars = seminarRepository.getAllSeminars()
                        seminarDetails = allSeminars.find { it.title.equals(seminar, ignoreCase = true) }
                    }

                    if (userId != -1L && seminarDetails != null) {
                        // Insert locally
                        val localRegistration = com.kime.seminar.database.Registration(
                            userId = userId,
                            seminarId = seminarDetails.id,
                            registrationDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                        )
                        registrationRepository.insertRegistration(localRegistration)

                        // Insert to Supabase (Remote)
                        try {
                            insertRegistrationData(
                                seminarId = seminarDetails.id.toString(),
                                name = nama,
                                email = email,
                                phone = noHp,
                                jenisKelamin = jenisKelamin
                            )
                        } catch (e: Exception) {
                            android.util.Log.e("FormActivity", "Supabase insert failed", e)
                        }
                        
                        goToResult(nama, email, noHp, jenisKelamin, seminar, tanggal)
                    } else {
                        val errorMsg = when {
                            userId == -1L -> "User ID tidak ditemukan. Silakan login ulang."
                            seminarDetails == null -> "Seminar '$seminar' tidak ditemukan di database."
                            else -> "Data tidak valid"
                        }
                        android.util.Log.e("FormActivity", "Registration failed: $errorMsg (userId=$userId, seminar=$seminar)")
                        Snackbar.make(binding.root, "Gagal mendaftar: $errorMsg", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("❌  Periksa Lagi", null)
            .setCancelable(false)
            .show()
    }

    private fun goToResult(
        nama: String, email: String, noHp: String,
        jenisKelamin: String, seminar: String, tanggal: String
    ) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("NAMA", nama)
            putExtra("EMAIL", email)
            putExtra("NO_HP", noHp)
            putExtra("JENIS_KELAMIN", jenisKelamin)
            putExtra("SEMINAR", seminar)
            putExtra("TANGGAL", tanggal)
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
        binding.rgJenisKelamin.clearCheck()
        binding.spinnerSeminar.setSelection(0)
        binding.cbPersetujuan.isChecked = false

        listOf(
            binding.tilNama, binding.tilEmail, binding.tilNoHp,
            binding.tilTanggal
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
