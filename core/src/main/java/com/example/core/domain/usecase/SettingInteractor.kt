package com.example.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.core.domain.repository.ISettingRepository
import javax.inject.Inject

class SettingInteractor @Inject constructor(private val settingRepository: ISettingRepository) :
    SettingUseCase {

    override fun getThemeSetting(): LiveData<Boolean> = settingRepository.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        settingRepository.saveThemeSetting(isDarkModeActive)
}