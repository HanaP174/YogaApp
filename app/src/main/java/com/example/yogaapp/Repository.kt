package com.example.yogaapp

import com.example.yogaapp.api.YogaApiService
import com.example.yogaapp.model.CategoryResponse
import com.example.yogaapp.model.LevelResponse

class Repository (private val apiService: YogaApiService) {
    suspend fun getYogaAsanasByCategory(): List<CategoryResponse>? {
        val response = apiService.getYogaAsanasByCategories()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getYogaAsanasByLevel(level: String): LevelResponse? {
        val response = apiService.getYogaAsanasByLevel(level)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}