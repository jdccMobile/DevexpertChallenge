package com.jdccmobile.devexpertchallenge.data

import com.jdccmobile.devexpertchallenge.data.model.UnsplashPhotosResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("photos")
    suspend fun listNewPhotos(
        // Query los que van detras de la interrogacion
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : UnsplashPhotosResult
}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}