package com.example.architecturebase.mvp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.R
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.PostsFragmentBinding
import com.example.architecturebase.mvp.MvpContract.MvpContract
import com.example.architecturebase.network.model.Post

class MvpView: Fragment(R.layout.posts_fragment), MvpContract.IView {
    private val presenter = MvpPresenter(this)
    private val mainAdapter = MainAdapter()
    private lateinit var binding : PostsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PostsFragmentBinding.bind(view)

        binding.mainRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mainAdapter
        }

        binding.listSRL.isRefreshing = true

        presenter.getPosts()

        binding.listSRL.setOnRefreshListener {
            mainAdapter.items = emptyList()
            presenter.getPosts()
        }
    }

    override fun showPosts(posts: List<Post>) {
        mainAdapter.items = posts
        binding.listSRL.isRefreshing = false
    }

    override fun showFailure(t: Throwable) {
        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
        t.printStackTrace()
        binding.listSRL.isRefreshing = false
    }
}