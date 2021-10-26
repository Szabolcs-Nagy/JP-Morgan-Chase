package com.jpmc.szabolcs.koin_injection

import com.jpmc.szabolcs.viewmodel.AlbumViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Creating a Koin module for the ViewModel
 * that creates an instance of [AlbumViewModel]
 */
val viewModelModule= module {
    viewModel { AlbumViewModel(get()) }
}