package com.ermilova.android.diary.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey
    val id: Long?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "start_time")
    val startTime: Long,
    @ColumnInfo(name = "finish_time")
    val finishTime: Long,
    @ColumnInfo(name = "description")
    val description: String?
)

