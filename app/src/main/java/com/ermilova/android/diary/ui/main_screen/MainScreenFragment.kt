package com.ermilova.android.diary.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ermilova.android.diary.MyApplication
import com.ermilova.android.diary.databinding.EventsListItemBinding
import com.ermilova.android.diary.databinding.FragmentMainScreenBinding
import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.usecase.GetEventsByTimeUseCase
import java.text.SimpleDateFormat
import java.util.*


class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private val viewModel: MainScreenViewModel by activityViewModels {
        MainScreenViewModelFactory(GetEventsByTimeUseCase((requireNotNull(activity).application as MyApplication).eventRepo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        drawTable()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendar.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val startTime = calendar.timeInMillis

                getEvents(startTime)
            })
    }

    private fun drawTable() {
        val timeFormat = "%d:%02d\n%d:%02d"
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        var hour = calendar.get(Calendar.HOUR_OF_DAY)

        for (i in 0 until 24) {
            val tableRow = TableRow(requireContext())
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
            )

            val timeColumn = TextView(requireContext())
            timeColumn.text = timeFormat.format(hour, 0, hour.plus(1), 0)
            tableRow.addView(timeColumn, 0)

            hour = hour.plus(1)

            binding.eventsTable.addView(tableRow, i)
        }
    }

    private fun getEvents(startTime: Long) {
        clearTable()
        viewModel.getEvents(startTime).observe(viewLifecycleOwner) { list ->
            list?.let {
                for (i in list.indices) {
                    val time = SimpleDateFormat("HH", Locale.getDefault()).format(list[i].startTime)
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, time.toInt())
                    val pos = calendar.get(Calendar.HOUR_OF_DAY)

                    val tableRow = binding.eventsTable.getChildAt(pos) as TableRow
                    tableRow.addView(
                        getEventCard(list[i], tableRow).root
                    )
                }
            }
        }
    }

    private fun getEventCard(
        event: EventModel,
        container: ViewGroup?
    ): EventsListItemBinding {
        val b = EventsListItemBinding.inflate(layoutInflater, container, false)
        b.eventName.text = event.name
        b.eventStartTime.text =
            SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(event.startTime)
        b.eventFinishTime.text =
            SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault()).format(event.finishTime)
        b.root.setOnClickListener {
            val action =
                MainScreenFragmentDirections.actionMainScreenFragmentToDetailsFragment(event.id!!)
            findNavController().navigate(action)
        }
        return b
    }

    private fun clearTable() {
        for (i in 0 until binding.eventsTable.childCount) {
            val row = (binding.eventsTable.getChildAt(i) as TableRow)
            row.removeViews(1, row.childCount - 1)
        }
    }
}