package com.shapegames.animals.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.local.DogsBreedModel
import com.shapegames.animals.data.local.DogsDao
import com.shapegames.animals.data.remote.DogsAPI
import com.shapegames.animals.data.remote.Resource
import com.shapegames.animals.data.remote.ResponseHandler
import com.shapegames.animals.utils.Util
import com.shapegames.animals.utils.Util.IMG_COUNT
import com.shapegames.animals.utils.Util.UNLIKED
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
    fun getDogsBySubBreed(
        parentBreedId: Long,
        subBreedId: Long
    ): LiveData<Resource<String>> {

        return liveData(Dispatchers.IO) {
            try {
                emit(Resource.loading(null))
                //Check if these images are already in db
                if (parentBreedId == 0L) {

                    //No parent breed so Fetch dogs with the sub breed
                    if (dao.isThisBreedImagesAreInDB(subBreedId) <= 0) {
                        val breedName = dao.getBreedNameById(subBreedId)

                        val res = api.getDogsByBreedFromApi(breedName, IMG_COUNT)
                        when (res.status) {
                            "success" -> {
                                saveDogsByBreedInDB(subBreedId, res.message)
                                emit(responseHandler.handleSuccess("success"))
                            }

                            "error" -> {
                                emit(responseHandler.handleSuccess("error"))
                            }
                        }
                    } else {
                        emit(responseHandler.handleSuccess("success"))
                    }

                } else if (dao.isThisBreedImagesAreInDB(subBreedId) <= 0) {

                    /**
                     * It has a parent breed and subBreedId both
                     * In API call use both parent and subBreed
                     */
                    val res = api.getDogsBySubBreedFromApi(
                        dao.getBreedNameById(parentBreedId),
                        dao.getBreedNameById(subBreedId)
                    )
                    when (res.status) {
                        "success" -> {
                            saveDogsByBreedInDB(subBreedId, res.message)
                            emit(responseHandler.handleSuccess("success"))
                        }

                        "error" -> {
                            emit(responseHandler.handleSuccess("error"))
                        }
                    }
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
     * Using breedId fetch Dog details & breed name from db
     */
    fun observeLocalDogsAndBreedByBreedId(breedId: Long): LiveData<MutableList<DogsBreedModel>> {
        return dao.getAllDogsWithBreedByBreedId(breedId)
    }

    /**
     * Insert dogs data in local Dog db
     */
    private fun saveDogsByBreedInDB(breedId: Long, dogImages: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                for (index in dogImages.indices) {
                    val dogDetailsModel =
                        DogDetails(dogUrl = dogImages[index], breedId = breedId, isLiked = UNLIKED)
                    dao.insertSingleDogByBreed(dogDetailsModel)
                }
            } catch (exception: Exception) {
                Log.d("error", exception.message.toString())
            }
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

                        /**
                         * We can fetch a random image for each breed
                         * to load in the breed list for better user experience.
                         * As of now keeping it simple with static image.
                         */

                        /*val res = api.getDogsByBreedFromApi(key, "1")

                        when (res.status) {
                            "success" -> {
                                if (res.message.isNotEmpty()) {
                                    saveBreedWithImg(key, res.message[0])
                                    emit(responseHandler.handleSuccess("$index"))
                                }
                            }

                            "error" -> {

                            }
                        }*/
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
        var url = img_url
        if (img_url.isEmpty()) {
            //default place holder image
            url = "https://images.dog.ceo/breeds/mountain-swiss/n02107574_1033.jpg"
        }

        //Save parent breed
        val parentId = dao.insetBreed(
            Breeds(
                breedName = breedName,
                parentId = 0,
                imgUrl = url
            )
        )

        //Save Sub breed if any
        dogsBreedsHashMap[breedName]?.forEach {
            dao.insetBreed(
                Breeds(
                    breedName = it,
                    parentId = parentId,
                    imgUrl = url
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
     * Update the dog row with like/unlike status based on user action
     */
    fun updateLikeStatus(dog: DogsBreedModel): LiveData<String> {
        return liveData(Dispatchers.IO) {
            try {
                val dogDetailsModel = DogDetails(
                    dogId = dog.dogId,
                    dogUrl = dog.dogUrl,
                    isLiked = dog.isLiked,
                    breedId = dog.breedId
                )
                val id = dao.updateLikeStatus(dogDetailsModel)
                if (id > 0) {
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
    fun getAllLikedDogs(): LiveData<MutableList<DogsBreedModel>> {
        return dao.getAllLikedDogsFromDb()
    }

    /**
     * Fetch liked dogs of a specific breed
     */
    fun getLikedDogsByBreedId(breedId: Long): LiveData<MutableList<DogsBreedModel>> {
        return dao.getLikedDogsByBreedId(breedId)
    }
}