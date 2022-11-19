package com.ermilova.android.diary

import android.app.Application
import com.ermilova.android.diary.data.room.AppDatabase
import com.ermilova.android.diary.data.room.EventLocalDataSource
import com.ermilova.android.diary.data.room.EventRepoImpl
import com.ermilova.android.diary.domain.EventRepo

class MyApplication : Application() {
    private val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    val eventRepo: EventRepo by lazy {
        EventRepoImpl(EventLocalDataSource((database.eventDao())))
    }
}