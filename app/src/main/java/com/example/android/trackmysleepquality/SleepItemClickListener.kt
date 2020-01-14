package com.example.android.trackmysleepquality

import com.example.android.trackmysleepquality.database.SleepNight

/**
 * We give a lambda of night id passed as a constructor as click listener that is it to onClick()
 */

class SleepItemClickListener(val clickListener: (sleepId: Long) -> Unit) {

    fun onClick(night: SleepNight) = clickListener(night.id)
}