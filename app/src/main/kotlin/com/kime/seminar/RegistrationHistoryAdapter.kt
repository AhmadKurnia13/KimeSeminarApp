package com.kime.seminar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kime.seminar.database.RegistrationWithSeminar
import com.kime.seminar.databinding.ItemRegistrationHistoryBinding

class RegistrationHistoryAdapter :
    ListAdapter<RegistrationWithSeminar, RegistrationHistoryAdapter.RegistrationViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val binding = ItemRegistrationHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RegistrationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RegistrationViewHolder(private val binding: ItemRegistrationHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(registration: RegistrationWithSeminar) {
            binding.tvSeminarTitle.text = registration.title
            binding.tvRegistrationDate.text = "Didaftar pada: ${registration.registrationDate}"
            binding.tvSeminarDateTime.text = "${registration.date}, ${registration.time}"
            binding.tvSeminarLocation.text = registration.location
            binding.tvSeminarSpeaker.text = registration.speaker
            binding.chipStatus.text = registration.status
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<RegistrationWithSeminar>() {
            override fun areItemsTheSame(oldItem: RegistrationWithSeminar, newItem: RegistrationWithSeminar): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RegistrationWithSeminar, newItem: RegistrationWithSeminar): Boolean {
                return oldItem == newItem
            }
        }
    }
}
