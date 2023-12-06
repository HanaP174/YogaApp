package com.example.yogaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yogaapp.repository.Repository

class YogaViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YogaViewModel::class.java)) {
            return YogaViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel class does not exist")
    }
}