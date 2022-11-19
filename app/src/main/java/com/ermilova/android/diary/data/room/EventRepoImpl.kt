package com.ermilova.android.diary.data.room

import com.ermilova.android.diary.domain.EventModel
import com.ermilova.android.diary.domain.EventRepo
import com.ermilova.android.diary.utils.toEntity
import com.ermilova.android.diary.utils.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepoImpl(private val localDataSource: EventLocalDataSource) : EventRepo {
    override suspend fun addEvent(event: EventModel) {
        localDataSource.addEvent(event.toEntity())
    }

    override fun getEventsByTime(startTime: Long): Flow<List<EventModel>?> {
        val finishTime = startTime + (1000 * 60 * 60 * 24)

        return localDataSource.getEventsByTime(startTime, finishTime).map { list ->
            list?.map { entity ->
                entity.toModel()
            }
        }
    }
}