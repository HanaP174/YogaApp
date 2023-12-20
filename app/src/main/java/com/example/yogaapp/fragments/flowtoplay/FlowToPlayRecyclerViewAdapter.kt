package com.example.yogaapp.fragments.flowtoplay

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.yogaapp.databinding.FlowToPlayBinding
import com.example.yogaapp.domain.FlowPose

import com.example.yogaapp.service.SvgLoaderService

class FlowToPlayRecyclerViewAdapter(
    private val flowPoses: List<FlowPose>,
    recyclerView: RecyclerView,
) : RecyclerView.Adapter<FlowToPlayRecyclerViewAdapter.ViewHolder>() {

    private val svgLoaderService = SvgLoaderService(recyclerView.context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FlowToPlayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pose = flowPoses[position]
        holder.poseName.text = pose.pose.englishName
        holder.poseDescription.text = pose.pose.description
        holder.duration.text = pose.duration.toString()

        svgLoaderService.loadSvgImage(pose.pose.svg, holder.svg)
    }

    override fun getItemCount(): Int = flowPoses.size

    inner class ViewHolder(binding: FlowToPlayBinding) : RecyclerView.ViewHolder(binding.root) {
        val poseName: TextView = binding.poseName
        val poseDescription: TextView = binding.poseDescriptionText
        val svg: ImageView = binding.poseIcon
        val duration = binding.poseDurationText
    }

}