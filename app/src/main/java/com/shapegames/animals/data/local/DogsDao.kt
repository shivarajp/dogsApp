package com.shapegames.animals.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shapegames.animals.data.models.DogsByBreedResponseModel

@Dao
interface DogsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insetBreed(breeds: Breeds): Long


    @Query("select COUNT(*) from BREEDS")
    fun getAllBreedNamesCount(): Int

    @Query("select breed_id from BREEDS where breed_name == :breedName")
    fun getBreedIdByName(breedName: String): Long


    @Insert
    fun insertSingleDogByBreed(dogDetailsModel: DogDetails)

    @Query("select COUNT(*) from DOG_DETAILS where breedid == :breedId")
    fun isThisBreedImagesAreInDB(breedId: Long): Int


    @Query(
        "select d.dog_id, d.dog_url," +
                " d.breedid, d.is_liked, b.breed_name " +
                "from DOG_DETAILS d left join breeds b on" +
                " d.breedid = b.breed_id where d.breedid == :breedId "
    )
    fun getAllDogsWithBreedByBreedId(breedId: Long): LiveData<MutableList<DogsBreedModel>>

    @Query("select * from BREEDS")
    fun getAllBreedsAsLiveData(): LiveData<MutableList<Breeds>>

    @Query("select breed_name from BREEDS where breed_id == :breedId")
    fun getBreedNameById(breedId: Long): String


    @Update
    fun updateLikeStatus(dog: DogDetails): Int

    @Query("select d.dog_id, d.dog_url," +
            " d.breedid, d.is_liked, b.breed_name " +
            "from DOG_DETAILS d left join breeds b on" +
            " d.breedid = b.breed_id where is_liked == 1")
    fun getAllLikedDogsFromDb(): LiveData<MutableList<DogsBreedModel>>

    @Query(
        "select d.dog_id, d.dog_url," +
                " d.breedid, d.is_liked, b.breed_name " +
                "from DOG_DETAILS d left join breeds b on" +
                " d.breedid = b.breed_id where d.breedid == :breedId and is_liked == 1 "
    )
    fun getLikedDogsByBreedId(breedId: Long): LiveData<MutableList<DogsBreedModel>>


}