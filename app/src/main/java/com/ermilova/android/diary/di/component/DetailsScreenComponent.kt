package com.ermilova.android.diary.di.component

import com.ermilova.android.diary.ui.details_screen.DetailsScreenFragment
import dagger.Subcomponent

@Subcomponent
interface DetailsScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsScreenComponent
    }

    fun inject(detailsScreenFragment: DetailsScreenFragment)
}