package com.example.bitcoinnews.di

import com.example.core.domain.usecase.NewsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun newsUseCase(): NewsUseCase
}