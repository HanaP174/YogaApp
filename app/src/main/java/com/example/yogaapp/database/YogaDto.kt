package com.example.yogaapp.database

import androidx.room.*
import com.example.yogaapp.domain.Flow
import com.example.yogaapp.domain.FlowPose
import com.example.yogaapp.domain.Pose
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "pose")
data class PoseDto (
    @PrimaryKey
    val id: Int,
    val categoryId: Int,
    var levelId: Int,
    val englishName: String,
    val originalName: String,
    val description: String,
    val benefit: String,
    val svg: String
)

fun PoseDto.mapToDomain(): Pose {
    return Pose(
        this.id,
        this.categoryId,
        this.levelId,
        this.englishName,
        this.originalName,
        this.description,
        this.benefit,
        this.svg
    )
}

@Entity(tableName = "flow_pose")
data class FlowPoseDto (
    var flowId: Int,
//    var order: Int,
    var duration: Int,
    // This is necessary to inform Room that it should include the fields of the embedded object in the containing object.
    @Embedded
    var pose: PoseDto,
    @PrimaryKey(autoGenerate = true)
    var flowPoseId: Int = 0
)

fun FlowPoseDto.mapToDomain(): FlowPose {
    return FlowPose(
        duration = this.duration,
        pose = this.pose.mapToDomain()
    )
}

@Entity(tableName = "flow")
data class FlowDto (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    @TypeConverters(FlowPoseDtoConverter::class)
    var poses: List<FlowPoseDto>,
    var numberOfPoses: Int = poses.size
)

fun FlowDto.mapToDomain(): Flow {
    return Flow(
        this.id,
        this.name,
        this.poses.map { it.mapToDomain() }.toMutableList(),
        this.numberOfPoses
    )
}

class FlowDtoConverter {
    @TypeConverter
    fun fromJson(value: String): List<FlowPoseDto> {
        val listType = object : TypeToken<List<FlowPoseDto>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toJson(list: MutableList<FlowPoseDto>): String {
        return Gson().toJson(list)
    }
}

class FlowPoseDtoConverter {
    @TypeConverter
    fun fromJson(value: String): FlowPoseDto {
        return Gson().fromJson(value, FlowPoseDto::class.java)
    }

    @TypeConverter
    fun toJson(flowPose: FlowPoseDto): String {
        return Gson().toJson(flowPose)
    }
}