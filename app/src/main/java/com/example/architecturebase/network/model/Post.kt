package com.example.architecturebase.network.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)

data class ResponsePost(
        @SerializedName("items")
        val list: List<Post>
)