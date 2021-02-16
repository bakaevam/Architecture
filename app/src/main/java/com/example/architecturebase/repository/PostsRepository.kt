package com.example.architecturebase.repository

import android.util.Log
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.ActivityMainBinding
import com.example.architecturebase.network.IPostApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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
            .baseUrl("https://social-network.samuraijs.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    override fun getAll(): IPostApi {
        Log.v("REPOSITORY", "GET ALL")
        return retrofit.create(IPostApi::class.java)
    }
}