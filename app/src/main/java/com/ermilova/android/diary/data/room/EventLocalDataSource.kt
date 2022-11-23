package com.ermilova.android.diary.data.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventLocalDataSource @Inject constructor(private val eventDao: EventDao) {
    suspend fun addEvent(event: EventEntity) {
        eventDao.insert(event)
    }

    fun getEventsByTime(startTime: Long, finishTime: Long): Flow<List<EventEntity>?> {
        return eventDao.getAllByTime(startTime, finishTime)
    }

    fun getEventById(eventId: Long) : Flow<EventEntity> {
        return eventDao.getEventById(eventId)
    }
}