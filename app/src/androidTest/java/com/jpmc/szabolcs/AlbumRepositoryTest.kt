package com.jpmc.szabolcs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jpmc.szabolcs.model.DatabaseAlbum
import com.jpmc.szabolcs.network.Api
import com.jpmc.szabolcs.repository.AlbumRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AlbumRepositoryTest: DatabaseTest() {

    private lateinit var mRepo: AlbumRepository

    @Mock
    lateinit var apiService: Api

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun init() {
        super.init()
        MockitoAnnotations.initMocks(this)

        mRepo = AlbumRepository(
            apiService,
            database
        )
    }

//    @Test
//    fun test_album_repo_returns_expected_data() =
//        runBlocking {
//            val testToReturn =
//                CompletableDeferred(TestData.listTestAlbum)
//
//            Mockito.`when`(apiService.getAlbum())
//                .thenReturn(testToReturn)
//
//            val dataReceived = mRepo.refresh()
//            assertNotNull(dataReceived)
//        }

    @Test
    fun test_getItemsSearch_retrieved_expected_data() = runBlocking {
        // prep
        val testObjects = TestData.listTestAlbum
        database.albums.insertAll(testObjects)
        val liveData = LiveDataTestUtil<List<DatabaseAlbum>>()

        // action
        val dataReceived = mRepo.getAlbumSearch(testObjects[0].title)
        val dataReturned = liveData.getValue(dataReceived)?.get(0)

        // verify
        assertNotNull(dataReceived)
        assertNotNull(dataReturned)

        assertEquals(testObjects?.get(0).title, dataReturned?.title)
        assertEquals(testObjects?.get(0).id, dataReturned?.id)
        assertEquals(testObjects?.get(0).userId, dataReturned?.userId)
    }

    @Test
    fun test_list_is_empty_when_filter_found_nothing() {
        // prep
        val testObjects = TestData.listTestAlbum
        val liveDataUtils = LiveDataTestUtil<List<DatabaseAlbum>>()

        // action
        val dataReceived = mRepo.getAlbumSearch(testObjects[0].title)
        val dataReturned = liveDataUtils.getValue(dataReceived)

        // verify
        assertNotNull(dataReceived)
        assertNotNull(dataReturned)
        assertEquals(0, dataReturned?.size)
    }

    @Test
    fun test_getAlbumsByAppearance_return_expected_value() {
        // prep
        val testObject = TestData.listTestAlbum
        database.albums.insertAll(testObject)
        val liveDataUtils = LiveDataTestUtil<List<DatabaseAlbum>>()
        val userId = "1"

        // action
        val dataReceived = mRepo.getAlbumsBasedOnUserId(userId)
        val dataReturned = liveDataUtils.getValue(dataReceived)?.get(0)

        //verify
        assertNotNull(dataReceived)
        assertNotNull(dataReturned)

        assertEquals(testObject?.get(0)?.title, dataReturned?.title)
        assertEquals(testObject?.get(0)?.id, dataReturned?.id)
        assertEquals(testObject?.get(0)?.userId, dataReturned?.userId)
    }
}