package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.databinding.ActivityRegisterBinding
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupAccountTypeSpinner()
        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupAccountTypeSpinner() {
        val accountTypes = listOf("Pilih Tipe Akun", "Personal", "Organisasi")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, accountTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAccountType.adapter = adapter
    }

    private fun setupRealTimeValidation() {
        binding.etName.addTextChangedListener(createWatcher { s ->
            if (s.isNotEmpty()) validateName(s) else binding.tilName.error = null
        })

        binding.etEmail.addTextChangedListener(createWatcher { s ->
            if (s.isNotEmpty()) validateEmail(s) else binding.tilEmail.error = null
        })

        binding.etPassword.addTextChangedListener(createWatcher { s ->
            if (s.isNotEmpty()) validatePassword(s) else binding.tilPassword.error = null
        })

        binding.etConfirmPassword.addTextChangedListener(createWatcher { s ->
            if (s.isNotEmpty()) validateConfirmPassword(binding.etPassword.text.toString(), s)
            else binding.tilConfirmPassword.error = null
        })
    }

    private fun createWatcher(after: (String) -> Unit) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { after(s.toString()) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            showConfirmationDialog()
        }

        // Gesture Interaction: Long Press to Reset
        binding.btnRegister.setOnLongClickListener {
            resetForm()
            Toast.makeText(this, "Form telah di-reset 🔄", Toast.LENGTH_SHORT).show()
            true
        }

        binding.tvLogin.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun resetForm() {
        binding.apply {
            etName.text?.clear()
            etEmail.text?.clear()
            etPassword.text?.clear()
            etConfirmPassword.text?.clear()
            etCity.text?.clear()
            rgGender.clearCheck()
            cbTech.isChecked = false
            cbBusiness.isChecked = false
            cbArt.isChecked = false
            cbSports.isChecked = false
            spinnerAccountType.setSelection(0)
            
            tilName.error = null
            tilEmail.error = null
            tilPassword.error = null
            tilConfirmPassword.error = null
            tilCity.error = null
        }
    }

    private fun showConfirmationDialog() {
        if (!validateAll()) return

        AlertDialog.Builder(this, R.style.KimeAlertDialog)
            .setTitle("Konfirmasi")
            .setMessage("Apakah data yang Anda masukkan sudah benar?")
            .setPositiveButton("Ya, Daftar") { _, _ -> performRegister() }
            .setNegativeButton("Cek Kembali", null)
            .show()
    }

    private fun validateAll(): Boolean {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirm = binding.etConfirmPassword.text.toString().trim()
        val city = binding.etCity.text.toString().trim()

        val isNameValid = validateName(name)
        val isEmailValid = validateEmail(email)
        val isPassValid = validatePassword(password)
        val isConfirmValid = validateConfirmPassword(password, confirm)
        val isCityValid = validateCity(city)
        
        // Gender validation
        val isGenderSelected = binding.rgGender.checkedRadioButtonId != -1
        if (!isGenderSelected) {
            Toast.makeText(this, "Silakan pilih jenis kelamin", Toast.LENGTH_SHORT).show()
        }

        // Hobbies validation
        val selectedHobbies = getSelectedHobbies()
        val isHobbiesValid = selectedHobbies.isNotEmpty()
        if (!isHobbiesValid) {
            Toast.makeText(this, "Pilih minimal 1 hobi", Toast.LENGTH_SHORT).show()
        }

        // Account Type validation
        val isAccountTypeValid = binding.spinnerAccountType.selectedItemPosition != 0
        if (!isAccountTypeValid) {
            Toast.makeText(this, "Silakan pilih tipe akun", Toast.LENGTH_SHORT).show()
        }

        return isNameValid && isEmailValid && isPassValid && isConfirmValid && isCityValid && 
               isGenderSelected && isHobbiesValid && isAccountTypeValid
    }

    private fun getSelectedHobbies(): List<String> {
        val list = mutableListOf<String>()
        if (binding.cbTech.isChecked) list.add("Teknologi")
        if (binding.cbBusiness.isChecked) list.add("Bisnis")
        if (binding.cbArt.isChecked) list.add("Seni")
        if (binding.cbSports.isChecked) list.add("Olahraga")
        return list
    }

    private fun validateName(name: String): Boolean {
        return if (name.length < 3) {
            binding.tilName.error = "Nama minimal 3 karakter"
            false
        } else {
            binding.tilName.error = null
            true
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.tilEmail.error = "Email tidak boleh kosong"
                false
            }
            !email.contains("@") -> {
                binding.tilEmail.error = "Email harus mengandung '@'"
                false
            }
            else -> {
                binding.tilEmail.error = null
                true
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        val hasMinLength = password.length >= 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return when {
            password.isEmpty() -> {
                binding.tilPassword.error = "Password tidak boleh kosong"
                false
            }
            !hasMinLength -> {
                binding.tilPassword.error = "Minimal 8 karakter"
                false
            }
            !hasUpperCase -> {
                binding.tilPassword.error = "Harus ada huruf KAPITAL"
                false
            }
            !hasSpecialChar -> {
                binding.tilPassword.error = "Harus ada karakter spesial (!@#$%^&*)"
                false
            }
            else -> {
                binding.tilPassword.error = null
                true
            }
        }
    }

    private fun validateConfirmPassword(password: String, confirm: String): Boolean {
        return if (password != confirm) {
            binding.tilConfirmPassword.error = "Password tidak cocok"
            false
        } else {
            binding.tilConfirmPassword.error = null
            true
        }
    }

    private fun validateCity(city: String): Boolean {
        return if (city.isEmpty()) {
            binding.tilCity.error = "Kota tidak boleh kosong"
            false
        } else {
            binding.tilCity.error = null
            true
        }
    }

    private fun performRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val gender = if (binding.rbMale.isChecked) "Laki-laki" else "Perempuan"
        val hobbies = getSelectedHobbies().joinToString(", ")
        val city = binding.etCity.text.toString().trim()

        binding.btnRegister.isEnabled = false
        binding.progressRegister.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val user = sessionManager.registerUser(name, email, password, gender, hobbies, city)
                binding.btnRegister.isEnabled = true
                binding.progressRegister.visibility = View.GONE

                if (user != null) {
                    Snackbar.make(binding.root, "✅ Registrasi berhasil!", Snackbar.LENGTH_LONG).show()
                    binding.root.postDelayed({ finish() }, 1500)
                } else {
                    binding.tilEmail.error = "Email sudah terdaftar"
                }
            } catch (e: Exception) {
                binding.btnRegister.isEnabled = true
                binding.progressRegister.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
