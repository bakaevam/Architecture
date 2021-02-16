package com.example.architecturebase.mvvm


import androidx.lifecycle.MutableLiveData
import com.example.architecturebase.network.IPostApi
import com.example.architecturebase.network.model.Post
import com.example.architecturebase.network.model.ResponsePost
import retrofit2.Response

interface MvvmContract {
    interface IPostViewModel {
        suspend fun getPosts(): Response<ResponsePost>
        val liveData: MutableLiveData<ResponsePost>?
    }

    interface IMvvmPresenter {
        fun getPosts(): IPostApi
    }
}