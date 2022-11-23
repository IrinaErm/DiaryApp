package com.ermilova.android.diary.ui.main_screen

import androidx.lifecycle.*
import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.usecase.GetEventsByTimeUseCase

class MainScreenViewModel(private val getEventsByTimeUseCase: GetEventsByTimeUseCase) : ViewModel() {

    private var _currentDate = MutableLiveData<Long>()
    val currentDate: LiveData<Long>
        get() {
            return _currentDate
        }

    val events: LiveData<List<EventModel>?> = currentDate.switchMap { startTime ->
        getEventsByTimeUseCase(startTime).asLiveData()
    }

    fun setCurrentDate(date: Long) {
        _currentDate.value = date
    }

}