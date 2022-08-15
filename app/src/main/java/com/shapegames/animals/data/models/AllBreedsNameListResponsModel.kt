package com.shapegames.animals.data.models


import com.google.gson.annotations.SerializedName

data class AllBreedsNameListResponsModel(
    @SerializedName("message")
    val message: Message,
    @SerializedName("status")
    val status: String
)