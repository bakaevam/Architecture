package com.example.architecturebase

import androidx.lifecycle.*
import com.example.architecturebase.UseCases.UseCaseGetPosts
import com.example.architecturebase.network.model.Post

class ViewModelMvvm : LifecycleObserver, MvvmContract {


    override val listPosts: MutableLiveData<List<Post>> = MutableLiveData()

    override val errorMessage: MutableLiveData<Throwable> = MutableLiveData()

    override fun getPosts() {
        UseCaseGetPosts.loadPosts(object : CallbackFromRetrofit {
            override fun onSuccess(value: List<Post>) {
                listPosts.value = value
            }

            override fun onFailure(value: Throwable) {
                errorMessage.value = value
            }
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        getPosts()
    }
}