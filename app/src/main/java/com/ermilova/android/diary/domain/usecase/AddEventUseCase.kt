package com.ermilova.android.diary.domain.usecase

import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.EventRepo

class AddEventUseCase(private val eventRepo: EventRepo) {
    suspend operator fun invoke(event: EventModel) {
        eventRepo.addEvent(event)
    }
}