package com.ermilova.android.diary.ui.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ermilova.android.diary.databinding.EventsListItemBinding
import com.ermilova.android.diary.domain.EventModel
import java.text.SimpleDateFormat
import java.util.*

class EventsListAdapter(private val onItemClicked: (EventModel) -> Unit) :
    ListAdapter<EventModel, EventsListAdapter.EventListViewHolder>(DiffCallback) {

    class EventListViewHolder(private var binding: EventsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventModel) {
            binding.eventName.text = event.name
            binding.eventStartTime.text = SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(event.startTime)
            binding.eventFinishTime.text = SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(event.finishTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val viewHolder = EventListViewHolder(
            EventsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EventModel>() {
            override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}