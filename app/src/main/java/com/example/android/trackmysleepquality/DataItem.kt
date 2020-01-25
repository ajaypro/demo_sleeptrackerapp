package com.example.android.trackmysleepquality

import com.example.android.trackmysleepquality.database.SleepNight


/**
 * DataItem class to hold dataitems which is abstract out from adapter, so adapter deals only with items
 * now dataset will be list of dataitem holders that is sleepnight and header.
 * Also createing a sealed class which encloses these dataItems, no other part of adapter class can create
 * another dataItem class type that will break.
 */
sealed class DataItem {
    // For diffutil to identify the id of data item
    abstract val id: Long
    // data class for holding sleepNightItems
    data class SleepNightItem(val sleepNight: SleepNight): DataItem(){
        override val id = sleepNight.id
    }
  // For header Item
    object Header: DataItem(){
      override val id = Long.MIN_VALUE
  }
}