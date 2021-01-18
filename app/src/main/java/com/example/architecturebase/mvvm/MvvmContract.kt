package com.example.architecturebase.mvvm

import com.example.architecturebase.mvvm.model.Subscribable
import com.example.architecturebase.network.model.Post

interface MvvmContract {
    interface IMvvmPresenter {
        val data: Subscribable<List<Post>>

        fun getPosts()
    }
}