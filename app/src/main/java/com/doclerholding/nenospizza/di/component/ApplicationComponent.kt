package com.doclerholding.nenospizza.di.component

import android.app.Application
import com.doclerholding.nenospizza.NennosApplication
import com.doclerholding.nenospizza.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        NetworkModule::class,
        ServiceModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: NennosApplication)
}