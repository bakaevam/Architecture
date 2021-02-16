package com.example.architecturebase.network

import com.example.architecturebase.network.model.Post
import com.example.architecturebase.network.model.ResponsePost
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface IPostApi {
    @GET("/api/1.0/users")
    fun getPosts(): Deferred<Response<ResponsePost>>
}
