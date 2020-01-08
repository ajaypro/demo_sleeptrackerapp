/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */

class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val nights = database.getAllNights()

    // Not used since we have convertd textview to RV
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }.also {
        Log.i("VM - nightstring", "${it.value}")
    }
    // livedata for getting values start time and stop time
    private val tonight = MutableLiveData<SleepNight?>()

    // livedata for navigation
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight> = _navigateToSleepQuality


    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent : LiveData<Boolean> = _showSnackbarEvent


    init {
        uiScope.launch {
            tonight.value = getTonight()
            Log.i("VM Initial value ","${tonight.value}")
        }

    }

    private suspend fun getTonight(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            Log.i("VM night value ","${night}")
            if (night?.stopTime != night?.startTime) {
                night = null
            }
            night
        }
    }

    /**
     * Inserts new data into db after switching to IO which runs on bg thread
     */

    fun startTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonight()
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insertNights(night)
        }
    }



    /**
     * Stops timer and updates the db
     */

    fun stopTracking() {
        uiScope.launch {
            val lastNight = tonight.value ?: return@launch // check this
            lastNight.stopTime = System.currentTimeMillis()
            Log.i("VM lastTime stop time", "${lastNight.stopTime}")
            update(lastNight)

            //navigates to sleepquality once it has lastnight value
            _navigateToSleepQuality.value = lastNight
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.updateNight(night)
        }
    }

    /**
     * Button enabled and disable states
     */

    val startButtonVisible = Transformations.map(tonight){
        it == null
    }

    val stopButtonVisible = Transformations.map(tonight){
        it != null
    }

    val clearButtonVisible = Transformations.map(nights){
        it?.isNotEmpty()
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Clear button -> clear db details also livedata
     */

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
          _showSnackbarEvent.value = true
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clearAll()
        }
    }


    /**
     *  Navigation part
     *
     *  doneNavigating() resets the variable that triggers navigation.
     */

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    fun doneShowingSnackBar(){
        _showSnackbarEvent.value = false
    }
}

