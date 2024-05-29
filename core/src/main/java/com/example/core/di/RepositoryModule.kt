package com.example.core.di

import com.example.core.data.NewsRepository
import com.example.core.data.SettingRepository
import com.example.core.domain.repository.INewsRepository
import com.example.core.domain.repository.ISettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(newsRepository: NewsRepository): INewsRepository

    @Binds
    abstract fun provideSettingRepository(settingRepository: SettingRepository): ISettingRepository

}