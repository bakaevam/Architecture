package com.example.architecturebase.mvp

import com.example.architecturebase.mvp.MvpContract.MvpContract
import com.example.architecturebase.mvp.UseCases.GetPostsUseCase
import com.example.architecturebase.network.model.Post
import com.example.architecturebase.repository.PostsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MvpPresenter(private val mvpView: MvpContract.IView) : MvpContract.IPresenter {
    private val useCase = GetPostsUseCase()

    override fun getPosts() {
        useCase.getPosts(mvpView::showPosts, mvpView::showFailure)
    }
}