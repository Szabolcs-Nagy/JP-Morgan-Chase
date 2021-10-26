package com.jpmc.szabolcs.sync_background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jpmc.szabolcs.repository.AlbumRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

/**
 * Creating the [BackgroundSyncWorker] coroutine ListenableWorker,
 * that provides interop with Kotlin Coroutines.
 */
class BackgroundSyncWorker (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params), KoinComponent
    {
        /**
         * Injecting the [AlbumRepository] to use it in the [doWork] function
         */
        private val aRepository : AlbumRepository by inject()

        /**
         * Overriding the [doWork] suspendable function
         * to call the refresh, retry and success methods
         * on the repository
         */
        override suspend fun doWork(): Result {
            try {
                aRepository.refresh()
            } catch (e: Exception){
                return Result.retry()
            }
            return Result.success()
        }
}