package com.ermilova.android.diary.di.module

import com.ermilova.android.diary.data.room.EventRepoImpl
import com.ermilova.android.diary.domain.EventRepo
import dagger.Binds
import dagger.Module

@Module
abstract class EventModule {
    @Binds
    abstract fun provideEventRepo(repo: EventRepoImpl): EventRepo
}