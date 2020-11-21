package com.doclerholding.nenospizza.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.doclerholding.nenospizza.data.api.NennosApiService
import com.doclerholding.nenospizza.data.repository.CartRepository
import com.doclerholding.nenospizza.data.repository.PizzaRepository
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePizzaRepository(api: NennosApiService) : PizzaRepository {
        return PizzaRepository(api)
    }

    @Provides
    @Singleton
    fun provideCartRepository() : CartRepository {
        return CartRepository()
    }

}