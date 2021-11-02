package com.jpmc.szabolcs.model

import androidx.room.*
import androidx.lifecycle.LiveData

/**
 * The album database access object interface
 * with two helper methods, the [getDBAlbums] gets
 * albums from the database and the [insertAll]
 * inserts albums into the database
 */

@Dao
interface AlbumDao {
    /**
     * Returns the albums from the database and orders the
     * results in ascending order. The return type is set to
     * Livedata so that the data displayed in the UI is
     * refreshed whenever the data in the database is changed
     */
    @Query("SELECT * FROM DatabaseAlbum ORDER BY title ASC")
    fun getDBAlbums(): LiveData<List<DatabaseAlbum>>

    /**
     * The insert method inserts a list of albums fetched from
     * the network into the database
     * I've set the conflict strategy to replace it overrides the
     * album entry is the album entry is already there
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(album: List<DatabaseAlbum>)

    /**
     * Dao query with filtering the search functionality based on title name
     */
    @Query("select * from DatabaseAlbum WHERE title LIKE :filter")
    fun getAlbumsSearch(filter: String): LiveData<List<DatabaseAlbum>>

 /**
     * Dao query with filtering the search functionality based on the userId
     */
    @Query("select * from DatabaseAlbum WHERE userId LIKE :filter")
    fun getAlbumsWithUserId(filter: String): LiveData<List<DatabaseAlbum>>
}

/**
 * Creating an abstract class called [AlbumDatabase]
 * which is a database for offline cache, this is
 * done by implementing [RoomDatabase]
 * using the @Database annotation to [AlbumDatabase]
 * as a Room database
 * Inside the class I defined a variable of type [AlbumDao]
 * to access the Dao methods
 */
@Database(entities=[DatabaseAlbum::class], version=1)
abstract class AlbumDatabase: RoomDatabase(){
    abstract val albums: AlbumDao
}