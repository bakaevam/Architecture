package com.example.architecturebase.mvvm.model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.architecturebase.MainActivity
import com.example.architecturebase.mvvm.MvvmContract
import com.example.architecturebase.mvvm.MvvmView
import com.example.architecturebase.network.model.Post
import com.example.architecturebase.network.model.ResponsePost
import com.example.architecturebase.repository.IPostsRepository
import com.example.architecturebase.repository.PostsRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel : ViewModel(), MvvmContract.IPostViewModel {
    override var liveData: MutableLiveData<ResponsePost>? = MutableLiveData()
    private val repository: IPostsRepository = PostsRepository()
    private val data = repository.getAll()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.v("Error", throwable.message.toString())
    }

    private val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    init {
        loadPosts()
    }

    fun loadPosts() {
        scope.launch {
            withContext(Dispatchers.IO) {
                val result = getPosts()
                if (result.isSuccessful) {
                    liveData?.postValue(result.body())
                } else {
                    liveData?.postValue(ResponsePost(emptyList()))
                }
            }
        }
    }

    override suspend fun getPosts() = repository.getAll()
            .getPosts()
            .await()
}
