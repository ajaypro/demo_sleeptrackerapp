package com.example.android.trackmysleepquality.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailBinding
import com.example.android.trackmysleepquality.sleepquality.SleepQualityFragmentArgs

class SleepDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentSleepDetailBinding =
                inflate(inflater, R.layout.fragment_sleep_detail, container, false)

        // gettting application context using kotlin requireNotNull function
        val application = requireNotNull(this.activity).application

        // getting datasource
        val datasource = SleepDatabase.getInstance(application).sleepDataBaseDao

        val arguments = SleepDetailFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = SleepDetailViewModelFactory(datasource, arguments.sleepNightKey)
        val sleepDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepDetailViewModel::class.java)

        binding.apply {
            this.sleepDetailViewModel = sleepDetailViewModel
            lifecycleOwner = this@SleepDetailFragment
        }

        sleepDetailViewModel.navigateToSleepTracker.observe(this, Observer {
            if(it == true){
                this.findNavController().navigate(SleepDetailFragmentDirections
                        .actionSleepDetailFragmentToSleepTrackerFragment())

                sleepDetailViewModel.doneNavigating()
            }

        })

        return binding.root
    }
}