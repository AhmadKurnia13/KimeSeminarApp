package com.kime.seminar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kime.seminar.databinding.ActivityHomeBinding
import android.util.Log
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            goToLogin()
            return
        }

        setupToolbar()
        setupUserInfo()
        setupAnimations()
        setupClickListeners()

        // lifecycleScope.launch digunakan untuk menjalankan proses di latar belakang (Coroutine)
        lifecycleScope.launch {
            try {
                // Memanggil fungsi fetchSeminars
                val listSeminar = fetchSeminars()

                // Melihat hasilnya di jendela Logcat Android Studio
                for (seminar in listSeminar) {
                    Log.d("DATA_SEMINAR", "Judul: ${seminar.title}, Pembicara: ${seminar.speaker}")
                }
            } catch (e: Exception) {
                Log.e("ERROR_SUPABASE", "Gagal mengambil data: ${e.message}")
            }
        }
    }

    // Fungsi suspend untuk mengambil data dari Supabase
    private suspend fun fetchSeminars(): List<Seminar> {
        return withContext(Dispatchers.IO) {
            // Mengambil semua data dari tabel "seminars"
            SupabaseHelper.client.postgrest["seminars"]
                .select()
                .decodeList<Seminar>()
        }
    }


    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
    }
    private fun setupUserInfo() {
        val name = sessionManager.getUserName()
        val email = sessionManager.getUserEmail()
        val firstName = name.split(" ").firstOrNull() ?: name

        binding.tvWelcome.text = "Halo, $firstName! 👋"
        binding.tvUserEmail.text = email
        binding.tvUserName.text = name

        // Inisial avatar
        val initials = name.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
            .joinToString("")
        binding.tvAvatar.text = initials.ifEmpty { "U" }
    }

    private fun setupAnimations() {
        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        binding.cardHeader.startAnimation(slideDown)
        binding.cardDaftarSeminar.startAnimation(slideUp)
        binding.cardInfo1.startAnimation(fadeIn)
        binding.cardInfo2.startAnimation(fadeIn)
        binding.cardInfo3.startAnimation(fadeIn)
    }

    private fun setupClickListeners() {
        binding.btnDaftarSeminar.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.cardDaftarSeminar.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this, R.style.KimeAlertDialog)
            .setTitle("Keluar")
            .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
            .setPositiveButton("Ya, Keluar") { _, _ ->
                sessionManager.logout()
                goToLogin()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
