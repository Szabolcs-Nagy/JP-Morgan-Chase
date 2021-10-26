package com.jpmc.szabolcs.koin_injection

import com.jpmc.szabolcs.network.Api
import org.koin.dsl.module
import retrofit2.Retrofit
/**
 * Creating a Koin module for the Api
 * that creates an singleton instance
 * of the [Api] class
 */
val apiModule = module {
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    single { provideApi(get()) }
}