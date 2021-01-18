package com.example.architecturebase.mvvm


import androidx.lifecycle.MutableLiveData
import com.example.architecturebase.network.IPostApi
import com.example.architecturebase.network.model.Post

interface MvvmContract {
    interface IPostViewModel {
        fun getPosts()
        val liveData: MutableLiveData<List<Post>>?
    }

    interface IMvvmPresenter {
        fun getPosts(): IPostApi
    }
}