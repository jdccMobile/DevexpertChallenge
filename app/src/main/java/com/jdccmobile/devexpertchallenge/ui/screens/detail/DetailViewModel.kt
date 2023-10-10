package com.jdccmobile.devexpertchallenge.ui.screens.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jdccmobile.devexpertchallenge.data.PhotosRepository
import com.jdccmobile.devexpertchallenge.data.model.Photo
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PhotosRepository, private val idPhoto: String): ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> = _state

    init {
        viewModelScope.launch {
            val photo = repository.getDetailPhoto(idPhoto)
            _state.value = UiState(photo = photo)
            Log.d("JD", "DetailViewModel init: $idPhoto, ${_state.value}")
        }
    }

    fun onPhotoClick(photo: Photo?) {
        viewModelScope.launch {
            if (photo != null) {
                repository.updatePhoto(photo.copy(isFavorite = !photo.isFavorite))
                _state.value = UiState(repository.getDetailPhoto(idPhoto))
                Log.d("JD", "DetailViewModel onPhotoClick: $idPhoto, ${_state.value}")
            } else {
                Log.e("JD", "DetailViewmodel onPhotoClick: Photo is null: $photo")
            }
        }
    }

    data class UiState(
        val photo: Photo? = null
    )
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val repository: PhotosRepository, private val idPhoto: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository, idPhoto) as T
    }
}