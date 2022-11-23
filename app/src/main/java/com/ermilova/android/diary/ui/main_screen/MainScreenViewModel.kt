package com.ermilova.android.diary.ui.main_screen

import androidx.lifecycle.*
import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.usecase.GetEventsByTimeUseCase
import java.util.Calendar

class MainScreenViewModel(private val getEventsByTimeUseCase: GetEventsByTimeUseCase) :
    ViewModel() {

    private var _currentDate = MutableLiveData<Calendar>()
    val currentDate: LiveData<Calendar>
        get() {
            return _currentDate
        }

    val events: LiveData<List<EventModel>?> = currentDate.switchMap { startTime ->
        getEventsByTimeUseCase(startTime.timeInMillis).asLiveData()
    }

    init {
        _currentDate.value = Calendar.getInstance().also { calendar ->
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
    }

    fun setCurrentDate(year: Int, month: Int, day: Int) {
        _currentDate.value = _currentDate.value.also{ calendar ->
            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, month)
            calendar?.set(Calendar.DAY_OF_MONTH, day)
        }
    }

}