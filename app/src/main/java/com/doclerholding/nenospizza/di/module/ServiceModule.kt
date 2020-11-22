package com.doclerholding.nenospizza.di.module

import com.doclerholding.nenospizza.data.api.CheckoutApiService
import com.doclerholding.nenospizza.data.api.NennosApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideNennosApiService(@Named("nennos")retrofit: Retrofit) : NennosApiService {
        return retrofit.create(NennosApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCheckoutApiService(@Named("checkout")retrofit: Retrofit) : CheckoutApiService {
        return retrofit.create(CheckoutApiService::class.java)
    }
}