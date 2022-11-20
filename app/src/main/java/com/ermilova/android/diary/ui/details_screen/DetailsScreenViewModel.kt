package com.ermilova.android.diary.ui.details_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.usecase.GetEventByIdUseCase

class DetailsScreenViewModel(private val getEventByIdUseCase: GetEventByIdUseCase) : ViewModel() {

    fun getEvent(eventId: Long) : LiveData<EventModel> {
        return getEventByIdUseCase(eventId).asLiveData()
    }

}