package com.example.yogaapp.fragments.flowtoplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.yogaapp.R
import com.example.yogaapp.model.FlowPose
import com.example.yogaapp.viewmodel.YogaViewModel

/**
 * A fragment representing a list of Items.
 */
class FlowToPlayFragment : Fragment() {

    private val viewModel: YogaViewModel by activityViewModels()
    private var flowId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            flowId = it.getInt(FLOW_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flow_to_play_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                val poses = getPoses()
                adapter = FlowToPlayRecyclerViewAdapter(poses, this)
            }
        }
        return view
    }

    private fun getPoses(): List<FlowPose> {
        return viewModel.flows.value?.find { it.id == flowId }?.poses ?: emptyList()
    }

    companion object {

        const val FLOW_ID = "flowId"

    }
}