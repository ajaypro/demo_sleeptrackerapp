package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabaseDao


class SleepDetailViewModelFactory(
        private val database: SleepDatabaseDao,
        private val sleepNightKey: Long): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(database, sleepNightKey) as T
        }
        throw  IllegalArgumentException("UnkownClass")
    }
}