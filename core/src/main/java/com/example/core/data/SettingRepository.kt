package com.example.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.core.domain.repository.ISettingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(
    private val pref: SharedPreferences
) : ISettingRepository {

    override fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }
}
