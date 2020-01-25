package com.example.android.trackmysleepquality

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id

    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return newItem == oldItem
    }
}