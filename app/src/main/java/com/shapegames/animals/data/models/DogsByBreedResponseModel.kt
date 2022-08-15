package com.shapegames.animals.data.models


import com.google.gson.annotations.SerializedName

data class DogsByBreedResponseModel(
    @SerializedName("message")
    val message: List<String>,
    @SerializedName("status")
    val status: String
)