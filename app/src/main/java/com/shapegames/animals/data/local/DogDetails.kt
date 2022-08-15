package com.shapegames.animals.data.local

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "dog_details",
    foreignKeys = [ForeignKey(
        entity = Breeds::class,
        parentColumns = arrayOf("breed_id"),
        childColumns = arrayOf("breedid"),
        onDelete = CASCADE
    )],
    indices = arrayOf(Index(value = arrayOf("breedid")))
)
class DogDetails(
    @ColumnInfo(name = "dog_id")
    @PrimaryKey(autoGenerate = true)
    var dogId: Long = 0,

    @ColumnInfo(name = "dog_url")
    var dogUrl: String,

    @ColumnInfo(name = "breedid")
    var breedId: Long,

    @ColumnInfo(name = "is_liked")
    var isLiked: Boolean,
)