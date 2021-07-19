package shradha.com.imagesearch.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import shradha.com.imagesearch.data.model.ImageRepository

class ViewModelFactory(private val imageRepository: ImageRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageViewModel(imageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}