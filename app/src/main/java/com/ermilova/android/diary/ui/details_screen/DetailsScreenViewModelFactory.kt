package com.ermilova.android.diary.ui.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ermilova.android.diary.domain.usecase.GetEventByIdUseCase

class DetailsScreenViewModelFactory(private val getEventByIdUseCase: GetEventByIdUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsScreenViewModel(getEventByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}