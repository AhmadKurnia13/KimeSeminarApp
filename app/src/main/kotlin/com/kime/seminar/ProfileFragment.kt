package com.kime.seminar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.kime.seminar.database.User
import com.kime.seminar.databinding.ActivityProfileBinding
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    private var currentUser: User? = null
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.ivProfilePicture.load(it) {
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())

        setupDropdowns()
        setupClickListeners()
        loadUserProfile()
    }

    private fun setupDropdowns() {
        val genders = listOf("Laki-laki", "Perempuan", "Lainnya")
        val genderAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        binding.actGender.setAdapter(genderAdapter)

        val relationshipStatuses = listOf("Single", "Dalam Hubungan", "Menikah", "Lainnya")
        val statusAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, relationshipStatuses)
        binding.actRelationshipStatus.setAdapter(statusAdapter)
    }

    private fun setupClickListeners() {
        binding.btnBack.visibility = View.GONE // Hide back button in fragment if not needed

        binding.btnChangePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }

        binding.btnChangePassword.setOnClickListener {
            // TODO: Navigate to change password screen
            Snackbar.make(binding.root, "Fitur ubah password akan segera hadir!", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun loadUserProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                currentUser = sessionManager.getCurrentUser()
                currentUser?.let { user ->
                    binding.etName.setText(user.name)
                    binding.etEmail.setText(user.email)
                    binding.actGender.setText(user.gender, false)
                    binding.etHobbies.setText(user.hobbies)
                    binding.etCity.setText(user.city)
                    binding.actRelationshipStatus.setText(user.relationshipStatus, false)
                    binding.etOccupation.setText(user.occupation)
                    binding.etAge.setText(user.age.toString())

                    user.profilePictureUrl?.let { url ->
                        binding.ivProfilePicture.load(url) {
                            crossfade(true)
                            placeholder(R.drawable.ic_person)
                            error(R.drawable.ic_person)
                            transformations(CircleCropTransformation())
                        }
                    }
                }
            } catch (e: Exception) {
                Snackbar.make(binding.root, "Gagal memuat profil: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProfile() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val gender = binding.actGender.text.toString().trim()
        val hobbies = binding.etHobbies.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val relationshipStatus = binding.actRelationshipStatus.text.toString().trim()
        val occupation = binding.etOccupation.text.toString().trim()
        val age = binding.etAge.text.toString().trim().toIntOrNull() ?: 0

        if (name.isEmpty() || email.isEmpty()) {
            Snackbar.make(binding.root, "Nama dan email tidak boleh kosong!", Snackbar.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                var profilePictureUrl = currentUser?.profilePictureUrl

                selectedImageUri?.let { uri ->
                    val bytes = requireContext().contentResolver.openInputStream(uri)?.readBytes()
                    bytes?.let {
                        val fileName = "avatar_${currentUser?.id}_${System.currentTimeMillis()}.jpg"
                        val bucket = SupabaseHelper.client.storage["avatars"]
                        bucket.upload(fileName, it)
                        profilePictureUrl = bucket.publicUrl(fileName)
                    }
                }

                val updatedUser = currentUser?.copy(
                    name = name,
                    email = email,
                    gender = gender,
                    hobbies = hobbies,
                    city = city,
                    relationshipStatus = relationshipStatus,
                    occupation = occupation,
                    age = age,
                    profilePictureUrl = profilePictureUrl
                )

                updatedUser?.let {
                    // Update local Room
                    sessionManager.updateUser(it)
                    
                    // Update remote Supabase
                    SupabaseHelper.client.postgrest["users"].update(
                        {
                            set("name", it.name)
                            set("gender", it.gender)
                            set("hobbies", it.hobbies)
                            set("city", it.city)
                            set("relationship_status", it.relationshipStatus)
                            set("occupation", it.occupation)
                            set("age", it.age)
                            set("profile_picture_url", it.profilePictureUrl)
                        }
                    ) {
                        filter {
                            eq("id", it.id)
                        }
                    }

                    Snackbar.make(binding.root, "Profil berhasil diperbarui!", Snackbar.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Snackbar.make(binding.root, "Gagal menyimpan profil: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
