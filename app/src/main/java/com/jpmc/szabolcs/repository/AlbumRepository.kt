package com.jpmc.szabolcs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.jpmc.szabolcs.model.AlbumDatabase
import com.jpmc.szabolcs.model.DatabaseAlbum
import com.jpmc.szabolcs.model.asDomainModel
import com.jpmc.szabolcs.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The repository class takes in two objects as constructor.
 * The api to get live data when online;
 * And a database object to access the Dao methods when offline.
 */
class AlbumRepository(private val Api: Api, private val database: AlbumDatabase) {
    /**
     * Adding the [refresh] method to refresh the videos stored in the offline cache.
     * This function has no arguments and returns nothing.
     * This method will be the API used to refresh the offline cache.
     * This is a suspend function it performs a database operation,
     * therefore it must be called from a coroutine.
     */
    suspend fun refresh(){
        /**
         * This function performs API request and save data locally.
         * It uses the IO dispatcher to ensure the database insert database operation
         * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
         * function is now safe to call from any thread including the Main thread.
         */
        withContext(Dispatchers.IO){
            val albumList = Api.getAlbum().await()
            database.albums.insertAll(albumList)
        }
    }

    /**
     * Using [Transformations.map] to convert the list of database objects
     * to a list of domain objects. Using the [asDomainModel] conversion function.
     */
    val results: LiveData<List<DatabaseAlbum>> = Transformations.map(database.albums.getDBAlbums()){
        it.asDomainModel()
    }
    /**
     * Function for search based on title
     */
    fun getAlbumSearch(filter: String): LiveData<List<DatabaseAlbum>> {
        return database.albums.getAlbumsSearch(filter)
    }
    /**
     * Function for filtering based on userId
     */
    fun getAlbumsBasedOnUserId(filter: String): LiveData<List<DatabaseAlbum>> {
        return database.albums.getAlbumsWithUserId(filter)
    }
}