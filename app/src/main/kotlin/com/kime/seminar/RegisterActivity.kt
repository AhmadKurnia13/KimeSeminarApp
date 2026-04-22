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

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)
        setContentView(binding.root)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Validasi sederhana
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses pembuatan akun di Background (Coroutine)
            lifecycleScope.launch {
                try {
                    SupabaseHelper.client.auth.signUpWith(Email) {
                        this.email = email
                        this.password = password
                    }
                    Toast.makeText(this@RegisterActivity, "Daftar Berhasil! Silakan Login.", Toast.LENGTH_LONG).show()

                    // Pindah ke halaman Login setelah sukses
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "Gagal daftar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        sessionManager = SessionManager(this)

        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupRealTimeValidation() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) validateName(s.toString())
                else binding.tilName.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) validateEmail(s.toString())
                else binding.tilEmail.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) validatePassword(s.toString())
                else binding.tilPassword.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) validateConfirmPassword(
                    binding.etPassword.text.toString(),
                    s.toString()
                )
                else binding.tilConfirmPassword.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            performRegister()
        }

        binding.tvLogin.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun validateName(name: String): Boolean {
        return when {
            name.isEmpty() -> {
                binding.tilName.error = "Nama tidak boleh kosong"
                false
            }
            name.length < 3 -> {
                binding.tilName.error = "Nama minimal 3 karakter"
                false
            }
            else -> {
                binding.tilName.error = null
                binding.tilName.isErrorEnabled = false
                true
            }
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
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = "Format email tidak valid"
                false
            }
            else -> {
                binding.tilEmail.error = null
                binding.tilEmail.isErrorEnabled = false
                true
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        return when {
            password.isEmpty() -> {
                binding.tilPassword.error = "Password tidak boleh kosong"
                false
            }
            password.length < 6 -> {
                binding.tilPassword.error = "Password minimal 6 karakter"
                false
            }
            else -> {
                binding.tilPassword.error = null
                binding.tilPassword.isErrorEnabled = false
                true
            }
        }
    }

    private fun validateConfirmPassword(password: String, confirm: String): Boolean {
        return when {
            confirm.isEmpty() -> {
                binding.tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
                false
            }
            password != confirm -> {
                binding.tilConfirmPassword.error = "Password tidak cocok"
                false
            }
            else -> {
                binding.tilConfirmPassword.error = null
                binding.tilConfirmPassword.isErrorEnabled = false
                true
            }
        }
    }

    private fun performRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        val isNameValid = validateName(name)
        val isEmailValid = validateEmail(email)
        val isPasswordValid = validatePassword(password)
        val isConfirmValid = validateConfirmPassword(password, confirmPassword)

        if (!isNameValid || !isEmailValid || !isPasswordValid || !isConfirmValid) return

        binding.btnRegister.isEnabled = false
        binding.progressRegister.visibility = View.VISIBLE

        binding.root.postDelayed({
            binding.btnRegister.isEnabled = true
            binding.progressRegister.visibility = View.GONE

            val success = sessionManager.registerUser(name, email, password)
            if (success) {
                Snackbar.make(binding.root, "✅ Registrasi berhasil! Silakan login.", Snackbar.LENGTH_LONG).show()
                binding.root.postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                }, 1500)
            } else {
                binding.tilEmail.error = "Email sudah terdaftar, gunakan email lain"
                Snackbar.make(binding.root, "❌ Email sudah terdaftar!", Snackbar.LENGTH_SHORT).show()
            }
        }, 800)
    }
}
