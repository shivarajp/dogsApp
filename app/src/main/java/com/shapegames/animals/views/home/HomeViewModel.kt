package com.shapegames.animals.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.local.DogsBreedModel
import com.shapegames.animals.data.remote.Resource
import com.shapegames.animals.data.repo.DogsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DogsRepositoryImpl) : ViewModel() {

    /**
     * Fetch dogs from API by breed & subBreed and save them in local db
     */
    fun fetchDogsBySubBreedFromApi(
        parentBreedId: Long,
        subBreedId: Long
    ): LiveData<Resource<String>> {
        return repository.getDogsBySubBreed(parentBreedId, subBreedId)
    }

    /**
     * Fetch dogs with breed name from db with breedId
     */
    fun observeLocalDogsAndBreedByBreedId(breedId: Long): LiveData<MutableList<DogsBreedModel>> {
        return repository.observeLocalDogsAndBreedByBreedId(breedId)
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
     * Update the like/unlike status for a dog
     */
    fun updateLikeStatus(dog: DogsBreedModel): LiveData<String> {
        return repository.updateLikeStatus(dog)
    }

    fun getAllLikedDogs(): LiveData<MutableList<DogsBreedModel>> {
        return repository.getAllLikedDogs()
    }

    fun getLikedDogsByBreedId(breedId: Long): LiveData<MutableList<DogsBreedModel>> {
        return repository.getLikedDogsByBreedId(breedId)
    }

}