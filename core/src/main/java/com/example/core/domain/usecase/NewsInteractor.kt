package com.example.core.domain.usecase

import com.example.core.domain.model.News
import com.example.core.domain.repository.INewsRepository
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val newsRepository: INewsRepository) :
    NewsUseCase {

    override fun getAllNews() = newsRepository.getAllNews()

    override fun getFavoriteNews() = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: News, state: Boolean) =
        newsRepository.setFavoriteNews(news, state)
}