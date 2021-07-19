package shradha.com.imagesearch.ui

import `in`.arunkumarsampath.suggestions.RxSuggestions
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import shradha.com.imagesearch.R
import shradha.com.imagesearch.data.model.ImageRepository
import shradha.com.imagesearch.domain.ImageViewModel
import shradha.com.imagesearch.domain.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var etImageSearch: EditText
    lateinit var buttonSearch: Button
    lateinit var recyclerView: RecyclerView
    val compositeDisposable = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        val imageRepository = ImageRepository()
        val viewModelFactory = ViewModelFactory(imageRepository)
        val viewModel: ImageViewModel = ViewModelProvider(this, viewModelFactory)
            .get(ImageViewModel::class.java)
        val imageAdapter = ImageListAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = imageAdapter
        buttonSearch.setOnClickListener {
            Log.d("MainActivity", "button clicked")
            viewModel.getAllImagesFromRepo(etImageSearch.text.toString())
        }

        viewModel.liveData.observe(this, Observer {
            imageAdapter.submitList(it.photos)
        })

        compositeDisposable.add(RxTextView.afterTextChangeEvents(etImageSearch)
            .map { changeEvent: TextViewAfterTextChangeEvent ->
                changeEvent.editable().toString()
            }
            .compose(RxSuggestions.suggestionsTransformer())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setSuggestions(it) },
                Action1 { obj: Throwable -> obj.printStackTrace() })
        )
    }

    private fun setSuggestions(suggestions: List<String?>?) {
       Log.d("MainActivity", "" + suggestions?.size)
    }

    private fun findViews() {
        etImageSearch = findViewById(R.id.etImageSearch)
        buttonSearch = findViewById(R.id.buttonSearch)
        recyclerView = findViewById(R.id.recyclerView)
    }
}