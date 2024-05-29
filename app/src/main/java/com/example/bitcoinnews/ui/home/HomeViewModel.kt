package com.example.bitcoinnews.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
}