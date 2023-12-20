package com.example.yogaapp.domain

import com.example.yogaapp.database.FlowDto
import com.example.yogaapp.database.FlowPoseDto
import com.example.yogaapp.database.PoseDto

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

fun Pose.mapToDatabase(): PoseDto {
    return PoseDto(
        id = this.id,
        categoryId = this.categoryId,
        levelId = this.levelId,
        englishName = this.englishName,
        originalName = this.originalName,
        description = this.description,
        benefit = this.benefit,
        svg = this.svg
    )
}

data class FlowPose (
    var duration: Int,
    var pose: Pose
)

fun FlowPose.mapToDatabase(): FlowPoseDto {
    return FlowPoseDto(
        flowId = 0,
        duration = this.duration,
        pose = this.pose.mapToDatabase()
    )
}

data class Flow (
    var id: Int,
    var name: String,
    var poses: MutableList<FlowPose>,
    var numberOfPoses: Int = poses.size
)

fun Flow.mapToDatabase(): FlowDto {
    return FlowDto(
        name = this.name,
        poses = this.poses.map { it.mapToDatabase() },
        numberOfPoses = this.numberOfPoses
    )
}

data class FlowSpinnerItem(val flow: Flow) {
    override fun toString(): String {
        return flow.name
    }
}