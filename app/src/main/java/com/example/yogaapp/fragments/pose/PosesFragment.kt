package com.example.yogaapp.fragments.pose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.yogaapp.R
import com.example.yogaapp.viewmodel.YogaViewModel
import com.example.yogaapp.domain.Pose

/**
 * A fragment representing a list of Items.
 */
class PosesFragment : Fragment() {

    private val viewModel: YogaViewModel by activityViewModels()
    private var levelId: Int = -1
    private var categoryId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poses_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                val poses = getPoses()
                adapter = PoseRecyclerViewAdapter(poses, this) { pose ->
                    val addPoseToFlowFragment = AddPoseToFlowDialogFragment(pose)
                    addPoseToFlowFragment.show(parentFragmentManager, "addPoseToFlow")
                }
            }
        }
        return view
    }

    private fun getPoses(): List<Pose> {
        return if (levelId != -1) {
            filterPosesByLevelId()
        } else if (categoryId != -1) {
            filterPosesByCategoryId()
        } else {
            viewModel.poses
        }
    }

    private fun filterPosesByCategoryId(): List<Pose> {
        return viewModel.poses
            .filter { pose -> pose.categoryId == categoryId}
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
            categoryId = it.getInt(CATEGORY)
            Log.v("poses fragment", "category assigned $categoryId")

        }
    }

    companion object {
        const val LEVEL = "level"
        const val CATEGORY = "category"
    }
}