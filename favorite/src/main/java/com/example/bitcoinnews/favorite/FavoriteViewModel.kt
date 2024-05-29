package com.example.bitcoinnews.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}