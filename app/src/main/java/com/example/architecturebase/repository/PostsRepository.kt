package com.example.architecturebase.repository

import android.util.Log
import com.example.architecturebase.MainActivity
import com.example.architecturebase.network.IPostApi
import com.example.architecturebase.repository.PostsRepository.Companion.REQUEST_TIMEOUT_SECONDS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PostsRepository : IPostsRepository {

    companion object {
        private const val REQUEST_TIMEOUT_SECONDS = 5L
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .callTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    override fun getAll(): IPostApi {
        Log.v("REPOSITORY", "GET ALL DATA")
        return retrofit.create(IPostApi::class.java)
    }
}