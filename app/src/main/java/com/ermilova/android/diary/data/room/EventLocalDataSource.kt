package com.ermilova.android.diary.data.room

import kotlinx.coroutines.flow.Flow

class EventLocalDataSource(private val eventDao: EventDao) {
    suspend fun addEvent(event: EventEntity) {
        eventDao.insert(event)
    }

    fun getEventsByTime(startTime: Long, finishTime: Long): Flow<List<EventEntity>?> {
        return eventDao.getAllByTime(startTime, finishTime)
    }
}