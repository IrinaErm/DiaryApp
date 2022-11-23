package com.ermilova.android.diary.ui.details_screen

import androidx.lifecycle.*
import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.usecase.GetEventByIdUseCase

class DetailsScreenViewModel(private val getEventByIdUseCase: GetEventByIdUseCase) : ViewModel() {

    private var _eventId = MutableLiveData<Long>()
    val event: LiveData<EventModel> = _eventId.switchMap { id ->
        getEventByIdUseCase(id).asLiveData()
    }

    fun setId(eventId: Long) {
        _eventId.value = eventId
    }

}