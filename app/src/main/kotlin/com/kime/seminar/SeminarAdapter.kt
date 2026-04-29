package com.kime.seminar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kime.seminar.databinding.ItemSeminarBinding

class SeminarAdapter(private val onItemClick: (Seminar) -> Unit) :
    ListAdapter<Seminar, SeminarAdapter.SeminarViewHolder>(SeminarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeminarViewHolder {
        val binding = ItemSeminarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SeminarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeminarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SeminarViewHolder(private val binding: ItemSeminarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val clickListener = {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
            binding.root.setOnClickListener { clickListener() }
            binding.btnViewDetail.setOnClickListener { clickListener() }
        }

        fun bind(seminar: Seminar) {
            binding.apply {
                tvSeminarTitle.text = seminar.title
                tvSeminarSpeaker.text = "👨‍🏫 ${seminar.speaker}"
                tvSeminarDate.text = "📅 ${seminar.eventDate}"
                tvSeminarCapacity.text = "👥 ${seminar.capacity} peserta"
                tvSeminarLocation.text = seminar.location ?: "Lokasi belum ditentukan"
                tvSeminarTime.text = seminar.time ?: "Waktu belum ditentukan"
            }
        }
    }

    class SeminarDiffCallback : DiffUtil.ItemCallback<Seminar>() {
        override fun areItemsTheSame(oldItem: Seminar, newItem: Seminar): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Seminar, newItem: Seminar): Boolean {
            return oldItem == newItem
        }
    }
}
