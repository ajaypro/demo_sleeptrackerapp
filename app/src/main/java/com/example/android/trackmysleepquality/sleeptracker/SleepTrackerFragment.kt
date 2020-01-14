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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.SleepItemClickListener
import com.example.android.trackmysleepquality.SleepNightAdapter
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sleep_tracker.view.*

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        // gettting application context using kotlin requireNotNull function
        val application = requireNotNull(this.activity).application

        // getting datasource
        val datasource = SleepDatabase.getInstance(application).sleepDataBaseDao


        val viewModelFactory = SleepTrackerViewModelFactory(datasource, application)
        val sleepViewModel = ViewModelProviders.of(this, viewModelFactory)[SleepTrackerViewModel::class.java]

        val sleepNightAdapter = SleepNightAdapter(SleepItemClickListener {
            //Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()

            sleepViewModel.onSleepNightclicked(it)
        })


        val gridLayoutManager = GridLayoutManager(activity, 3)

        binding.sleepList.apply {
            adapter = sleepNightAdapter
            layoutManager = gridLayoutManager
        }

        binding.apply {

            // bindining viewmodel variable
            sleepTrackerViewModel = sleepViewModel

            // binding lifeycleowner for livedata
            lifecycleOwner = this@SleepTrackerFragment

        }

        sleepViewModel.nights.observe(this, Observer {
            it?.let { sleepNightAdapter.submitList(it) }
        })

        sleepViewModel.navigateToSleepQuality.observe(this, Observer {
            it?.let {
                findNavController().navigate(
                        SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(it.id)
                )
                sleepViewModel.doneNavigating()
            }
        })
        sleepViewModel.navigateToSleepDetail.observe(this, Observer {
            it?.let {
                findNavController().navigate(
                        SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepDetailFragment(it)
                )
                sleepViewModel.onSleepNightNavigated()
            }

        })

        sleepViewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT).show()

                sleepViewModel.doneShowingSnackBar()
            }
        })


        return binding.root
    }
}
