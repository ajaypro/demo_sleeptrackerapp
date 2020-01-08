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

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDatabaseDao {

    @Insert
    fun insertNights(sleepNight: SleepNight)

    @Update
    fun updateNight(night: SleepNight)

    @Query("SELECT * from sleep_quality WHERE id = :key")
    fun getNight(key: Long): SleepNight?

    @Query("SELECT * FROM sleep_quality ORDER BY id DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("SELECT * FROM sleep_quality ORDER BY id DESC LIMIT 1")
    fun getTonight(): SleepNight?

    @Query("DELETE FROM sleep_quality")
    fun clearAll()
}
