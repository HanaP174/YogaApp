package com.example.yogaapp.api

import com.example.yogaapp.model.CategoryResponse
import com.example.yogaapp.model.LevelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YogaApiService {

    companion object {
        const val YOGA_ENDPOINT_CATEGORIES = "categories"
        const val YOGA_ENDPOINT_LEVEL = "poses"
    }

    @GET(YOGA_ENDPOINT_CATEGORIES)
    suspend fun getYogaAsanasByCategories() : Response<List<CategoryResponse>>

    @GET(YOGA_ENDPOINT_LEVEL)
    suspend fun getYogaAsanasByLevel(@Query("level") level: String) : Response<LevelResponse>
}