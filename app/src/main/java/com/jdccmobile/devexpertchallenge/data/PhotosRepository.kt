package com.jdccmobile.devexpertchallenge.data

import com.jdccmobile.devexpertchallenge.data.local.LocalDataSource
import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow


// el viewmodel se comunica con el repository y este hace la peticion donde sea necesario
class PhotosRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    val movies: Flow<List<Photo>> = localDataSource.photos

    suspend fun updatePhoto(photo: Photo){
        localDataSource.updatePhoto(photo)
    }

    suspend fun getDetailPhoto(idPhoto: String) : Photo{
        return localDataSource.getDetailPhoto(idPhoto)
    }

    suspend fun requestPhotos(){
        val isDbEmpty = localDataSource.count() == 0
        if(isDbEmpty){localDataSource.insertAll(remoteDataSource.getPhotos())}
    }

}