package com.ermilova.android.diary.di.component

import com.ermilova.android.diary.ui.main_screen.MainScreenFragment
import dagger.Subcomponent

@Subcomponent
interface MainScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainScreenComponent
    }

    fun inject(mainScreenFragment: MainScreenFragment)
}