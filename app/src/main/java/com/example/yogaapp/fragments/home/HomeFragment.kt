package com.example.yogaapp.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yogaapp.R
import com.example.yogaapp.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryCard.setOnClickListener {
            // Navigate to AnotherFragment using the action
            findNavController().navigate(R.id.action_HomeFragment_to_categoriesFragment)
        }

        binding.poseCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_posesFragment)
        }

        binding.levelCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_levelsFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}