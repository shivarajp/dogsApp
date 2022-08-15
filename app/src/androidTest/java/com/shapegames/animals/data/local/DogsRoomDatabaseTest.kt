package com.shapegames.animals.data.local

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DogsRoomDatabaseTest : TestCase() {

    lateinit var database: DogsRoomDatabase
    private lateinit var dao: DogsDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), DogsRoomDatabase::class.java
        ).build()
        dao = database.getDao()
    }


    @After
    public override fun tearDown() {
        database.close()
    }

    @Test
    fun test_insertBreedsTest() = kotlinx.coroutines.test.runTest {

        val breed = Breeds(
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        dao.insetBreed(breed)
        val name = dao.getBreedNameById(1)
        assertThat(name).isEqualTo("pug")
    }

    @Test
    fun test_getAllBreedNamesCount() = kotlinx.coroutines.test.runTest {
        val breed = Breeds(
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        val breed2 = Breeds(
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        dao.insetBreed(breed)
        dao.insetBreed(breed2)
        val count = dao.getAllBreedNamesCount()

        assertThat(count).isEqualTo(2)
    }

    @Test
    fun test_getBreedIdByName() = kotlinx.coroutines.test.runTest {
        val breed = Breeds(
            breedId = 122,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        dao.insetBreed(breed)
        val id = dao.getBreedIdByName("pug")
        assertThat(id).isEqualTo(122)
    }

    @Test
    fun test_insertSingleDogByBreed() = kotlinx.coroutines.test.runTest {
        val breed = Breeds(
            breedId = 122,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        val breedId = dao.insetBreed(breed)

        val dog = DogDetails(
            dogId = 111,
            dogUrl = "https://github.com/",
            isLiked = false,
            breedId = breedId
        )

        dao.insertSingleDogByBreed(dog)

        val res = DogsBreedModel(
            dogId = 111,
            dogUrl = "https://github.com/",
            isLiked = false,
            breedId = breed.breedId,
            breedName = "pug"
        )

        val dogs = dao.getAllDogsWithBreedByBreedId(breedId).getOrAwaitValue()
        assertThat(dogs).contains(res)
    }


    @Test
    fun test_getAllBreedsAsLiveData() = kotlinx.coroutines.test.runTest {
        val breed = Breeds(
            breedId = 1223,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        val breed2 = Breeds(
            breedId = 321,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        dao.insetBreed(breed)
        dao.insetBreed(breed2)

        val allBreeds = dao.getAllBreedsAsLiveData().getOrAwaitValue()
        assertThat(allBreeds).contains(breed)
        assertThat(allBreeds).contains(breed2)
    }

    @Test
    fun test_getBreedNameById() = kotlinx.coroutines.test.runTest {
        val breed2 = Breeds(
            breedId = 321,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        dao.insetBreed(breed2)


        val name = dao.getBreedNameById(321)
        assertThat(breed2.breedName).isEqualTo(name)
    }

    @Test
    fun test_updateLikeStatus() = kotlinx.coroutines.test.runTest {
        val breed2 = Breeds(
            breedId = 321,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        val breedId = dao.insetBreed(breed2)

        val dog = DogDetails(
            dogId = 111,
            dogUrl = "https://github.com/",
            isLiked = false,
            breedId = breedId
        )
        dao.insertSingleDogByBreed(dog)

        dog.isLiked = true

        dao.updateLikeStatus(dog)

        val dogBreedModel =
            DogsBreedModel(dog.dogId, dog.dogUrl, dog.breedId, dog.isLiked, breed2.breedName)

        val updatedDogs = dao.getLikedDogsByBreedId(dog.breedId).getOrAwaitValue()

        assertThat(updatedDogs).contains(dogBreedModel)

    }

    @Test
    fun test_getAllLikedDogsFromDb() = kotlinx.coroutines.test.runTest {
        val breed2 = Breeds(
            breedId = 321,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        val breedId = dao.insetBreed(breed2)

        val dog = DogDetails(
            dogId = 111,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedId = breedId
        )
        val dog2 = DogDetails(
            dogId = 112,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedId = breedId
        )
        dao.insertSingleDogByBreed(dog)
        dao.insertSingleDogByBreed(dog2)

        val dogBreedModel = DogsBreedModel(dog.dogId, dog.dogUrl, dog.breedId, dog.isLiked, breed2.breedName)
        val dogBreedModel2 = DogsBreedModel(dog2.dogId, dog2.dogUrl, dog2.breedId, dog2.isLiked, breed2.breedName)

        val updatedDogs = dao.getAllLikedDogsFromDb().getOrAwaitValue()

        assertThat(updatedDogs).contains(dogBreedModel)
        assertThat(updatedDogs).contains(dogBreedModel2)

    }

    @Test
    fun test_getLikedDogsByBreedId() = kotlinx.coroutines.test.runTest {
        val breed2 = Breeds(
            breedId = 321,
            breedName = "pug",
            parentId = 0,
            imgUrl = "https://github.com/"
        )
        val breedId = dao.insetBreed(breed2)

        val dog = DogDetails(
            dogId = 111,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedId = breedId
        )
        val dog2 = DogDetails(
            dogId = 112,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedId = breedId
        )
        dao.insertSingleDogByBreed(dog)
        dao.insertSingleDogByBreed(dog2)

        val dogBreedModel = DogsBreedModel(dog.dogId, dog.dogUrl, dog.breedId, dog.isLiked, breed2.breedName)
        val dogBreedModel2 = DogsBreedModel(dog2.dogId, dog2.dogUrl, dog2.breedId, dog2.isLiked, breed2.breedName)

        val updatedDogs = dao.getLikedDogsByBreedId(breedId).getOrAwaitValue()

        assertThat(updatedDogs).contains(dogBreedModel)
        assertThat(updatedDogs).contains(dogBreedModel2)

    }
}