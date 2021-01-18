package com.example.architecturebase.mvvm

import android.util.Log
import com.example.architecturebase.mvvm.model.SetterSubscribable
import com.example.architecturebase.mvvm.model.Subscribable
import com.example.architecturebase.mvvm.useCases.GetPostsUseCases
import com.example.architecturebase.network.model.Post

class MvvmPresenter: MvvmContract.IMvvmPresenter {
    private val _data: SetterSubscribable<List<Post>> = SetterSubscribable()
    override val data: Subscribable<List<Post>> = _data
    private val useCase = GetPostsUseCases()

    override fun getPosts() {
        Log.v("PRESENTER", "GET POSTS")
        _data.setData(useCase.getPosts())
    }

}