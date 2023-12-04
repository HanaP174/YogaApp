package com.example.yogaapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class YogaViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YogaViewModel::class.java)) {
            return YogaViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel class does not exist")
    }
}