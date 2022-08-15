package com.shapegames.animals.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class DogsBreedModel(

    @ColumnInfo(name = "dog_id")
    var dogId: Long = 0,

    @ColumnInfo(name = "dog_url")
    var dogUrl: String,

    @ColumnInfo(name = "breedid")
    var breedId: Long,

    @ColumnInfo(name = "is_liked")
    var isLiked: Boolean,

    @ColumnInfo(name = "breed_name")
    var breedName: String
)
