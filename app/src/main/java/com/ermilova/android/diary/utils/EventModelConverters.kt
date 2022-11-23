package com.ermilova.android.diary.utils

import com.ermilova.android.diary.data.room.EventEntity
import com.ermilova.android.diary.domain.EventModel

fun EventModel.toEntity(): EventEntity {
    return EventEntity(
        id, name, startTime, finishTime, description
    )
}

fun EventEntity.toModel(): EventModel {
    return EventModel(
        id, name, startTime, finishTime, description
    )
}