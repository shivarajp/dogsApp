package com.shapegames.animals.views.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.shapegames.animals.MainCoroutineRule
import com.shapegames.animals.data.models.DogsByBreedResponseModel
import com.shapegames.animals.data.remote.Resource
import com.shapegames.animals.data.remote.ResponseHandler
import com.shapegames.animals.data.remote.Status
import com.shapegames.animals.data.repo.DogsRepositoryImpl
import com.shapegames.animals.getOrAwaitValue
import com.shapegames.animals.observeForTesting
import com.shapegames.animals.utils.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
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
    private lateinit var testDispatcher: TestCoroutineDispatcher

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        responseHandler = ResponseHandler()
        viewModel = HomeViewModel(repository)
        testDispatcher = TestCoroutineDispatcher()

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

        viewModel.currentWeather.observeForTesting {

            val it = viewModel.currentWeather.value
            when (viewModel.currentWeather.value?.status) {
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
    fun saveLocalBreedsListInDb() {
        /*runTest {
            Mockito.`when`(
                repository.saveLocalBreedsInDB()
            ).thenReturn(responseHandler.handleSuccess(mockedDogsResponse))
        }*/

    }

    @Test
    fun getAllBreedsFromLocal() {
    }

    @Test
    fun insertLikedDog() {
    }

    @Test
    fun getAllLikedDogs() {
    }

    @Test
    fun getLikedDogsByBreedNameAndSubBreed() {
    }

    @Test
    fun getAllDogsUrls() {
    }

    @Test
    fun deleteDog() {
    }

    @Test
    fun getBreedsFromApi() {
    }
}