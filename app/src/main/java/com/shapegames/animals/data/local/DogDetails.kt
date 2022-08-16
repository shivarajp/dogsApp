package com.shapegames.animals.data.local

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "dog_details", indices = [Index(value = ["dog_url"], unique = true)]
)
class DogDetails(
    @ColumnInfo(name = "dog_id")
    @PrimaryKey(autoGenerate = true)
    var dogId: Long = 0,

    @ColumnInfo(name = "dog_url")
    var dogUrl: String,

    @ColumnInfo(name = "breed_name")
    var breedName: String,

    @ColumnInfo(name = "sub_breed")
    var subBreed: String,

    @ColumnInfo(name = "is_liked")
    var isLiked: Boolean,
)