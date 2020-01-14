package com.example.android.trackmysleepquality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.SleepNightAdapter.ViewHolder.Companion.from
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

/**
 * We use list adapter which takes Data, VH as type and diffutil as constructor argument.
 * Uses diffutil which further is implemented by asyncListDiffer which runs in bg thread to provide
 * difference in list
 */
class SleepNightAdapter(val clickListener: SleepItemClickListener) :
        ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    /**
     * creating viewholder instance from companion object which gives class instance and therefore
     * making viewholder class private constructor so no ones calls it
     */

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder =
                    ViewHolder(ListItemSleepNightBinding.inflate(LayoutInflater.from(parent.context)
                            , parent, false))
        }

        fun bind(item: SleepNight, clickListener: SleepItemClickListener) {
            binding.sleepNight = item
            binding.sleepClickListener = clickListener
            /**
             * This call is an optimization that asks data binding to execute any pending bindings right away.
             * It's always a good idea to call executePendingBindings() when you use binding adapters in a RecyclerView,
             * because it can slightly speed up sizing the views.
             */
            binding.executePendingBindings()
        }
    }
}