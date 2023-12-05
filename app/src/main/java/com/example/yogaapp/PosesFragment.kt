package com.example.yogaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.yogaapp.model.Pose

/**
 * A fragment representing a list of Items.
 */
class PosesFragment : Fragment() {

    private val viewModel: YogaViewModel by activityViewModels()
    private var levelId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poses_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                val poses = if (levelId != 0) {
                    filterPosesByLevelId()
                } else {
                    filterPosesByCategoryId()
                }
                adapter = MyPoseRecyclerViewAdapter(poses, this)
            }
        }
        return view
    }

    private fun filterPosesByCategoryId(): List<Pose> {
        if (viewModel.selectedCategory.value != null) {
            val poses = viewModel.poses
                .filter { pose -> pose.categoryId == viewModel.selectedCategory.value}
            viewModel.selectedCategory.value = null
            return poses
        }
        return viewModel.poses
    }

    private fun filterPosesByLevelId(): List<Pose> {
        return viewModel.poses
            .filter { pose -> pose.levelId == levelId }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            levelId = it.getInt(LEVEL)
            Log.v("poses fragment", "level assigned $levelId")
        }
    }

    companion object {
        const val LEVEL = "level"
    }
}