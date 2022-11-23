package com.ermilova.android.diary.ui.add_event_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ermilova.android.diary.domain.usecase.AddEventUseCase

class AddEventScreenViewModelFactory(private val addEventUseCase: AddEventUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEventScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddEventScreenViewModel(addEventUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}