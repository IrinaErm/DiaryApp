package com.ermilova.android.diary.di

import android.app.Application
import com.ermilova.android.diary.di.component.AddEventScreenComponent
import com.ermilova.android.diary.di.component.DateTimePickerComponent
import com.ermilova.android.diary.di.component.DetailsScreenComponent
import com.ermilova.android.diary.di.component.MainScreenComponent
import com.ermilova.android.diary.di.module.CacheModule
import com.ermilova.android.diary.di.module.EventModule
import com.ermilova.android.diary.di.module.ViewModelModule
import com.ermilova.android.diary.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheModule::class, ViewModelModule::class, EventModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun mainScreenComponent(): MainScreenComponent.Factory
    fun addEventScreenComponent(): AddEventScreenComponent.Factory
    fun detailsScreenComponent(): DetailsScreenComponent.Factory
    fun dateTimePickerComponent(): DateTimePickerComponent.Factory

    fun inject(mainActivity: MainActivity)
}