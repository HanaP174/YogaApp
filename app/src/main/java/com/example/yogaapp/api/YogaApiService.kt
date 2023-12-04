package com.example.yogaapp.api

import com.example.yogaapp.model.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YogaApiService {

    companion object {
        const val YOGA_ENDPOINT = "categories"
    }

    @GET(YOGA_ENDPOINT)
    suspend fun getYogaAsanasByCategories() : Response<List<CategoryResponse>>
}