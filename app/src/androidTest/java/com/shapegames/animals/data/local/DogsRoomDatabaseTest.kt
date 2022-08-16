package com.shapegames.animals.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
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
    fun test_getAllBreedNamesCount() = kotlinx.coroutines.test.runTest {
        val breed = Breeds(
            breedName = "australian",
            subBreedName = "shepherd",
            imgUrl = "https://github.com/"
        )
        val breed2 = Breeds(
            breedName = "australian",
            subBreedName = "shepherd",
            imgUrl = "https://github.com/"
        )
        dao.insetBreed(breed)
        dao.insetBreed(breed2)
        val count = dao.getAllBreedNamesCount()

        assertThat(count).isEqualTo(2)
    }


    @Test
    fun test_getAllBreedsAsLiveData() = kotlinx.coroutines.test.runTest {
        val breed = Breeds(
            breedId = 1223,
            breedName = "australian",
            subBreedName = "shepherd",
            imgUrl = "https://github.com/"
        )
        val breed2 = Breeds(
            breedId = 321,
            breedName = "australian",
            subBreedName = "shepherd",
            imgUrl = "https://github.com/2"
        )
        dao.insetBreed(breed)
        dao.insetBreed(breed2)

        val allBreeds = dao.getAllBreedsAsLiveData().getOrAwaitValue()
        assertThat(allBreeds).contains(breed)
        assertThat(allBreeds).contains(breed2)
    }

    @Test
    fun test_getAllLikedDogsFromDb() = kotlinx.coroutines.test.runTest {

        val dog = DogDetails(
            dogId = 2323,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedName = "australian",
            subBreed = "shepherd"
        )
        val dog2 = DogDetails(
            dogId = 4545,
            dogUrl = "https://github.com/2",
            isLiked = true,
            breedName = "australian",
            subBreed = "pub"
        )
        dao.insertLikedDog(dog)
        dao.insertLikedDog(dog2)

        val updatedDogs = dao.getAllLikedDogsFromDb().getOrAwaitValue()

        assertThat(updatedDogs[0].dogUrl).isEqualTo(dog.dogUrl)
        assertThat(updatedDogs[1].dogUrl).isEqualTo(dog2.dogUrl)
    }

    @Test
    fun test_getAllLikedDogsFromDbByBreeds() = kotlinx.coroutines.test.runTest {

        val dog = DogDetails(
            dogId = 2323,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedName = "australian",
            subBreed = "shepherd"
        )
        val dog2 = DogDetails(
            dogId = 4545,
            dogUrl = "https://github.com/2",
            isLiked = true,
            breedName = "australian",
            subBreed = "shepherd"
        )
        dao.insertLikedDog(dog)
        dao.insertLikedDog(dog2)

        val updatedDogs = dao.getAllDogsUrls("australian", "shepherd").getOrAwaitValue()

        assertThat(updatedDogs[0]).isEqualTo(dog.dogUrl)
        assertThat(updatedDogs[1]).isEqualTo(dog2.dogUrl)
    }

    @Test
    fun test_delete_dog() = kotlinx.coroutines.test.runTest {

        val dog = DogDetails(
            dogId = 2323,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedName = "australian",
            subBreed = "shepherd"
        )

        dao.insertLikedDog(dog)

        val updatedDogs = dao.getAllDogsUrls("australian", "shepherd").getOrAwaitValue()
        assertThat(updatedDogs.size).isGreaterThan(0)

        dao.deleteDog("australian",
            "shepherd", "https://github.com/")
        val updatedDogs2 = dao.getAllDogsUrls("australian", "shepherd").getOrAwaitValue()

        assertThat(updatedDogs2.size).isEqualTo(0)
    }

}