package com.example.yogaapp.fragments.flowtoplay

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.yogaapp.R
import com.example.yogaapp.domain.FlowPose
import com.example.yogaapp.service.SvgLoaderService
import com.example.yogaapp.viewmodel.YogaViewModel

class FlowToPlayFragment : Fragment() {

    private val viewModel: YogaViewModel by activityViewModels()
    private var flowId = 0
    private val handler = Handler(Looper.getMainLooper())
    private var secondsElapsed = 0
    private var poses: List<FlowPose> = emptyList()
    private lateinit var activePose: FlowPose
    private var currentIndex = 0

    private var svgLoaderService = SvgLoaderService(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            flowId = it.getInt(FLOW_ID)
        }

        poses = getPoses()
        activePose = poses[currentIndex]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flow_to_play_list, container, false)

        // Set the adapter
        if (view.findViewById<RecyclerView>(R.id.list) is RecyclerView) {
            with(view.findViewById<RecyclerView>(R.id.list)) {
                adapter = FlowToPlayRecyclerViewAdapter(poses, this)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        svgLoaderService = SvgLoaderService(context)
        updateCard()
        startTimer()
    }

    private fun getPoses(): List<FlowPose> {
        return viewModel.flows.value?.find { it.id == flowId }?.poses ?: emptyList()
    }

    companion object {

        const val FLOW_ID = "flowId"

    }

    private val runnable = object : Runnable {
        override fun run() {
            secondsElapsed++
            updateProgressBar()

            if (secondsElapsed >= activePose.duration) {
                currentIndex++
                if (currentIndex < poses.size) {
                    activePose = poses[currentIndex]
                    updateCard()
                    secondsElapsed = 0
                    view?.findViewById<ProgressBar>(R.id.progressBar)?.max = activePose.duration
                }
            }

            if (currentIndex >= poses.size) {
                stopTimer()
                return
            }

            // Schedule the runnable to run again after 1 second
            handler.postDelayed(this, 1000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopTimer()
    }

    private fun startTimer() {
        handler.postDelayed(runnable, 1000)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    private fun updateProgressBar() {
        if (currentIndex < poses.size) {
            view?.findViewById<ProgressBar>(R.id.progressBar)?.max = activePose.duration
            view?.findViewById<ProgressBar>(R.id.progressBar)?.progress = secondsElapsed
        }
    }

    private fun updateCard() {
        view?.findViewById<TextView>(R.id.poseName)?.text = activePose.pose.englishName
        svgLoaderService.loadSvgImage(activePose.pose.svg, view?.findViewById(R.id.poseIcon))
    }
}