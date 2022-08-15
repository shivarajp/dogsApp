package com.shapegames.animals.di

import android.content.Context
import androidx.room.Room
import com.shapegames.animals.data.local.DogsDao
import com.shapegames.animals.data.local.DogsRoomDatabase
import com.shapegames.animals.data.remote.DogsAPI
import com.shapegames.animals.data.remote.ResponseHandler
import com.shapegames.animals.data.repo.DogsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogsModule {

    @Provides
    @Singleton
    fun provideDogsApi(): DogsAPI {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDogsRepository(api: DogsAPI, dao:DogsDao, responseHandler: ResponseHandler): DogsRepositoryImpl{
        return DogsRepositoryImpl(api, dao, responseHandler)
    }

    @Provides
    @Singleton
    fun provideResponseHandler(): ResponseHandler{
        return ResponseHandler()
    }

    @Provides
    @Singleton
    fun provideDogsRoomDao(@ApplicationContext context: Context): DogsDao{
        return Room.databaseBuilder(
            context.applicationContext,
            DogsRoomDatabase::class.java,
            "dogs_bd"
        ).build().getDao()
    }


}