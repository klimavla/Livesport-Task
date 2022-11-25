package io.vlk.livesporttask.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.vlk.livesporttask.network.ApiService
import io.vlk.livesporttask.network.DataRepository
import io.vlk.livesporttask.utils.model.LocaleConfiguration
import io.vlk.livesporttask.utils.readLocaleConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideLocaleConfiguration(application: Application): LocaleConfiguration {
        return application.readLocaleConfig()
    }

    @Singleton
    @Provides
    fun provideRetrofit(configuration: LocaleConfiguration): Retrofit {
        val httpClient = OkHttpClient
            .Builder()
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(configuration.baseUrl)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkServiceService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(service: ApiService) =
        DataRepository(service = service)
}