package shradha.com.imagesearch.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import shradha.com.imagesearch.data.model.ImageRepository
import shradha.com.imagesearch.data.model.ImageResponse

class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {
    private val mutableLiveData = MutableLiveData<ImageResponse>()
    val liveData: LiveData<ImageResponse> get() = mutableLiveData
    private val compositeDisposable = CompositeDisposable()
    fun getAllImagesFromRepo(query: String) {
        compositeDisposable.add(
            imageRepository
                .getAllImages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    mutableLiveData.postValue(it)
                }, onError = {
                 Log.d("Error",""+it.localizedMessage)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}