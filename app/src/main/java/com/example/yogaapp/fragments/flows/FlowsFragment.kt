package com.example.yogaapp.fragments.flows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.yogaapp.R
import com.example.yogaapp.viewmodel.YogaViewModel

class FlowsFragment : Fragment() {

    private val viewModel: YogaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_flows_list, container, false)

        if (view.findViewById<RecyclerView>(R.id.list) is RecyclerView) {
            with(view.findViewById<RecyclerView>(R.id.list)) {
                adapter = FlowRecyclerViewAdapter(viewModel.flows)
            }
        }
        return view
    }
}