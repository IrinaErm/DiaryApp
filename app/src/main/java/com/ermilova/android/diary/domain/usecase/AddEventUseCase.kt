package com.ermilova.android.diary.domain.usecase

import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.EventRepo
import javax.inject.Inject

class AddEventUseCase @Inject constructor(private val eventRepo: EventRepo) {
    suspend operator fun invoke(event: EventModel) {
        eventRepo.addEvent(event)
    }
}