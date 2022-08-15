package com.shapegames.animals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Breeds::class, DogDetails::class], version = 1)
abstract class DogsRoomDatabase : RoomDatabase(){

    abstract fun getDao(): DogsDao

}