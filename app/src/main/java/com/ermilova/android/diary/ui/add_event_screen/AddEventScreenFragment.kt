package com.ermilova.android.diary.ui.add_event_screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ermilova.android.diary.databinding.FragmentAddEventScreenBinding

class AddEventScreenFragment : DialogFragment() {

    private lateinit var binding: FragmentAddEventScreenBinding
    private lateinit var viewModel: AddEventScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventScreenBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[AddEventScreenViewModel::class.java]

        return binding.root
    }

}