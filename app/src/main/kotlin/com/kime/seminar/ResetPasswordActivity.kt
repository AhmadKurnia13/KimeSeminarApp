package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.databinding.ActivityResetPasswordBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRealTimeValidation()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Reset Password"
        binding.toolbar.setNavigationOnClickListener {
            goToLogin()
        }
    }

    private fun setupRealTimeValidation() {
        binding.etNewPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) validatePassword(s.toString())
                else binding.tilNewPassword.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) validatePasswordMatch()
                else binding.tilConfirmPassword.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupClickListeners() {
        binding.btnResetPassword.setOnClickListener {
            performResetPassword()
        }

        binding.tvBackToLogin.setOnClickListener {
            goToLogin()
        }
    }

    private fun validatePassword(password: String): Boolean {
        return when {
            password.isEmpty() -> {
                binding.tilNewPassword.error = "Password tidak boleh kosong"
                false
            }
            password.length < 6 -> {
                binding.tilNewPassword.error = "Password minimal 6 karakter"
                false
            }
            else -> {
                binding.tilNewPassword.error = null
                binding.tilNewPassword.isErrorEnabled = false
                true
            }
        }
    }

    private fun validatePasswordMatch(): Boolean {
        val password = binding.etNewPassword.text.toString()
        val confirm = binding.etConfirmPassword.text.toString()

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

    private fun performResetPassword() {
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        val isPasswordValid = validatePassword(newPassword)
        val isMatchValid = validatePasswordMatch()

        if (!isPasswordValid || !isMatchValid) return

        binding.btnResetPassword.isEnabled = false
        binding.progressReset.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Update password melalui Supabase
                SupabaseHelper.client.auth.updateUser {
                    password = newPassword
                }

                binding.btnResetPassword.isEnabled = true
                binding.progressReset.visibility = View.GONE

                // Tampilkan pesan sukses dan redirect ke login
                Toast.makeText(
                    this@ResetPasswordActivity,
                    "✅ Password berhasil direset! Silakan login dengan password baru.",
                    Toast.LENGTH_LONG
                ).show()

                goToLogin()

            } catch (e: Exception) {
                binding.btnResetPassword.isEnabled = true
                binding.progressReset.visibility = View.GONE

                // Tampilkan error
                Toast.makeText(
                    this@ResetPasswordActivity,
                    "Gagal mereset password: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}

