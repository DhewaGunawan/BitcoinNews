package com.example.bitcoinnews.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.usecase.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val settingUseCase: SettingUseCase) :
    ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> = settingUseCase.getThemeSetting()
}