package com.jpmc.szabolcs

import android.app.Application
import com.jpmc.szabolcs.koin_injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AlbumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        /**
         * Starting Koin with the following modules:
         * viewModelModule, repositoryModule, netModule,
         * apiModule, databaseModule
         */
        startKoin {
            /**
             * The [androidContext] function gets the
             * Context instance of the Koin modules
             * defined in [AlbumApp]
             */
            androidContext(this@AlbumApp)
            androidLogger(Level.NONE)
            modules(listOf(viewModelModule, repositoryModule, netModule, apiModule, databaseModule))
        }
    }
}