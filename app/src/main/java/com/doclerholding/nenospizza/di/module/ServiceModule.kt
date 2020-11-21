package com.doclerholding.nenospizza.di.module

import com.doclerholding.nenospizza.data.api.NennosApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideNennosApiService(retrofit: Retrofit) : NennosApiService {
        return retrofit.create(NennosApiService::class.java)
    }
}