package com.example.yogaapp.database

import android.content.Context
import androidx.room.*

@Dao
interface FlowDao {

    @Query("select * from flow")
    fun getFlows(): List<FlowDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlow(flow: FlowDto): Long

    @Transaction
    fun insertFlowsWithPoses(flows: List<FlowDto>) {
        for (flow in flows) {
            insertFlow(flow)
            flow.poses.forEach { it.flowId = flow.id }
            insertPoses(flow.poses)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoses(poses: List<FlowPoseDto>)

    @Transaction
    fun insertPoseIntoFlow(flowId: Int, pose: FlowPoseDto) {
        val existingFlow = getFlow(flowId)
        val poses = existingFlow.poses.toMutableList()
        poses.add(pose)
        existingFlow.poses = poses
        existingFlow.numberOfPoses += 1
        insertFlow(existingFlow)
    }

    @Query("SELECT * FROM flow WHERE id = :flowId")
    fun getFlow(flowId: Int): FlowDto
}

@Database(entities = [FlowDto::class, FlowPoseDto::class], version = 1)
@TypeConverters(FlowDtoConverter::class, FlowPoseDtoConverter::class)
abstract class YogaDatabase: RoomDatabase() {
    abstract val flowDao: FlowDao
}

private lateinit var INSTANCE: YogaDatabase

fun getDatabase(context: Context): YogaDatabase {
    synchronized(YogaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            YogaDatabase::class.java,
            "yoga_database").build()
        }
    }
    return INSTANCE
}