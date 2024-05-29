package com.example.core.utils

import android.util.Log
import com.example.core.data.source.local.entity.NewsEntity
import com.example.core.data.source.remote.response.NewsResponse
import com.example.core.domain.model.News

object DataMapper {
    fun mapResponsesToEntities(input: List<NewsResponse>): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val news = NewsEntity(
                sourceName = it.source.name,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isFavorite = false
            )
            newsList.add(news)
        }
        Log.d("Testing4", "newsList: $newsList")
        return newsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                sourceName = it.sourceName,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: News) = NewsEntity(
        sourceName = input.sourceName,
        author = input.author,
        title = input.title,
        description = input.description,
        url = input.url,
        urlToImage = input.urlToImage,
        publishedAt = input.publishedAt,
        content = input.content,
        isFavorite = input.isFavorite
    )
}