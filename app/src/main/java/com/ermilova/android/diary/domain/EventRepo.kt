package com.ermilova.android.diary.domain

import kotlinx.coroutines.flow.Flow

interface EventRepo {
    suspend fun addEvent(event: EventModel)
    fun getEventsByTime(startTime: Long): Flow<List<EventModel>?>
}