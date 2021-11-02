package com.jpmc.szabolcs

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jpmc.szabolcs.model.AlbumDao
import com.jpmc.szabolcs.model.AlbumDatabase
import org.junit.After
import org.junit.Before
import java.lang.Exception

open class DatabaseTest {

    protected lateinit var database: AlbumDatabase
    val albumDao: AlbumDao
        get() = database.albums

    @Before
    open fun init() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun finish() {
        try {
            database.close()
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}