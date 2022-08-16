package com.shapegames.animals.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.local.DogsDao
import com.shapegames.animals.data.models.AllBreedsNameListResponsModel
import com.shapegames.animals.data.models.DogsByBreedResponseModel
import com.shapegames.animals.data.remote.DogsAPI
import com.shapegames.animals.data.remote.Resource
import com.shapegames.animals.data.remote.ResponseHandler
import com.shapegames.animals.utils.Util.IMG_COUNT
import com.shapegames.animals.utils.Util.dogsBreedsHashMap
import com.shapegames.animals.utils.Util.imageUrls
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DogsRepositoryImpl
@Inject constructor(
    private val api: DogsAPI,
    private val dao: DogsDao,
    private val responseHandler: ResponseHandler
) {

    /**
     * Fetch dogs list form API both parent and sub breed
     *
     */
    suspend fun getDogsBySubBreed(
        parentBreedName: String,
        subBreedName: String
    ): Resource<DogsByBreedResponseModel> {

        try {
            if (subBreedName.isNotEmpty()) {
                //get sub breed dogs
                val res = api.getDogsBySubBreedFromApi(
                    parentBreedName,
                    subBreedName
                )
                return responseHandler.handleSuccess(res)

            } else {
                //get parent breed only dogs
                val res = api.getDogsByBreedFromApi(
                    parentBreedName,
                    IMG_COUNT
                )
                return responseHandler.handleSuccess(res)
            }
        } catch (e: Exception) {
            return responseHandler.handleException(e)
        }
    }


    /**
     * Checks if the locally available breed list is saved in db
     * if not then it stores it in Breeds table
     */
    fun saveLocalBreedsInDB(): LiveData<Resource<String>> {

        return liveData(Dispatchers.IO) {
            try {
                val rand = Random()
                emit(Resource.loading(null))

                if (dao.getAllBreedNamesCount() <= 0) {
                    dogsBreedsHashMap.keys.forEachIndexed { index, key ->
                        saveBreedWithImg(key, imageUrls[rand.nextInt(10)])
                        emit(responseHandler.handleSuccess("$index"))
                    }
                    emit(responseHandler.handleSuccess("success"))
                } else {
                    emit(responseHandler.handleSuccess("success"))
                }
            } catch (e: Exception) {
                emit(responseHandler.handleException(e))
                Log.d("error", e.message.toString())
            }
        }
    }

    /**
     * Go through local breed map and stores it in db with a dog image
     */
    private fun saveBreedWithImg(breedName: String, img_url: String) {

        //save both breed and sub breed
        if (!dogsBreedsHashMap[breedName].isNullOrEmpty()) {
            dogsBreedsHashMap[breedName]?.forEach {
                dao.insetBreed(
                    Breeds(
                        breedName = breedName,
                        subBreedName = it,
                        imgUrl = img_url
                    )
                )
            }
        } else {
            //Save parent breed
            dao.insetBreed(
                Breeds(
                    breedName = breedName,
                    subBreedName = "",
                    imgUrl = img_url
                )
            )
        }
    }


    /**
     * Fetch all breeds from local db Breeds entity
     */
    fun getAllBreedsFromLocalDb(): LiveData<MutableList<Breeds>> {
        return dao.getAllBreedsAsLiveData()
    }

    /**
     * Insert liked dog in db
     */
    fun insertLikedDog(
        dogDetail: DogDetails
    ): LiveData<String> {
        return liveData(Dispatchers.IO) {
            try {
                val id = dao.insertLikedDog(dogDetail)
                if (id > 0L) {
                    emit("Updated")
                } else {
                    emit("Update failed")
                }
            } catch (e: Exception) {
                emit("Update failed")
            }
        }
    }

    /**
     * Fetch all liked dogs from db
     */
    fun getAllLikedDogs(): LiveData<MutableList<DogDetails>> {
        return dao.getAllLikedDogsFromDb()
    }

    /**
     * Fetch liked dogs of a specific breed
     */
    fun getLikedDogsByBreedNameAndSubBreed(
        breedName: String,
        subBreedName: String
    ): LiveData<MutableList<DogDetails>> {
        return dao.getLikedDogsByBreedNameAndSubBreed(breedName, subBreedName)
    }


    /**
     * Gets all breeds from api
     */
    suspend fun getAllBreedsFromApi(): Resource<AllBreedsNameListResponsModel> {
        return try {
            responseHandler.handleSuccess(api.getAllBreedNamesFromApi())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    /**
     * Fetch liked dogs of a specific breed
     */
    fun getAllDogsUrls(
        parentBreedName: String,
        subBreedName: String
    ): LiveData<MutableList<String>> {
        return dao.getAllDogsUrls(parentBreedName, subBreedName)
    }

    /**
     * Delete a dog from table
     */
    fun deleteDog(parentBreedName: String, subBreedName: String, dogUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteDog(parentBreedName, subBreedName, dogUrl)
        }
    }


}