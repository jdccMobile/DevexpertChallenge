package com.jdccmobile.devexpertchallenge.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.jdccmobile.devexpertchallenge.data.model.Photo
import kotlinx.coroutines.flow.Flow

// la bbdd va a tener tablas con esos valores
@Database(entities = [LocalPhoto::class], version = 1)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}

// el dao define como manipulamos los datos de la bbdd
@Dao
interface PhotosDao{

    @Query("SELECT * FROM LocalPhoto")
    fun getPhotos(): Flow<List<LocalPhoto>>

    @Insert
    suspend fun insertAll(photos: List<LocalPhoto>)

    @Update
    suspend fun updatePhoto(photo: LocalPhoto)

    @Query("SELECT COUNT(*) FROM LocalPhoto")
    suspend fun count(): Int
}

// tenemos nuestra propia base de datos por si cambian la api
// asi si hay cambios externos en la api no afecta a nuestro codigo
// hay que tener un modelo(entity) de datos especifico para cada fuente de datos (api, room...)
// tambien hay que tener un modelo para el resto de la app

@Entity
data class LocalPhoto(
    @PrimaryKey
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

fun LocalPhoto.toPhoto() = Photo(
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