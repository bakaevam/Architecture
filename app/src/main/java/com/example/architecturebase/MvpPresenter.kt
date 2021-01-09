package com.example.architecturebase

import com.example.architecturebase.UseCases.UseCaseGetPosts

class MvpPresenter(private val view: MvpContract.IView) : MvpContract.IPresenter {

    override fun loadPosts() {
        UseCaseGetPosts.loadPosts(view::getNewPosts, view::showFailureLoadDataDialog)
    }
}