package com.jdccmobile.devexpertchallenge.data

import com.jdccmobile.devexpertchallenge.data.local.LocalDataSource
import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.data.remote.RemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking

class PhotosRepositoryTest {
    // comprobamos que si la base de datos es vacio, se ha hecho la peticion de retrofit

    @Test
    fun `When DB is empty, server is called`() {
        // GIVEN
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 0 // cuando llame al count devuelve un cero porque si no seria un null y daria error
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = PhotosRepository(localDataSource, remoteDataSource)

        // WHEN
        runBlocking { repository.requestPhotos() }

        //THEN
        verifyBlocking(remoteDataSource, times(1)) { getPhotos() } // verificar si se ha llamado al getphotos
    }

    @Test
    fun `When DB is empty, photos are saved into DB`() {
        // GIVEN
        val expectedPhotos = listOf(Photo("1", "test", "test", 5,5,"test", "test", 5, false))
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 0
        }
        val remoteDataSource = mock<RemoteDataSource>(){
            onBlocking { getPhotos() } doReturn expectedPhotos
        }
        val repository = PhotosRepository(localDataSource, remoteDataSource)

        // WHEN
        runBlocking { repository.requestPhotos() }

        //THEN
        verifyBlocking(localDataSource, ) { insertAll(any()) } // quiero que me llames al localdata source con cualquier valor
    }

    @Test
    fun `When DB is not empty, remote data source is not called`() {
        // GIVEN
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 1 // cuando llame al count devuelve un uno porque si no seria un null y daria error
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = PhotosRepository(localDataSource, remoteDataSource)

        // WHEN
        runBlocking { repository.requestPhotos() }

        //THEN
        verifyBlocking(remoteDataSource, times(0)) { getPhotos() } // verificar si no se ha llamado al getphotos
    }
}