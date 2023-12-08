package com.example.yogaapp.fragments.category

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.yogaapp.databinding.CategoriesBinding
import com.example.yogaapp.model.Category
import com.example.yogaapp.service.SvgLoaderService

class CategoryRecyclerViewAdapter(
    private val categories: List<Category>,
    recyclerView: RecyclerView,
    val listener: (Int) -> Unit
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    private val svgLoaderService = SvgLoaderService(recyclerView.context)

    inner class ViewHolder(binding: CategoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        val categoryName: TextView = binding.categoryName
        val categoryDescription: TextView = binding.categoryDescriptionText
        val imageView: ImageView = binding.categoryIcon
        val categoryCard = binding.clickableCategoryCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = CategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name
        holder.categoryDescription.text = category.description

        svgLoaderService.loadSvgImage(category.svg, holder.imageView)

        holder.categoryCard.setOnClickListener {
            listener(category.id)
        }
    }

    override fun getItemCount(): Int = categories.size

}