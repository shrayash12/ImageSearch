package shradha.com.imagesearch.data.model

import io.reactivex.rxjava3.core.Single
import shradha.com.imagesearch.utitity.Constants

class ImageRepository {
    fun getAllImages(query: String):Single<ImageResponse>{
      return  MyApi.getImageService().getAllImages(query,"100")
    }
}