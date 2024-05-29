package com.example.bitcoinnews.ui.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.News
import com.example.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    fun setFavoriteNews(news: News, newStatus: Boolean) =
        newsUseCase.setFavoriteNews(news, newStatus)
}