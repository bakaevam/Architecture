package com.example.architecturebase

import com.example.architecturebase.network.model.Post

interface CallbackFromRetrofit {
    fun onSuccess(value: List<Post>)
    fun onFailure(value: Throwable)
}