package com.shapegames.animals.data.remote

import com.shapegames.animals.data.models.AllBreedsNameListResponsModel
import com.shapegames.animals.data.models.DogsByBreedResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsAPI {

    @GET("{breed}/images/random/{img_count}")
    suspend fun getDogsByBreedFromApi(@Path("breed") breed: String, @Path("img_count") count: String): DogsByBreedResponseModel

    @GET("{breed}/{sub_breed}/images/random/100")
    suspend fun getDogsBySubBreedFromApi(
        @Path("breed") breed: String,
        @Path("sub_breed") subBreed: String
    ):DogsByBreedResponseModel

    @GET("list/all")
    suspend fun getAllBreedNamesFromApi(): AllBreedsNameListResponsModel


}