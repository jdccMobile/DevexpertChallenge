package com.jdccmobile.devexpertchallenge.data.remote.model

import com.jdccmobile.devexpertchallenge.data.model.Photo

data class UnsplashPhotosResult(
    val color: String,
    val description: String?,
    val height: Int,
    val id: String,
    val likes: Int,
    val urls: Urls,
    val user: User,
    val width: Int,
    var isFavorite: Boolean
)

fun UnsplashPhotosResult.toPhoto() = Photo(
    id = id,
    color = color,
    description = description ?: "Without description",
    height = height,
    likes = likes,
    urls = urls.small,
    userName = user.username,
    width = width,
    isFavorite = isFavorite
)

