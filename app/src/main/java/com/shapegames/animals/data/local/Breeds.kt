package com.shapegames.animals.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class Breeds(
    @ColumnInfo(name = "breed_id")
    @PrimaryKey(autoGenerate = true)
    var breedId: Long = 0,

    @ColumnInfo(name = "breed_name")
    var breedName: String,

    @ColumnInfo(name = "parent_id")
    var parentId: Long,

    @ColumnInfo(name = "img_url")
    var imgUrl: String
) {


}