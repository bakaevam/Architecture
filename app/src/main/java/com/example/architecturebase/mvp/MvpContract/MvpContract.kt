package com.example.architecturebase.mvp.MvpContract

import com.example.architecturebase.network.model.Post

interface MvpContract {
    interface IView {
        fun showPosts(posts: List<Post>)
        fun showFailure(t: Throwable)
    }
    interface IPresenter {
        fun getPosts()
    }
}