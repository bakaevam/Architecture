package com.example.architecturebase.mvp.UseCases

import com.example.architecturebase.network.IPostApi
import com.example.architecturebase.network.model.Post
import com.example.architecturebase.repository.IPostsRepository
import com.example.architecturebase.repository.PostsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction1

class GetPostsUseCase {
    private val repository: IPostsRepository = PostsRepository()
    private val data = repository.getAll()

    fun getPosts(funGetPosts: KFunction1<List<Post>, Unit>, Fail: KFunction1<Throwable, Unit>) {
        data.getPosts().enqueue(object: Callback<List<Post>> {

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts -> funGetPosts(
                        posts.filter {
                            !it.title.startsWith("H")
                        }.map {
                            it.copy(title = it.title + "appendix")
                        }.sortedBy {
                            it.title
                        }.subList(0, posts.size - 3)
                        )}
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Fail(t)
            }
        })
    }
}