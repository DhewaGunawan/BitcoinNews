package com.example.bitcoinnews.di

import com.example.core.domain.usecase.NewsInteractor
import com.example.core.domain.usecase.NewsUseCase
import com.example.core.domain.usecase.SettingInteractor
import com.example.core.domain.usecase.SettingUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideNewsUseCase(newsInteractor: NewsInteractor): NewsUseCase

    @Binds
    @Singleton
    abstract fun provideSettingUseCase(settingInteractor: SettingInteractor): SettingUseCase

}