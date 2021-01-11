package com.example.architecturebase

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.example.architecturebase.network.model.Post

interface MvvmContract : LifecycleObserver {

    val listPosts: LiveData<List<Post>>
    val errorMessage: LiveData<Throwable>
    fun getPosts()
}