package com.ermilova.android.diary.ui.add_event_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ermilova.android.diary.MyApplication
import com.ermilova.android.diary.R
import com.ermilova.android.diary.databinding.FragmentAddEventScreenBinding
import com.ermilova.android.diary.domain.usecase.AddEventUseCase
import com.ermilova.android.diary.utils.Result
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

class AddEventScreenFragment : DialogFragment() {

    private lateinit var binding: FragmentAddEventScreenBinding
    private val viewModel: AddEventScreenViewModel by activityViewModels {
        AddEventScreenViewModelFactory(AddEventUseCase((requireNotNull(activity).application as MyApplication).eventRepo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEventScreenBinding.inflate(layoutInflater, container, false)

        viewModel.startTime.observe(viewLifecycleOwner) { time ->
            binding.eventStartTime.hint =
                SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(time)
        }
        viewModel.finishTime.observe(viewLifecycleOwner) { time ->
            binding.eventFinishTime.hint =
                SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(time)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.addBtn.setOnClickListener {
            if (viewModel.validateInput(binding.eventName.text.toString().trim()) is Result.Success) {
                viewModel.addEvent(
                    name = binding.eventName.text.toString(),
                    description = binding.eventDescription.text.toString()
                )
                findNavController().navigateUp()
            } else {
                Snackbar.make(view.rootView, getString(R.string.invalid_input_error), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        binding.eventStartTime.setOnClickListener {
            val action =
                AddEventScreenFragmentDirections.actionAddEventScreenFragmentToDateTimePickerFragment(
                    TimeType.START_TIME
                )
            findNavController().navigate(action)
        }

        binding.eventFinishTime.setOnClickListener {
            val action =
                AddEventScreenFragmentDirections.actionAddEventScreenFragmentToDateTimePickerFragment(
                    TimeType.FINISH_TIME
                )
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

}