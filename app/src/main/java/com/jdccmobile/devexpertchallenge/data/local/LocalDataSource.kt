package com.jdccmobile.devexpertchallenge.data.local

import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.data.model.toLocalPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val dao: PhotosDao){

    val photos: Flow<List<Photo>> = dao.getPhotos().map { photos ->
        photos.map { it.toPhoto() }
    }

    suspend fun getDetailPhoto(idPhoto: String) : Photo{
        return dao.getDetailPhoto(idPhoto)
    }

    suspend fun updatePhoto(photo: Photo){
        dao.updatePhoto(photo.toLocalPhoto())
    }

    suspend fun insertAll(photos: List<Photo>){
        dao.insertAll(photos.map { it.toLocalPhoto()})
    }
    suspend fun count(): Int {
        return dao.count()
    }

}