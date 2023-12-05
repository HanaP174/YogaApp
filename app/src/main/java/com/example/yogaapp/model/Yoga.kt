package com.example.yogaapp.model

data class Category (
    val id: Int,
    val name: String,
    val description: String,
    val svg: String
)

data class Pose (
    val id: Int,
    val categoryId: Int,
    var levelId: Int,
    val englishName: String,
    val originalName: String,
    val description: String,
    val benefit: String,
    val svg: String
)

data class YogaFlowPose (
    var order: Int,
    var time: Int,
    var pose: Pose
)

data class CategoryResponse (
    val id: Int,
    val category_name: String,
    val category_description: String,
    val poses: List<PoseResponse>
)

data class PoseResponse (
    val id: Int,
    val category_name: String,
    val english_name: String,
    val sanskrit_name_adapted: String,
    val sanskrit_name: String,
    val pose_description: String,
    val pose_benefits: String,
    val url_svg: String
)

data class LevelResponse (
    val id: Int,
    val difficulty_level: String,
    val poses: List<PoseResponse>
)