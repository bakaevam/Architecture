package com.example.architecturebase.mvvm

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.R
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.PostsFragmentBinding
import com.example.architecturebase.mvvm.model.PostViewModel
import com.example.architecturebase.network.model.Post

class MvvmView : Fragment(R.layout.posts_fragment) {
    private val mainAdapter = MainAdapter()
    private lateinit var binding: PostsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PostsFragmentBinding.bind(view)
        val viewModel = PostViewModel()
        viewModel.loadPosts()

        binding.mainRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mainAdapter
        }

        binding.listSRL.isRefreshing = true

        viewModel.liveData?.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.v("VIEW", "SHOW POSTS")
                showPosts(it)
            } else {
                showFailure()
            }
        }

        binding.listSRL.setOnRefreshListener {
            mainAdapter.items = emptyList()
            viewModel.getPosts()
        }
    }

    private fun showPosts(posts: List<Post>) {
        Log.v("VIEW", "SHOW POSTS")
        mainAdapter.items = posts
        binding.listSRL.isRefreshing = false
    }

    private fun showFailure() {
        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT)
    }
}