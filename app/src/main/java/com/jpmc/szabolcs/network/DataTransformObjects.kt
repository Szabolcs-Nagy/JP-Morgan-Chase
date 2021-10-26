package com.jpmc.szabolcs.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.jpmc.szabolcs.model.DatabaseAlbum
import com.jpmc.szabolcs.model.asDomainModel

/**
 * [NetworkAlbumContainer] is a data class annotated with
 * @JsonClass(generateAdapter = true).
 * This means that this class will generate a JsonAdapter
 * to handle serializing/deserializing
 * to and from JSON of the specified type.
 */
@JsonClass(generateAdapter = true)
data class NetworkAlbumContainer(val value: List<ValueItem>)

data class ValueItem(
    /**
     * The @Json(name=...) annotation defines the JSON key name
     * for serialisation and the property to set the value on
     * with deserialization.
     */
    @Json(name="id") val id: Int,
    @Json(name="userId") val userId: Int,
    @Json(name="title") val title: String
)
/**
 * The following mapper adds an offline cache
 * The [asDomainModel] converts the [DatabaseAlbum] database objects
 * into domain model objects
 */
fun NetworkAlbumContainer.asDatabaseModel() : List<DatabaseAlbum> {
    return value.map {
        DatabaseAlbum(
            id = it.id,
            userId = it.userId,
            title = it.title
        )
    }
}
