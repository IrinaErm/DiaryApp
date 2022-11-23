package com.ermilova.android.diary.domain.usecase

import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.EventRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(private val eventRepo: EventRepo) {
    operator fun invoke(eventId: Long): Flow<EventModel> {
        return eventRepo.getEventById(eventId)
    }
}