package com.ermilova.android.diary.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ermilova.android.diary.MyApplication
import com.ermilova.android.diary.databinding.EventsListItemBinding
import com.ermilova.android.diary.databinding.FragmentMainScreenBinding
import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.usecase.GetEventsByTimeUseCase
import java.text.SimpleDateFormat
import java.util.Locale

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private val viewModel: MainScreenViewModel by activityViewModels {
        MainScreenViewModelFactory(GetEventsByTimeUseCase((requireNotNull(activity).application as MyApplication).eventRepo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        drawTable()
        viewModel.currentDate.value?.let { date ->
            binding.calendar.date = date.timeInMillis
        }
        viewModel.events.observe(viewLifecycleOwner) { list ->
            showEvents(list)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendar.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, day ->
                viewModel.setCurrentDate(year, month, day)
            })

        binding.addBtn.setOnClickListener {
            val action =
                MainScreenFragmentDirections.actionMainScreenFragmentToAddEventScreenFragment()
            findNavController().navigate(action)

        }
    }

    private fun drawTable() {
        val timeFormat = "%d:%02d\n%d:%02d"
        var hour = 0

        for (i in 0 until 24) {
            val tableRow = TableRow(requireContext())
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
            )

            val timeColumn = TextView(requireContext())
            timeColumn.text = timeFormat.format(hour, 0, (++hour) % 24, 0)
            timeColumn.setPadding(8, 0, 8, 8)
            tableRow.addView(timeColumn, 0)

            val linearLayout = LinearLayout(requireContext())
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.visibility = View.VISIBLE
            tableRow.addView(linearLayout, 1)

            binding.eventsTable.addView(tableRow, i)
        }
    }

    private fun showEvents(list: List<EventModel>?) {
        clearTable()
        binding.scrollView.scrollTo(0, 0)
        val startTime = viewModel.currentDate.value!!.timeInMillis
        val endOfDay = viewModel.currentDate.value!!.timeInMillis + (1000 * 60 * 60 * 24) - 1

        list?.let {
            for (i in list.indices) {
                var start = if (list[i].startTime >= startTime) list[i].startTime else startTime
                var finish =
                    if (list[i].finishTime <= endOfDay) list[i].finishTime else endOfDay
                if (list[i].finishTime - 1 == startTime - 1) {
                    continue
                }

                while (start <= finish) {
                    var pos =
                        SimpleDateFormat("HH", Locale.getDefault()).format(start).toInt()
                    val tableRow = binding.eventsTable.getChildAt(pos) as TableRow

                    val layout = tableRow.getChildAt(1) as LinearLayout
                    val params = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    layout.layoutParams = params
                    layout.setPadding(0, 0, 0, 0)

                    layout.addView(
                        getEventCard(list[i], tableRow).root
                    )
                    start += 1000 * 60 * 60
                    ++pos
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
            (row.getChildAt(1) as LinearLayout).removeAllViews()
        }
    }
}