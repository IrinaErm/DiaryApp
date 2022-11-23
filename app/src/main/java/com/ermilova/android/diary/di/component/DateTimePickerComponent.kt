package com.ermilova.android.diary.di.component

import com.ermilova.android.diary.ui.add_event_screen.DateTimePickerFragment
import dagger.Subcomponent

@Subcomponent
interface DateTimePickerComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DateTimePickerComponent
    }

    fun inject(dateTimePickerFragment: DateTimePickerFragment)
}