package com.doclerholding.nenospizza.di.module

import com.doclerholding.nenospizza.utils.DefaultViewContainer
import com.doclerholding.nenospizza.utils.ViewContainer
import dagger.Module
import dagger.Provides


@Module
class UiModule {
    @Provides
    fun provideViewContainer(): ViewContainer {
        return DefaultViewContainer()
    }
}
