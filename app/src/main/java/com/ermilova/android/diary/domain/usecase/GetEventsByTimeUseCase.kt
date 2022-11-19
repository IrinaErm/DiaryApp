package com.ermilova.android.diary.domain.usecase

import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.EventRepo
import kotlinx.coroutines.flow.Flow

class GetEventsByTimeUseCase(private val eventRepo: EventRepo) {
    operator fun invoke(startTime: Long): Flow<List<EventModel>?> {
        return eventRepo.getEventsByTime(startTime)
    }
}