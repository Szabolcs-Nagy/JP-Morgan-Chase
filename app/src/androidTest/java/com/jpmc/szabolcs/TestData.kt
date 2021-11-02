package com.jpmc.szabolcs

import com.jpmc.szabolcs.model.DatabaseAlbum

object TestData {
    val testAlbum = DatabaseAlbum(id = 1, userId = 1, title = "Title one")
    val listTestAlbum = listOf(testAlbum)
}
