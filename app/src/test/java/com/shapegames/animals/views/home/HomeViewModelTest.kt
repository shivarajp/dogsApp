package com.shapegames.animals.views.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth
import com.shapegames.animals.MainCoroutineRule
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.models.DogsByBreedResponseModel
import com.shapegames.animals.data.remote.ResponseHandler
import com.shapegames.animals.data.remote.Status
import com.shapegames.animals.data.repo.DogsRepositoryImpl
import com.shapegames.animals.getOrAwaitValue
import com.shapegames.animals.observeForTesting
import com.shapegames.animals.views.home.DataManager.getBreedsList
import com.shapegames.animals.views.home.DataManager.getDogDetailsObjectList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner


@LargeTest
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var repository: DogsRepositoryImpl

    @Mock
    private lateinit var mockedDogsResponse: DogsByBreedResponseModel

    private lateinit var viewModel: HomeViewModel
    private lateinit var responseHandler: ResponseHandler


    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        responseHandler = ResponseHandler()
        viewModel = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun fetchDogsBySubBreedFromApi() = runTest {

        Mockito.`when`(
            repository.getDogsBySubBreed(
                "australian", "shepherd"
            )
        ).thenReturn(responseHandler.handleSuccess(mockedDogsResponse))

        viewModel.fetchDogsBySubBreedFromApi(
            "australian",
            "shepherd"
        )

        viewModel.observeDogsApi.observeForTesting {

            val it = viewModel.observeDogsApi.value
            when (viewModel.observeDogsApi.value?.status) {
                Status.ERROR -> {
                    assert(it?.message != null)
                }
                Status.SUCCESS -> {
                    assert(it?.data != null)
                }
                Status.LOADING -> {
                    assert(it?.data == null)
                }
            }
        }
    }


    @Test
    fun getAllBreedsFromLocal() {
        val list = MutableLiveData<MutableList<Breeds>>()

        runTest {
            Mockito.`when`(
                repository.getAllBreedsFromLocalDb()
            ).thenReturn(list)
        }
        list.value = getBreedsList()

        val data = viewModel.getAllBreedsFromLocal().getOrAwaitValue()

        Mockito.verify(repository, times(1)).getAllBreedsFromLocalDb()

        Truth.assertThat(data.size).isEqualTo(2)
    }



    @Test
    fun insertLikedDog() {
        val result = MutableLiveData<String>()
        val dogs = getDogDetailsObjectList()

        runTest {
            Mockito.`when`(
                repository.insertLikedDog(dogs[0])
            ).thenReturn(result)
        }
        result.value = "updated"

        val data = viewModel.insertLikedDog(dogs[0]).getOrAwaitValue()

        Mockito.verify(repository, times(1)).insertLikedDog(dogs[0])

        Truth.assertThat(data).isEqualTo("updated")
    }

    @Test
    fun getAllLikedDogs() {

        val result = MutableLiveData<MutableList<DogDetails>>()

        runTest {
            Mockito.`when`(
                repository.getAllLikedDogs()
            ).thenReturn(result)
        }
        result.value = getDogDetailsObjectList()

        val data = viewModel.getAllLikedDogs().getOrAwaitValue()

        Mockito.verify(repository, times(1)).getAllLikedDogs()

        Truth.assertThat(data.size).isEqualTo(2)
    }

    @Test
    fun getLikedDogsByBreedNameAndSubBreed() {

        val result = MutableLiveData<MutableList<DogDetails>>()

        val breedName = "australian"
        val subBreedName = "shepherd"

        runTest {
            Mockito.`when`(
                repository.getLikedDogsByBreedNameAndSubBreed(
                    breedName,
                    subBreedName
                )
            ).thenReturn(result)
        }
        result.value = getDogDetailsObjectList()

        val data =
            viewModel.getLikedDogsByBreedNameAndSubBreed(breedName, subBreedName).getOrAwaitValue()

        Mockito.verify(repository, times(1))
            .getLikedDogsByBreedNameAndSubBreed(
                breedName,
                subBreedName
            )

        Truth.assertThat(data.size).isEqualTo(2)
    }

    @Test
    fun getAllDogsUrls() {
        val result = MutableLiveData<MutableList<String>>()
        val list = mutableListOf<String>()
        val breedName = "australian"
        val subBreedName = "shepherd"

        list.add("https://github.com/")
        list.add("https://github.com/2")

        runTest {
            Mockito.`when`(
                repository.getAllDogsUrls(
                    breedName,
                    subBreedName
                )
            ).thenReturn(result)
        }
        result.value = list

        val data =
            viewModel.getAllDogsUrls(breedName, subBreedName).getOrAwaitValue()

        Mockito.verify(repository, times(1))
            .getAllDogsUrls(
                breedName,
                subBreedName
            )

        Truth.assertThat(data.size).isEqualTo(2)
    }

    @Test
    fun deleteDog() {

        val breedName = "australian"
        val subBreedName = "shepherd"
        val url = "https://github.com/"


        viewModel.deleteDog(breedName, subBreedName, url)
        Mockito.verify(repository, times(1))
            .deleteDog(breedName, subBreedName, url)

    }

}