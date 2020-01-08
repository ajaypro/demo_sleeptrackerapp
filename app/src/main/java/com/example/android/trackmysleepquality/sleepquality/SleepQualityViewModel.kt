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

package com.example.android.trackmysleepquality.sleepquality

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SleepQualityViewModel(val database: SleepDatabaseDao,
                            private val sleepNightKey: Long = 0L) : ViewModel(){

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleeptracker: LiveData<Boolean?> = _navigateToSleepTracker

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun onQualitySelected(sleepQuality: Int){

        uiScope.launch {
            withContext(Dispatchers.IO){
                val toNight = database.getNight(sleepNightKey)?: return@withContext
                toNight.sleepQuality = sleepQuality
                database.updateNight(toNight)
            }
            _navigateToSleepTracker.value = true

        }
    }

    fun doneNavigating(){
          _navigateToSleepTracker.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
