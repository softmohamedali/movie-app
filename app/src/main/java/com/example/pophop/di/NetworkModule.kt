package com.example.pophop.di

import com.example.pophop.data.remote.MovieApi
import com.example.pophop.utils.Constanse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient
    {
        return OkHttpClient().newBuilder()
                .callTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(15,TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideGsonconverterFactory():GsonConverterFactory
    {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,client:OkHttpClient):Retrofit
    {
        return Retrofit.Builder()
                .baseUrl(Constanse.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MovieApi
    {
        return retrofit.create(MovieApi::class.java)
    }
}