package com.example.architecturebase.UseCases

import android.util.Log
import com.example.architecturebase.IRepository
import com.example.architecturebase.Repository
import com.example.architecturebase.network.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KFunction1

object UseCaseGetPosts {

    private val repository: IRepository = Repository()

    fun loadPosts(getPosts: KFunction1<List<Post>, Unit>, showFail: (Throwable) -> Unit) {
        Log.i("successful", "eeeeeeeeeee")
        repository.getData().getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts -> getPosts(getPostsSorted(posts)) }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                showFail(t)
            }
        })
    }

    private fun getPostsSorted(posts: List<Post>): List<Post> {
        return posts.filter {
            !it.title.startsWith("H")
        }.map {
            it.copy(title = it.title + "appendix")
        }.sortedBy {
            it.title
        }.subList(0, posts.size - 3)
    }

}