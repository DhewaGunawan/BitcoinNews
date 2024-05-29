package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.ListNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): ListNewsResponse
}