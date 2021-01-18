package com.example.architecturebase.mvvm.useCases

import android.util.Log
import android.widget.Toast
import com.example.architecturebase.MainActivity
import com.example.architecturebase.mvvm.MvvmView
import com.example.architecturebase.network.model.Post
import com.example.architecturebase.repository.IPostsRepository
import com.example.architecturebase.repository.PostsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetPostsUseCases {
    private val repository: IPostsRepository = PostsRepository()
    private val data = repository.getAll()
    private var posts = emptyList<Post>()

    fun getPosts(): List<Post> {
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

                        this@GetPostsUseCases.posts = processedPosts
                        Log.v("USE CASES", "POSTS")
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(MainActivity().applicationContext, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })

        return posts
    }
}