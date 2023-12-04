package com.example.yogaapp

import com.example.yogaapp.api.YogaApiService
import com.example.yogaapp.model.CategoryResponse

class Repository (private val apiService: YogaApiService) {
    suspend fun getYogaAsanasByCategory(): List<CategoryResponse>? {
        val response = apiService.getYogaAsanasByCategories()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}