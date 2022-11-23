package com.ermilova.android.diary

import android.app.Application
import com.ermilova.android.diary.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent = DaggerAppComponent.builder().application(this).build()
}