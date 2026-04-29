package com.kime.seminar

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.kime.seminar.database.SeminarRepository
import com.kime.seminar.database.UserRepository
import com.kime.seminar.databinding.FragmentTicketQrBinding
import kotlinx.coroutines.launch

class TicketQRFragment : Fragment() {

    private var _binding: FragmentTicketQrBinding? = null
    private val binding get() = _binding!!

    private lateinit var seminarRepository: SeminarRepository
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    private var seminarId: Long = -1
    private var registrationId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            seminarId = it.getLong(ARG_SEMINAR_ID, -1)
            registrationId = it.getLong(ARG_REGISTRATION_ID, -1)
        }
        
        seminarRepository = SeminarRepository(requireContext())
        userRepository = UserRepository(requireContext())
        sessionManager = SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        loadTicketData()
        generateQRCode()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnDownloadTicket.setOnClickListener {
            // TODO: Implement ticket download
            Snackbar.make(binding.root, "Fitur download tiket akan segera hadir!", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnShareTicket.setOnClickListener {
            // TODO: Implement ticket sharing
            Snackbar.make(binding.root, "Fitur bagikan tiket akan segera hadir!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun loadTicketData() {
        lifecycleScope.launch {
            try {
                val seminar = seminarRepository.getSeminarById(seminarId)
                val user = sessionManager.getCurrentUser()

                seminar?.let {
                    binding.tvSeminarTitle.text = it.title
                    binding.tvSeminarDateTime.text = "${it.date}, ${it.time}"
                    binding.tvSeminarLocation.text = it.location
                }

                user?.let {
                    binding.tvUserName.text = it.name
                    binding.tvUserEmail.text = it.email
                }
            } catch (e: Exception) {
                Snackbar.make(binding.root, "Gagal memuat data tiket: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateQRCode() {
        try {
            // Create QR code content (could include seminar ID, registration ID, user info)
            val qrContent = "TICKET|$seminarId|$registrationId|${System.currentTimeMillis()}"

            val qrCodeWriter = QRCodeWriter()
            val bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 512, 512)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)

            binding.ivQRCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
            Snackbar.make(binding.root, "Gagal generate QR code: ${e.message}", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SEMINAR_ID = "seminarId"
        private const val ARG_REGISTRATION_ID = "registrationId"

        fun newInstance(seminarId: Long, registrationId: Long) = TicketQRFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_SEMINAR_ID, seminarId)
                putLong(ARG_REGISTRATION_ID, registrationId)
            }
        }
    }
}
