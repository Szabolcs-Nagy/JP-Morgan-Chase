package com.jpmc.szabolcs.network

import com.jpmc.szabolcs.model.DatabaseAlbum
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Creating the Api interface that gets the list of
 * albums from the url path.
 * The [getAlbum] function returns a Deferrable List
 * meaning that it's created with an async coroutine builder,
 * it's a job with a result and it's cancellable.
 * Returning a list of [DatabaseAlbum]
 */
interface Api {
    @GET(Constants.URL_PATH)
    fun getAlbum(): Deferred<List<DatabaseAlbum>>
}