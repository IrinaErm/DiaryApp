package com.ermilova.android.diary.domain

import com.google.gson.annotations.SerializedName

data class EventModel(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String,
    @SerializedName("date_start")
    val startTime: Long,
    @SerializedName("date_finish")
    val finishTime: Long,
    @SerializedName("description")
    val description: String?
)