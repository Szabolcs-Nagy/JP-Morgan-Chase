package com.jpmc.szabolcs.viewmodel

import androidx.lifecycle.ViewModel
import com.jpmc.szabolcs.utils.OpenForTesting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jpmc.szabolcs.repository.AlbumRepository
import com.jpmc.szabolcs.utils.LoadingState
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * The album ViewModel class,
 * The data source this ViewModel will fetch results from
 * is the albumRepository variable of type AlbumRepository
 */
@OpenForTesting
class AlbumViewModel(val albumRepository: AlbumRepository): ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    /**
     * Creating the [viewModelJob] SupervisorJob that returns a CompletableJob
     * The SupervisorJob's children can fail without affecting the SupervisorJob
     */
    private val viewModelJob = SupervisorJob()

    /**
     * Now passing the SupervisorJob as parameter for the CoroutineScope
     */
    private val viewModelScope  = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * The [albumsResults] holds a LiveData list of the albums from the repository.
     * This list gets displayed on the screen.
     */
    val albumsResults = albumRepository.results

    /**
     * Calling the [refreshFromRepository] method,
     * that fetches the albums from the repository,
     * not directly from the network.
     */
    init {
        refreshFromRepository()
    }
    /**
     * The [refreshFromRepository] refreshes the data from
     * the repository and not from the network.
     * Using a coroutine launch to run in a background thread.
     */
    fun refreshFromRepository() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                albumRepository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (networkError: Exception){
                _loadingState.value = LoadingState.error(networkError.message)
            }
        }
    }

    /**
     * The [onCleared] method will be called when this
     * ViewModel is no longer used and will be destroyed.
     * It is useful when ViewModel observes some data and
     * you need to clear this subscription to prevent
     * a leak of this ViewModel.
     */
    override fun onCleared(){
        super.onCleared()
        viewModelScope.cancel()
    }
}