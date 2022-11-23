package com.ermilova.android.diary.ui.add_event_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CalendarView
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ermilova.android.diary.MyApplication
import com.ermilova.android.diary.databinding.FragmentDateTimePickerBinding
import com.ermilova.android.diary.utils.ViewModelFactory
import java.util.Calendar
import javax.inject.Inject

class DateTimePickerFragment : DialogFragment() {

    private lateinit var binding: FragmentDateTimePickerBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AddEventScreenViewModel>

    private val viewModel: AddEventScreenViewModel by activityViewModels {
        viewModelFactory
    }

    private lateinit var timeType: TimeType
    private lateinit var time: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDateTimePickerBinding.inflate(inflater, container, false)

        initPickers()

        val bundle = arguments
        bundle?.let {
            val args = DateTimePickerFragmentArgs.fromBundle(bundle)
            timeType = args.timeType
        }
        time = Calendar.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendar.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, day ->
                time = Calendar.getInstance().also { calendar ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                }
            })

        binding.cancelBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.okBtn.setOnClickListener {
            when (timeType) {
                TimeType.START_TIME -> viewModel.setStartTime(
                    time.also { calendar ->
                        calendar.set(Calendar.HOUR_OF_DAY, binding.hour.value)
                        calendar.set(Calendar.MINUTE, binding.minute.value)
                    }.timeInMillis
                )
                TimeType.FINISH_TIME -> viewModel.setFinishTime(
                    time.also { calendar ->
                        calendar.set(Calendar.HOUR_OF_DAY, binding.hour.value)
                        calendar.set(Calendar.MINUTE, binding.minute.value)
                }.timeInMillis)
            }

            findNavController().navigateUp()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as MyApplication).appComponent.dateTimePickerComponent().create().inject(this)
    }

    private fun initPickers() {
        binding.hour.maxValue = 23;
        binding.hour.minValue = 0;
        binding.hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.minute.maxValue = 59;
        binding.minute.minValue = 0;
        binding.hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );
    }
}