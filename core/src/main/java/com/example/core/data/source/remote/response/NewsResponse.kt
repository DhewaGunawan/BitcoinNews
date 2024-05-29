package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("source")
    val source: Source,

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("urlToImage")
    val urlToImage: String?,

    @field:SerializedName("publishedAt")
    val publishedAt: String,

    @field:SerializedName("content")
    val content: String
)

data class Source(
    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("name")
    val name: String
)