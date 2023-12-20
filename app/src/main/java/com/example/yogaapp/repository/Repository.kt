package com.example.yogaapp.repository

import com.example.yogaapp.api.YogaApiService
import com.example.yogaapp.database.FlowDto
import com.example.yogaapp.database.FlowPoseDto
import com.example.yogaapp.database.YogaDatabase
import com.example.yogaapp.database.mapToDomain
import com.example.yogaapp.domain.Flow
import com.example.yogaapp.model.CategoryResponse
import com.example.yogaapp.model.LevelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository (private val apiService: YogaApiService, private val database: YogaDatabase) {
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

    suspend fun getFlowsFromDb(): List<Flow> {
        return withContext(Dispatchers.IO) {
            database.flowDao.getFlows().map { it.mapToDomain() }
        }
    }

    suspend fun addFlowToDb(flow: FlowDto): Int {
        return withContext(Dispatchers.IO) {
            database.flowDao.insertFlow(flow).toInt()
        }
    }

    suspend fun addFlowPoseToFlowDb(flowId: Int, flowPose: FlowPoseDto) {
        withContext(Dispatchers.IO) {
            database.flowDao.insertPoseIntoFlow(flowId, flowPose)
        }
    }
}