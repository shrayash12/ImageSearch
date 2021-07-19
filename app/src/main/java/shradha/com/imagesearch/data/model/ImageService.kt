package shradha.com.imagesearch.data.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import shradha.com.imagesearch.utitity.Constants

interface ImageService {
    @Headers("Authorization: 563492ad6f91700001000001849e07e0baba43e7853bacd9c0eb1a54")
    @GET(Constants.PATH)
    fun getAllImages(
        @Query("query") query: String,
        @Query("per_page") perPage: String
    ): Single<ImageResponse>
}