package com.example.searchapplication.di

import android.util.Log
import com.example.searchapplication.data.apiservice.CharacterSearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("API_LOG", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }
    @Provides
    @Singleton
    fun getOkHttpObject(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient = OkHttpClient.Builder().addInterceptor(
        loggingInterceptor).build()

    @Provides
    @Singleton
    fun getRetrofitObject(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/").client(okHttpClient).addConverterFactory(
        GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun getSearchCharApiService(retrofit: Retrofit) : CharacterSearchApiService =  retrofit.create(CharacterSearchApiService::class.java)
}