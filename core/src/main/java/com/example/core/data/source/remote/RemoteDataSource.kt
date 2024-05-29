package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllNews(): Flow<ApiResponse<List<NewsResponse>>> {
        return flow {
            try {
                val response = apiService.getNews("bitcoin", "effdb523d82f41d9b4ab0376ab9ff965")
                val dataArray = response.articles
                Log.d("Testing4", "loadFromDB response : $response")
                Log.d("Testing4", "loadFromDB dataArray : $dataArray")
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Log.d("Testing4", "loadFromDB exception : $e")
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
