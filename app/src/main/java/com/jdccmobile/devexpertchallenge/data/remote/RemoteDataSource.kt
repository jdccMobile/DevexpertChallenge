package com.jdccmobile.devexpertchallenge.data.remote

import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.data.remote.model.toPhoto

class RemoteDataSource {
    suspend fun getPhotos() : List<Photo>{
        return RetrofitServiceFactory
            .makeRetrofitService()
            .getNewPhotos("cEkjo_dtwlhCZV6jR0mjjlQ4_Ic3rTSfDrZ7u24IB0s", 1, 10)
            .map { it.toPhoto() }
    }
}