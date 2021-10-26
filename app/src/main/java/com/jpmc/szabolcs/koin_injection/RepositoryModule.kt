package com.jpmc.szabolcs.koin_injection


import com.jpmc.szabolcs.model.AlbumDatabase
import com.jpmc.szabolcs.network.Api
import com.jpmc.szabolcs.repository.AlbumRepository
import org.koin.dsl.module
/**
 * Creating a Koin module for the repository
 * that creates an singleton instance of [AlbumRepository]
 * Taking in as parameters the Api and the Local Database
 */
val repositoryModule = module {
    fun provideRepository(api: Api, dao: AlbumDatabase): AlbumRepository {
        return AlbumRepository(api, dao)
    }

    single {
        provideRepository(get(), get())
    }
}