package com.jpmc.szabolcs.koin_injection


import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jpmc.szabolcs.network.Constants
import com.jpmc.szabolcs.repository.AlbumRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Creating a Koin module for the network
 * that creates an singleton instance of
 * [Cache], [OkHttpClient], [Gson] and [Retrofit]
 */
val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @SuppressLint("LogNotTimber")
    fun provideHttpClient(cache: Cache): OkHttpClient {
        val logging =
            HttpLoggingInterceptor(Logger { message ->
                Log.i(
                    "Details",
                    "" + message
                )
            }).setLevel(Level.BODY)


        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .cache(cache)


        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get()) }

}
