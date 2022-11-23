package com.ermilova.android.diary.ui.add_event_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.usecase.AddEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.ermilova.android.diary.utils.Result
import java.util.Calendar
import javax.inject.Inject

class AddEventScreenViewModel @Inject constructor(private val addEventUseCase: AddEventUseCase) : ViewModel() {

    private var _startTime = MutableLiveData<Long>()
    val startTime: LiveData<Long>
        get() {
            return _startTime
        }

    private var _finishTime = MutableLiveData<Long>()
    val finishTime: LiveData<Long>
        get() {
            return _finishTime
        }

    init {
        _startTime.value = Calendar.getInstance().timeInMillis
        _finishTime.value = Calendar.getInstance().timeInMillis
    }

    fun setStartTime(time: Long) {
        _startTime.value = time
    }

    fun setFinishTime(time: Long) {
        _finishTime.value = time
    }

    fun validateInput(name: String?): Result {
        if (_startTime.value!! > _finishTime.value!! || name.isNullOrBlank()) {
            return Result.Error
        }
        return Result.Success
    }

    fun addEvent(name: String, description: String?) {
        val event = EventModel(
            name = name,
            startTime = _startTime.value!!,
            finishTime = _finishTime.value!!,
            description = description
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addEventUseCase(event)
            }
        }
    }
}