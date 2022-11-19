package com.ermilova.android.diary.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.ermilova.android.diary.MyApplication
import com.ermilova.android.diary.databinding.FragmentMainScreenBinding
import com.ermilova.android.diary.domain.usecase.GetEventsByTimeUseCase
import kotlinx.coroutines.launch
import java.util.*

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var listAdapter: EventsListAdapter
    private val viewModel: MainScreenViewModel by activityViewModels {
        MainScreenViewModelFactory(GetEventsByTimeUseCase((requireNotNull(activity).application as MyApplication).eventRepo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = EventsListAdapter { event ->
            val action = MainScreenFragmentDirections.actionMainScreenFragmentToDetailsFragment(event.id!!)
            findNavController().navigate(action)
        }
        binding.eventsList.adapter = listAdapter

        binding.calendar.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val startTime = calendar.timeInMillis

                lifecycle.coroutineScope.launch {
                    viewModel.getEvents(startTime).collect() { list ->
                        listAdapter.submitList(list)
                    }
                }
        })
    }
}