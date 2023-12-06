package com.example.yogaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

/**
 * A fragment representing a list of Items.
 */
class CategoriesFragment : Fragment() {

    private val viewModel: YogaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                adapter = MyCategoryRecyclerViewAdapter(viewModel.categories, this) { categoryId ->
                    val args = Bundle().apply {
                        putInt("category", categoryId)
                    }
                    findNavController().navigate(R.id.action_categoriesFragment_to_posesFragment, args)
                }
            }
        }
        return view
    }
}