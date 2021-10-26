package com.jpmc.szabolcs.model

import androidx.room.PrimaryKey
import androidx.room.Entity

/**
 * The DatabaseAlbum table
 * Having the id as primary key
 */
@Entity
data class DatabaseAlbum (
    @PrimaryKey
    var id: Int,
    var userId: Int,
    var title: String
)

/**
 * The following mapper adds an offline cache
 * The [asDomainModel] converts the [DatabaseAlbum] database objects
 * into domain model objects
 */
fun List<DatabaseAlbum>.asDomainModel():List<DatabaseAlbum> {
    return map {
        DatabaseAlbum(
            id = it.id,
            userId = it.userId,
            title = it.title
        )
    }
}