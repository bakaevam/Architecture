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
import com.example.architecturebase.repository.IPostsRepository
import com.example.architecturebase.repository.PostsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel : ViewModel(), MvvmContract.IPostViewModel {
    override var liveData: MutableLiveData<List<Post>>? = null
    private val repository: IPostsRepository = PostsRepository()
    private val data = repository.getAll()

    fun loadPosts(): MutableLiveData<List<Post>>? {
        if (liveData == null) {
            liveData = MutableLiveData()
            getPosts()
        }

        return liveData
    }

    override fun getPosts() {
        data.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        val processedPosts = posts.filter {
                            !it.title.startsWith("H")
                        }.map {
                            it.copy(title = it.title + "appendix")
                        }.sortedBy {
                            it.title
                        }.subList(0, posts.size - 3)

                        liveData?.value = processedPosts
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(MvvmView().context, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}