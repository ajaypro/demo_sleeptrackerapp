package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.*

class SleepDetailViewModel(
        val database: SleepDatabaseDao,
        val sleepNightKey: Long = 0L) : ViewModel() {


    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?> = _navigateToSleepTracker

    private val night: LiveData<SleepNight>
    fun getNight() = night


    private val viewModelJob = Job()

    val dataSource = database

    init {
        night = dataSource.getNightWithId(sleepNightKey)
    }

    fun navigateToSleepTracker() {
        _navigateToSleepTracker.value = true
    }

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}