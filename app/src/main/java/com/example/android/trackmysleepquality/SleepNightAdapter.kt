package com.example.android.trackmysleepquality

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import java.lang.ClassCastException

/**
 * We use list adapter which takes Data, VH as type and diffutil as constructor argument.
 * Uses diffutil which further is implemented by asyncListDiffer which runs in bg thread to provide
 * difference in list
 */

private const val ITEM_VIEW_TYPE_ITEM = 0
private const val ITEM_VIEW_TYPE_HEADER = 1

class SleepNightAdapter(private val clickListener: SleepItemClickListener) :
        ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> SleepItemViewHolder.from(parent)
            else -> throw ClassCastException("Unkown viewType $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SleepItemViewHolder -> {
                holder.bind((getItem(position) as DataItem.SleepNightItem).sleepNight, clickListener)
            }
        }

    }

    /**
     * adding items in submitList of listadapter
     */
    fun addAndSubmitList(list: List<SleepNight>?){
        val items = when(list){
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
        }
        submitList(items)
    }

    /**
     * creating viewholder instance from companion object which gives class instance and therefore
     * making viewholder class private constructor so no ones calls it
     */

    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view){
        companion object{
            fun from(parent: ViewGroup) = HeaderViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_item_view, parent, false))
        }
    }

    class SleepItemViewHolder private constructor(val binding: ListItemSleepNightBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): SleepItemViewHolder =
                    SleepItemViewHolder(ListItemSleepNightBinding.inflate(LayoutInflater.from(parent.context)
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