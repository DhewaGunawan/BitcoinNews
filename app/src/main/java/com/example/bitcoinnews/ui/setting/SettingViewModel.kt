package com.example.bitcoinnews.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val settingUseCase: SettingUseCase) :
    ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> = settingUseCase.getThemeSetting()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingUseCase.saveThemeSetting(isDarkModeActive)
        }
    }
}