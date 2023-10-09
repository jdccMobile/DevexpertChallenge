package com.jdccmobile.devexpertchallenge.data.remote

import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.data.remote.model.toPhoto

class RemoteDataSource {
    suspend fun getPhotos() : List<Photo>{
        return RetrofitServiceFactory
            .makeRetrofitService()
            .getNewPhotos("", 1, 10)
            .map { it.toPhoto() }
        // todo con inyeccion de depencias utilizar el contexto para poner el api key desde secrets
    }
}