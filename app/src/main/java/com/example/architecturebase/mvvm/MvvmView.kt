package com.example.architecturebase.mvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.R
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.PostsFragmentBinding
import com.example.architecturebase.network.model.Post

class MvvmView: Fragment(R.layout.posts_fragment) {
    private val presenter: MvvmContract.IMvvmPresenter = MvvmPresenter()
    private val mainAdapter = MainAdapter()
    private lateinit var binding: PostsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PostsFragmentBinding.bind(view)

        binding.mainRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mainAdapter
        }

        binding.listSRL.isRefreshing = true
        presenter.getPosts()

        presenter.data.subscribe { data ->
            mainAdapter.items = data
            binding.listSRL.isRefreshing = false
        }

        /*presenter.data.subscribe { data ->
            print(data)
            showPosts(data)

            binding.listSRL.setOnRefreshListener {
                mainAdapter.items = emptyList()
                showPosts(data)
            }
        }*/
    }

    private fun showPosts(posts: List<Post>){
        Log.v("VIEW", "SHOW POSTS")
        mainAdapter.items = posts
        binding.listSRL.isRefreshing = false
    }
}