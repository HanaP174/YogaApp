package com.example.yogaapp.fragments.flows

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yogaapp.databinding.FlowsBinding
import com.example.yogaapp.domain.Flow

class FlowRecyclerViewAdapter(
    private val flows: List<Flow>?,
    val playFlow: (Int?) -> Unit
) : RecyclerView.Adapter<FlowRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FlowsBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.flowName
        val duration: TextView = binding.duration
        val numberOfPoses: TextView = binding.numberOfPoses
        val playButton = binding.playButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FlowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flow = flows?.get(position)
        holder.title.text = flow?.name
        holder.duration.text = flow?.poses?.sumOf { it.duration }.toString()
        holder.numberOfPoses.text = flow?.poses?.size.toString()

        holder.playButton.setOnClickListener {
            playFlow(flow?.id)
        }
    }

    override fun getItemCount(): Int {
        return flows?.size ?: 0
    }
}