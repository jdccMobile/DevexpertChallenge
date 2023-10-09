package com.jdccmobile.devexpertchallenge.ui.screens.home

import com.jdccmobile.devexpertchallenge.data.model.Photo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jdccmobile.devexpertchallenge.data.PhotosRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PhotosRepository) : ViewModel() {

    private val _state = MutableLiveData(UiState()) // setter
    val state: LiveData<UiState> =
        _state // getter, lo podemos observar desde fuera pero modificar solo desde aqui
    // desde el vm no deberiamos poder acceder a los contextos porque los contextos pueden desparecer y el vm seguir vivo afectando a la ux


    init {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            repository.requestPhotos()
            repository.movies.collect{photo ->
                _state.value = UiState(photos = photo)
            }

        }
    }


    fun onPhotoClick(photo: Photo?) {
        viewModelScope.launch {
            if (photo != null) {
                repository.updatePhoto(photo.copy(isFavorite = !photo.isFavorite))

            } else {
                Log.e("JD", "Photo is null: $photo")
            }
        }

    }

    data class UiState(
        var isLoading: Boolean = false,
        val photos: List<Photo> = emptyList()
    )
}

// Si el viewmodel tiene argumentos de entrada
@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: PhotosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}