package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.databinding.ActivityLoginBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            goToHome()
            return
        }

        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupRealTimeValidation() {
        binding.etEmail.addTextChangedListener(createWatcher { s ->
            if (s.isNotEmpty()) validateEmail(s) else binding.tilEmail.error = null
        })

        binding.etPassword.addTextChangedListener(createWatcher { s ->
            if (s.isNotEmpty()) validatePassword(s) else binding.tilPassword.error = null
        })
    }

    private fun createWatcher(after: (String) -> Unit) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { after(s.toString()) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.tilEmail.error = "Email tidak boleh kosong"
                false
            }
            !email.contains("@") -> {
                binding.tilEmail.error = "Format email tidak valid (harus mengandung '@')"
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
            password.length < 8 -> {
                binding.tilPassword.error = "Password minimal 8 karakter"
                false
            }
            else -> {
                binding.tilPassword.error = null
                binding.tilPassword.isErrorEnabled = false
                true
            }
        }
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        val isEmailValid = validateEmail(email)
        val isPasswordValid = validatePassword(password)

        if (!isEmailValid || !isPasswordValid) return

        binding.btnLogin.isEnabled = false
        binding.progressLogin.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val user = sessionManager.loginUser(email, password)
                binding.btnLogin.isEnabled = true
                binding.progressLogin.visibility = View.GONE

                if (user != null) {
                    sessionManager.createLoginSession(user)
                    goToHome()
                } else {
                    binding.tilEmail.error = " "
                    binding.tilPassword.error = "Email atau password salah"
                    Snackbar.make(binding.root, "❌ Email atau password salah!", Snackbar.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                binding.btnLogin.isEnabled = true
                binding.progressLogin.visibility = View.GONE
                Snackbar.make(binding.root, "❌ Gagal login: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
