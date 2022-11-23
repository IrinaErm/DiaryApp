package com.ermilova.android.diary.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(eventEntity: EventEntity)

    @Query("SELECT * FROM events WHERE " +
            "(start_time BETWEEN :startTime and :finishTime " +
            "or finish_time BETWEEN :startTime and :finishTime) " +
            " or (start_time <= :startTime and finish_time >= :finishTime)")
    fun getAllByTime(startTime: Long, finishTime: Long): Flow<List<EventEntity>?>

    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventById(eventId: Long): Flow<EventEntity>
}