package com.ermilova.android.diary.domain.usecase

import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.EventRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsByTimeUseCase @Inject constructor(private val eventRepo: EventRepo) {
    operator fun invoke(startTime: Long): Flow<List<EventModel>?> {
        return eventRepo.getEventsByTime(startTime)
    }
}