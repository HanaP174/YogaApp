package com.example.yogaapp.fragments.flows

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yogaapp.databinding.FlowsBinding
import com.example.yogaapp.model.Flow

class FlowRecyclerViewAdapter(
    private val flows: List<Flow>?,
) : RecyclerView.Adapter<FlowRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FlowsBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.flowName
        val duration: TextView = binding.duration
        val numberOfPoses: TextView = binding.numberOfPoses
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FlowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flow = flows?.get(position)
        holder.title.text = flow?.name
        holder.duration.text = flow?.poses?.sumOf { it.time }.toString()
        holder.numberOfPoses.text = flow?.poses?.size.toString()
    }

    override fun getItemCount(): Int {
        return flows?.size ?: 0
    }
}