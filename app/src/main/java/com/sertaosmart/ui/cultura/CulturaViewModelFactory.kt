package com.sertaosmart.ui.cultura

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sertaosmart.data.AppDatabase
import com.sertaosmart.data.repository.CulturaRepository

class CulturaViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CulturaViewModel::class.java)) {
            val culturaDao = AppDatabase.getDatabase(context).culturaDao()
            val culturaRepository = CulturaRepository(culturaDao)
            @Suppress("UNCHECKED_CAST")
            return CulturaViewModel(culturaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
