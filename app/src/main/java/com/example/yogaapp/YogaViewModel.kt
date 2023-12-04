package com.example.yogaapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yogaapp.model.Category
import com.example.yogaapp.model.CategoryResponse
import com.example.yogaapp.model.Pose
import com.example.yogaapp.model.PoseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class YogaViewModel (private val repository: Repository) : ViewModel() {

    val selectedCategory = MutableLiveData<Int>()

    private var _categories: MutableList<Category> = mutableListOf()
    val categories: List<Category> = _categories

    private var _poses: MutableList<Pose> = mutableListOf()
    val poses: List<Pose> = _poses

    // this is object for calling api only once and distribute it to custom objects
    private var _asanasResponse: List<CategoryResponse> = emptyList()

    fun getCategories() {
        if (_asanasResponse.isEmpty() || _categories.isEmpty()) {
            // first api call
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getYogaAsanasByCategory()
                if (result is List<CategoryResponse>) {
                    _asanasResponse = result
                    mapCategories()
                }
            }
        }
    }

    fun getPoses() {
        if (_asanasResponse.isEmpty() || _poses.isEmpty()) {
            // first api call
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getYogaAsanasByCategory()
                if (result is List<CategoryResponse>) {
                    _asanasResponse = result
                    mapPoses()
                }
            }
        }
    }

    private fun mapCategories() {
        _asanasResponse.forEach {
            _categories.add(Category(it.id, it.category_name,
                it.category_description, it.poses[Random.nextInt(0, it.poses.size - 1)].url_svg))
        }
    }

    private fun mapPoses() {
        val posesResponse: List<PoseResponse> = _asanasResponse.flatMap { it.poses }
        posesResponse.forEach {
            val pose: Pose = createPose(it)
            if (!_poses.contains(pose)) {
                Log.v("view mode", "pose: " + pose.englishName + " added")
                _poses.add(pose)
            }
        }
    }

    private fun createPose(poseResponse: PoseResponse): Pose {
        return Pose(
            poseResponse.id,
            getCategoryId(poseResponse.category_name),
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
}