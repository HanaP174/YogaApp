package com.example.yogaapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.yogaapp.databinding.PosesBinding
import com.example.yogaapp.model.Pose
import com.example.yogaapp.service.SvgLoaderService

class MyPoseRecyclerViewAdapter(
    private val poses: List<Pose>,
    recyclerView: RecyclerView
) : RecyclerView.Adapter<MyPoseRecyclerViewAdapter.ViewHolder>() {

    private val svgLoaderService = SvgLoaderService(recyclerView.context)

    inner class ViewHolder(binding: PosesBinding) : RecyclerView.ViewHolder(binding.root) {
        val poseName: TextView = binding.poseName
        val poseDescription: TextView = binding.poseDescriptionText
        val poseBenefit: TextView = binding.poseBenefitText
        val svg: ImageView = binding.poseIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PosesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pose = poses[position]
        holder.poseName.text = pose.englishName
        holder.poseDescription.text = pose.description
        holder.poseBenefit.text = pose.description

        svgLoaderService.loadSvgImage(pose.svg, holder.svg)
    }

    override fun getItemCount(): Int = poses.size

}