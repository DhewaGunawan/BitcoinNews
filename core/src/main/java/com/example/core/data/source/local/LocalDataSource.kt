package com.example.core.data.source.local

import android.util.Log
import com.example.core.data.source.local.entity.NewsEntity
import com.example.core.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val newsDao: NewsDao) {

    fun getAllNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()

    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()

    suspend fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)

    fun setFavoriteNews(news: NewsEntity, newState: Boolean) {
        Log.d("Testing4", "setFavoriteNews news : $news & $newState")
        news.isFavorite = newState
        newsDao.updateFavoriteNews(news)
    }
}