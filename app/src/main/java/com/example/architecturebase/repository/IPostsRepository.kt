package com.example.architecturebase.repository

import com.example.architecturebase.network.IPostApi

interface IPostsRepository {
    fun getAll(): IPostApi
}