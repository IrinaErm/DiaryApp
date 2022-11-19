package com.ermilova.android.diary.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ermilova.android.diary.domain.usecase.GetEventsByTimeUseCase

class MainScreenViewModelFactory(private val getEventsByTimeUseCase: GetEventsByTimeUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainScreenViewModel(getEventsByTimeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}