package com.example.architecturebase.mvvm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturebase.MainActivity
import com.example.architecturebase.R
import com.example.architecturebase.adapter.MainAdapter
import com.example.architecturebase.databinding.PostsFragmentBinding
import com.example.architecturebase.mvvm.model.PostViewModel
import com.example.architecturebase.network.model.ResponsePost
import com.google.android.material.snackbar.Snackbar


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
                Log.v("VIEW", "FAIL")
                showFailure()
            }
        }

        binding.listSRL.setOnRefreshListener {
            if(!isOnline()) {
                showSnackBar(view)
                binding.listSRL.isRefreshing = false
            } else {
                viewModel.loadPosts()
            }
        }
    }

    private fun showPosts(posts: ResponsePost) {
        Log.v("VIEW", "SHOW POSTS")
        mainAdapter.items = posts.list
        binding.listSRL.isRefreshing = false
    }

    private fun showFailure() {
        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT)
    }

    private fun isOnline(): Boolean {
        var result = false
        val connect = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connect.activeNetwork ?: return false
            val network =
                    connect.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connect.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }

    private fun showSnackBar(view: View) {
        val onLine = isOnline()
        if(!onLine) {
            Snackbar.make(view, "No Internet!", Snackbar.LENGTH_LONG).show()
        }
    }
}