package com.shapegames.animals.views.home

import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails

object DataManager {

    fun getDogDetailsObjectList(): MutableList<DogDetails> {
        val list = mutableListOf<DogDetails>()

        val dog = DogDetails(
            dogId = 2323,
            dogUrl = "https://github.com/",
            isLiked = true,
            breedName = "australian",
            subBreed = "shepherd"
        )
        val dog2 = DogDetails(
            dogId = 23223,
            dogUrl = "https://github.com/2",
            isLiked = true,
            breedName = "australian",
            subBreed = "shepherd"
        )
        list.add(dog)
        list.add(dog2)
        return list
    }

    fun getBreedsList(): MutableList<Breeds> {
        val list2 = mutableListOf<Breeds>()
        val breed = Breeds(
            breedName = "australian",
            subBreedName = "shepherd",
            imgUrl = "https://github.com/"
        )
        val breed2 = Breeds(
            breedName = "australian",
            subBreedName = "shepherd",
            imgUrl = "https://github.com/"
        )
        list2.add(breed)
        list2.add(breed2)
        return list2
    }

}