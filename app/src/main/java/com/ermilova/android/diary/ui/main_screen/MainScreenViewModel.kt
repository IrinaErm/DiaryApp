package com.ermilova.android.diary.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ermilova.android.diary.domain.usecase.GetEventsByTimeUseCase

class MainScreenViewModel(private val getEventsByTimeUseCase: GetEventsByTimeUseCase) : ViewModel() {

    fun getEvents(startTime: Long) = getEventsByTimeUseCase(startTime).asLiveData()

}