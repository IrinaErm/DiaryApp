package com.ermilova.android.diary.di.component

import com.ermilova.android.diary.ui.add_event_screen.AddEventScreenFragment
import dagger.Subcomponent

@Subcomponent
interface AddEventScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AddEventScreenComponent
    }

    fun inject(addEventScreenFragment: AddEventScreenFragment)
}