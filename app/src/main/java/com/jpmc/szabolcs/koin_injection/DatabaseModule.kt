package com.jpmc.szabolcs.koin_injection

import android.app.Application
import androidx.room.Room
import com.jpmc.szabolcs.model.AlbumDatabase

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
/**
 * Creating a Koin module for the Local Database
 * that creates an singleton instance of [AlbumDatabase]
 */
val databaseModule = module {
    fun providesDatabase(application: Application): AlbumDatabase {
        return Room.databaseBuilder(application, AlbumDatabase::class.java, "albums.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { providesDatabase(androidApplication()) }
}