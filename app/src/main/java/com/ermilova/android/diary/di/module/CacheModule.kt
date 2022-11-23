package com.ermilova.android.diary.di.module

import android.app.Application
import com.ermilova.android.diary.data.room.AppDatabase
import com.ermilova.android.diary.data.room.EventDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun provideRoomInstance(application: Application): AppDatabase {
        return AppDatabase.getInstance(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }
}