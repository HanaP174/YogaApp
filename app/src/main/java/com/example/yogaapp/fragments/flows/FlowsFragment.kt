package com.example.yogaapp.fragments.flows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.yogaapp.R
import com.example.yogaapp.domain.Flow
import com.example.yogaapp.viewmodel.YogaViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FlowsFragment : Fragment() {

    private val viewModel: YogaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_flows_list, container, false)

        if (view.findViewById<RecyclerView>(R.id.list) is RecyclerView) {
            with(view.findViewById<RecyclerView>(R.id.list)) {
                adapter = FlowRecyclerViewAdapter(viewModel.flows.value) { flowId ->
                    navigateToPlayFlow(flowId)
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.addFlowButton).setOnClickListener {
            val newFlowFragment = AddFlowDialogFragment()
            newFlowFragment.show(parentFragmentManager, "newFlow")
        }

        viewModel.flows.observe(viewLifecycleOwner) { newFlows ->
            updateRecyclerView(newFlows)
        }
    }

    private fun updateRecyclerView(newFlows: List<Flow>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.list)
        recyclerView?.adapter = FlowRecyclerViewAdapter(newFlows) { flowId ->
            navigateToPlayFlow(flowId)
        }
        recyclerView?.adapter?.notifyItemInserted(newFlows.size - 1)
    }

    private fun navigateToPlayFlow(flowId: Int?) {
        if (flowId != null) {
            val args = Bundle().apply {
                putInt("flowId", flowId)
            }
            findNavController().navigate(R.id.action_FlowsFragment_to_flowToPlayFragment, args)
        }
    }
}