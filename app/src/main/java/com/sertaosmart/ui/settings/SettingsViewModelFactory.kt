package com.sertaosmart.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sertaosmart.data.repository.UserPreferencesRepository

class SettingsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(UserPreferencesRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
