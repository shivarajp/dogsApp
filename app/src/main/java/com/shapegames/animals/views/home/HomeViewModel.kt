package com.shapegames.animals.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.models.AllBreedsNameListResponsModel
import com.shapegames.animals.data.models.DogsByBreedResponseModel
import com.shapegames.animals.data.remote.Resource
import com.shapegames.animals.data.repo.DogsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DogsRepositoryImpl) : ViewModel() {


    val observeDogsApi = MutableLiveData<Resource<DogsByBreedResponseModel>>()

    /**
     * Fetch dogs from API by breed & subBreed and save them in local db
     */
    fun fetchDogsBySubBreedFromApi(
        parentBreedId: String,
        subBreedId: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            observeDogsApi.postValue((Resource.loading(null)))
            observeDogsApi.postValue(repository.getDogsBySubBreed(parentBreedId, subBreedId))
        }
    }


    /**
     * Save locally available breed names in db
     */
    fun saveLocalBreedsListInDb(): LiveData<Resource<String>> {
        return repository.saveLocalBreedsInDB()
    }


    /**
     * Fetch breed list from db and returns a livedata of MutableList<Breeds>
     */
    fun getAllBreedsFromLocal(): LiveData<MutableList<Breeds>> {
        return repository.getAllBreedsFromLocalDb()
    }


    /**
     * Insert the liked dog into local db
     */
    fun insertLikedDog(
        dogDetail: DogDetails
    ): LiveData<String> {
        return repository.insertLikedDog(dogDetail)
    }


    /**
     * Fetches all breed dogs form the [dog_details] table
     */
    fun getAllLikedDogs(): LiveData<MutableList<DogDetails>> {
        return repository.getAllLikedDogs()
    }


    /**
     * Fetches dogs for specific breed/subreed the [dog_details] table
     */
    fun getLikedDogsByBreedNameAndSubBreed(
        breedName: String,
        subBreedName: String
    ): LiveData<MutableList<DogDetails>> {
        return repository.getLikedDogsByBreedNameAndSubBreed(breedName, subBreedName)
    }


    /**
     * Fetches all breed dogs image urls from [dog_details] table
     */
    fun getAllDogsUrls(
        parentBreedName: String,
        subBreedName: String
    ): LiveData<MutableList<String>> {
        return repository.getAllDogsUrls(parentBreedName, subBreedName)
    }

    /**
     * Deletes a dog from the table based on breed and url
     * because we are not having these ids for remote dogs
     */
    fun deleteDog(parentBreedName: String, subBreedName: String, dogUrl: String) {
        repository.deleteDog(parentBreedName, subBreedName, dogUrl)
    }

    /**
     * Fetches breeds from the api
     */
    fun getBreedsFromApi(): LiveData<Resource<AllBreedsNameListResponsModel>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(repository.getAllBreedsFromApi())
        }
    }

}