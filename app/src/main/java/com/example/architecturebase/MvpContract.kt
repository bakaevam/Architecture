package com.example.architecturebase

import com.example.architecturebase.network.model.Post

interface MvpContract {

    interface IView {
        fun showFailureLoadDataDialog(t: Throwable)
        fun getNewPosts(posts: List<Post>)
    }

    interface IPresenter {
        fun loadPosts()
    }
}