package com.jpmc.szabolcs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jpmc.szabolcs.model.DatabaseAlbum
import com.jpmc.szabolcs.network.Api
import com.jpmc.szabolcs.repository.AlbumRepository
import com.jpmc.szabolcs.viewmodel.AlbumViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SingleNetworkCallViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: Api

    @Mock
    private lateinit var aRepository: AlbumRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<List<DatabaseAlbum>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<DatabaseAlbum>())
                .`when`(apiHelper)
                .getAlbum()
            val viewModel = AlbumViewModel(aRepository)
            verify(apiHelper).getAlbum()
            viewModel.albumsResults.removeObserver(apiUsersObserver)
        }
    }
    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getAlbum()
            val viewModel = AlbumViewModel(aRepository)
            viewModel.albumsResults.observeForever(apiUsersObserver)
            verify(apiHelper).getAlbum()
            viewModel.albumsResults.removeObserver(apiUsersObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

}