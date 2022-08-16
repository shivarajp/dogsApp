package com.shapegames.animals.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class DogsBreedModel(

    @ColumnInfo(name = "dog_url")
    var dogUrl: String,

    @ColumnInfo(name = "is_liked")
    var isLiked: Boolean,

)
