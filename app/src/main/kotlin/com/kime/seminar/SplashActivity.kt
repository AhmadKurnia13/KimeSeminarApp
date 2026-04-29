package com.kime.seminar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.kime.seminar.databinding.ActivitySplashBinding

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest
import android.util.Log

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Test Supabase Connection Instantly
        testSupabaseConnection()

        // Animasi logo
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        binding.ivLogo.startAnimation(fadeIn)
        binding.tvAppName.startAnimation(slideUp)
        binding.tvTagline.startAnimation(slideUp)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateNext()
        }, 2500)
    }

    private fun testSupabaseConnection() {
        lifecycleScope.launch {
            try {
                // Try to fetch a single row from seminars to test connection
                val result = SupabaseHelper.client.postgrest["seminars"].select()
                Log.d("KONEKSI_CEK", "Koneksi Supabase Berhasil! ✅")
            } catch (e: Exception) {
                Log.e("KONEKSI_CEK", "Koneksi Supabase Gagal: ${e.message} ❌")
            }
        }
    }

    private fun navigateNext() {
        val intent = if (sessionManager.isLoggedIn()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
