package com.jdccmobile.devexpertchallenge.data.remote

import com.jdccmobile.devexpertchallenge.data.remote.model.UnsplashPhotosResult

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("photos")
    suspend fun getNewPhotos(
        // Query los que van detras de la interrogacion
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : MutableList<UnsplashPhotosResult>
}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}