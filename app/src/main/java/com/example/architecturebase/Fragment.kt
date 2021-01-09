package com.example.architecturebase

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.FragmentBinding
import com.example.architecturebase.network.model.Post

class Fragment : Fragment(R.layout.fragment), MvpContract.IView {

    private val presenter: MvpContract.IPresenter = MvpPresenter(this)

    private val mainAdapter = MainAdapter()

    private lateinit var binding: FragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBinding.bind(view)
        binding.mainRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mainAdapter
        }
        binding.listSRL.isRefreshing = true

        presenter.loadPosts()

        binding.listSRL.setOnRefreshListener {
            mainAdapter.items = emptyList()
            presenter.loadPosts()
        }
    }

    override fun showFailureLoadDataDialog(t: Throwable) {
        t.printStackTrace()
        binding.listSRL.isRefreshing = false
        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
    }

    override fun getNewPosts(posts: List<Post>) {
        mainAdapter.items = posts
        binding.listSRL.isRefreshing = false
    }
}