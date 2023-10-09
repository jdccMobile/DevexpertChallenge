package com.jdccmobile.devexpertchallenge.data.model

import com.jdccmobile.devexpertchallenge.data.local.LocalPhoto

data class Photo(
    val id: String,
    val color: String,
    val description: String,
    val height: Int,
    val likes: Int,
    val urls: String,
    val userName: String,
    val width: Int,
    val isFavorite: Boolean
)

fun Photo.toLocalPhoto() = LocalPhoto(
    id = id,
    color = color,
    description = description,
    height = height,
    likes = likes,
    urls = urls,
    userName = userName,
    width = width,
    isFavorite = isFavorite
)

