package com.example.architecturebase.mvvm

import com.example.architecturebase.network.IPostApi
import com.example.architecturebase.repository.PostsRepository

class MvvmPresenter : MvvmContract.IMvvmPresenter {
    private val repository = PostsRepository()

    override fun getPosts(): IPostApi {
        return repository.getAll()
    }
}