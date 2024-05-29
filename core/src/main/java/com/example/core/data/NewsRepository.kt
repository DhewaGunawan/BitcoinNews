package com.example.core.data

import android.util.Log
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.NewsResponse
import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {

    override fun getAllNews(): Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, List<NewsResponse>>() {
            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getAllNews().map {
                    Log.d("Testing5", "loadFromDB: ${DataMapper.mapEntitiesToDomain(it)}")
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<News>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<NewsResponse>>> =
                remoteDataSource.getAllNews()

            override suspend fun saveCallResult(data: List<NewsResponse>) {
                Log.d("Testing4", "saveCallResult: $data")
                val newsList = DataMapper.mapResponsesToEntities(data)
                val existingNews = localDataSource.getAllNews().first()
                if (existingNews.isNotEmpty()) {
                    val uniqueNewsList = newsList.filter { newsEntity ->
                        existingNews.none { it.title == newsEntity.title }
                    }
                    localDataSource.insertNews(uniqueNewsList)
                } else {
                    localDataSource.insertNews(newsList)
                }
            }
        }.asFlow()

    override fun getFavoriteNews(): Flow<List<News>> {
        return localDataSource.getFavoriteNews().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteNews(news: News, state: Boolean) {
        Log.d("Testing4", "setFavoriteNews: $news")
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute { localDataSource.setFavoriteNews(newsEntity, state) }
    }
}
