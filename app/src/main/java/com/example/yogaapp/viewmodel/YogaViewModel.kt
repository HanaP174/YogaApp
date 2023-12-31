package com.example.yogaapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yogaapp.domain.Flow
import com.example.yogaapp.domain.FlowPose
import com.example.yogaapp.domain.Pose
import com.example.yogaapp.domain.mapToDatabase
import com.example.yogaapp.model.*
import com.example.yogaapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class YogaViewModel (private val repository: Repository) : ViewModel() {

    private var _categories: MutableList<Category> = mutableListOf()
    val categories: List<Category> = _categories
    var categoriesLoading = true

    private var _poses: MutableList<Pose> = mutableListOf()
    val poses: List<Pose> = _poses

    // this is object for calling api only once and distribute it to custom objects
    private var _asanasResponseByCategory: List<CategoryResponse> = emptyList()
    private lateinit var _asanasResponseByLevel: LevelResponse

    private val _flows = MutableLiveData<List<Flow>>()
    val flows: LiveData<List<Flow>> get() = _flows

    init {
        loadFlows()
        getCategories()
    }

    fun getCategories() {
        if (_asanasResponseByCategory.isEmpty() || _categories.isEmpty()) {
            // first api call
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getYogaAsanasByCategory()
                if (result is List<CategoryResponse>) {
                    _asanasResponseByCategory = result
                    mapCategories()
                    mapPoses()
                    categoriesLoading = false
                }
            }
        }
    }

    fun getPoses() {
        if (_asanasResponseByCategory.isEmpty() || _poses.isEmpty()) {
            // first api call
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getYogaAsanasByCategory()
                if (result is List<CategoryResponse>) {
                    _asanasResponseByCategory = result
                    mapPoses()
                }
            }
        }
    }

    fun getLevel(level: String) {
        if (!isLevelMapped(level)) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getYogaAsanasByLevel(level)
                if (result is LevelResponse) {
                    _asanasResponseByLevel = result
                    mapLevel()
                }
            }
        }
    }

    fun addFlow(name: String, poses: MutableList<FlowPose>) {
        val currentFlows = _flows.value.orEmpty().toMutableList()
        val flow = Flow(name = name, poses = poses, id = 0)
        viewModelScope.launch {
            val id = repository.addFlowToDb(flow.mapToDatabase())
            flow.id = id
            currentFlows.add(flow)
            _flows.value = currentFlows
        }
    }

    fun addPoseToFlow(flowId: Int, flowPose: FlowPose) {
        viewModelScope.launch {
            repository.addFlowPoseToFlowDb(flowId, flowPose.mapToDatabase())
            _flows.value?.find { it.id == flowId }?.poses?.add(flowPose)
        }
    }

    private fun loadFlows() {
        viewModelScope.launch {
            _flows.value = repository.getFlowsFromDb()
        }
    }

    private fun mapCategories() {
        _asanasResponseByCategory.forEach {
            _categories.add(Category(it.id, it.category_name,
                it.category_description, it.poses[Random.nextInt(0, it.poses.size - 1)].url_svg))
        }
    }

    private fun mapPoses() {
        val posesResponse: List<PoseResponse> = _asanasResponseByCategory.flatMap { it.poses }
        posesResponse.forEach {
            val pose: Pose = createPose(it)
            if (!_poses.contains(pose)) {
                Log.v("view mode", "pose: " + pose.englishName + " added")
                _poses.add(pose)
            }
        }
    }

    private fun mapLevel() {
        val posesResponse: List<PoseResponse> = _asanasResponseByLevel.poses
        val levelId = _asanasResponseByLevel.id
        posesResponse.forEach {
            setPoseLevel(it.id, levelId)
        }
    }

    private fun createPose(poseResponse: PoseResponse): Pose {
        return Pose(
            poseResponse.id,
            getCategoryId(poseResponse.category_name),
            0, // 0 is no id for any level
            poseResponse.english_name,
            poseResponse.sanskrit_name,
            poseResponse.pose_description,
            poseResponse.pose_benefits,
            poseResponse.url_svg
        )
    }

    private fun getCategoryId(categoryName: String): Int {
        // if no category found by its name it will be automatically assign to first one
        return _categories.find { it.name == categoryName }?.id ?: 1
    }

    private fun setPoseLevel(id: Int, levelId: Int) {
        poses.find { it.id == id }?.levelId = levelId
    }

    private fun getLevelId(level: String): Int {
        return when(level) {
            in "beginner" -> 1
            in "intermediate" -> 2
            in "expert" -> 3
            else -> 0
        }
    }

    private fun isLevelMapped(level: String): Boolean {
        val levelId = getLevelId(level)
        return poses.any { it.levelId == levelId }
    }
}