package com.example.yogaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.yogaapp.databinding.FragmentLevelsBinding

/**
 * A fragment representing a list of Items.
 */
class LevelsFragment : Fragment() {

    companion object {
        const val BEGINNER = "beginner"
        const val INTERMEDIATE = "intermediate"
        const val EXPERT = "expert"
    }

    private var _binding: FragmentLevelsBinding? = null
    private val viewModel: YogaViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLevelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.beginnerCard.setOnClickListener {
            viewModel.getLevel(BEGINNER)
            val args = Bundle().apply {
                putInt("level", 1)
            }
            view.findNavController().navigate(R.id.action_levelsFragment_to_posesFragment, args)
        }

        binding.intermediateCard.setOnClickListener {
            viewModel.getLevel(INTERMEDIATE)
            val args = Bundle().apply {
                putInt("level", 2)
            }
            view.findNavController().navigate(R.id.action_levelsFragment_to_posesFragment, args)
        }

        binding.expertCard.setOnClickListener {
            viewModel.getLevel(EXPERT)
            val args = Bundle().apply {
                putInt("level", 3)
            }
            view.findNavController().navigate(R.id.action_levelsFragment_to_posesFragment, args)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}