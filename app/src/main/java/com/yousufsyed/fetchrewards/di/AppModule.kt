package com.yousufsyed.fetchrewards.di

import android.app.Application
import android.content.Context
import com.yousufsyed.fetchrewards.network.DefaultRewardsRestClient
import com.yousufsyed.fetchrewards.network.RewardsApi
import com.yousufsyed.fetchrewards.network.RewardsRestClient
import com.yousufsyed.fetchrewards.network.data.RewardItem
import com.yousufsyed.fetchrewards.provider.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    fun getGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @[Provides Singleton]
    fun getRewardsApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): RewardsApi = Retrofit.Builder()
        .baseUrl("https://fetch-hiring.s3.amazonaws.com")
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
        .create(RewardsApi::class.java)

    @Provides
    fun rewardsRestClient(impl: DefaultRewardsRestClient): RewardsRestClient = impl

    @Provides
    fun rewardsProvider(impl: DefaultRewardsProvider): RewardsProvider = impl

    @Provides
    fun getDispatcherProvider(impl: DefaultDispatcherProvider): DispatcherProvider = impl

    @Provides
    fun getEventLogger(impl: DefaultEventLogger): EventLogger = impl

    @Provides
    fun rewardsConverter(impl: DefaultRewardsConverter): RewardsConverter<RewardItem> = impl

    @[Provides Singleton]
    fun networkAvailabilityProvider(impl: DefaultNetworkAvailabilityProvider): NetworkAvailabilityProvider = impl
}