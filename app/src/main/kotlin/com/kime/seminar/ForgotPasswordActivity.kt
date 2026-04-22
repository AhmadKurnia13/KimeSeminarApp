package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.databinding.ActivityForgotPasswordBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Lupa Password"
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRealTimeValidation() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) validateEmail(s.toString())
                else binding.tilEmail.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupClickListeners() {
        binding.btnSendReset.setOnClickListener {
            performForgotPassword()
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }

        binding.btnBackToLogin.setOnClickListener {
            finish()
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

    private fun performForgotPassword() {
        val email = binding.etEmail.text.toString().trim()

        if (!validateEmail(email)) return

        binding.btnSendReset.isEnabled = false
        binding.progressReset.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Mengirim email reset password melalui Supabase
                SupabaseHelper.client.auth.resetPasswordForEmail(email)

                binding.btnSendReset.isEnabled = true
                binding.progressReset.visibility = View.GONE

                // Tampilkan pesan sukses
                binding.containerSuccess.visibility = View.VISIBLE
                binding.containerForm.visibility = View.GONE
                binding.tvSuccessEmail.text = email

            } catch (e: Exception) {
                binding.btnSendReset.isEnabled = true
                binding.progressReset.visibility = View.GONE

                // Tampilkan error
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Gagal mengirim email reset: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

