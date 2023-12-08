package com.example.yogaapp.fragments.pose

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.yogaapp.R
import com.example.yogaapp.model.Flow
import com.example.yogaapp.model.FlowPose
import com.example.yogaapp.model.FlowSpinnerItem
import com.example.yogaapp.model.Pose
import com.example.yogaapp.viewmodel.YogaViewModel

class AddPoseToFlowDialogFragment(private val pose: Pose) : DialogFragment() {

    private val viewModel: YogaViewModel by activityViewModels()
    private var selectedFlow: Flow? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.add_pose_to_flow_dialog, null)
            val poseDurationEditText = view.findViewById<EditText>(R.id.poseDuration)
            val flowNameEditText = view.findViewById<EditText>(R.id.flowName)
            val spinner: Spinner = view.findViewById(R.id.flows)
            val flows = viewModel.flows.value ?: emptyList()
            if (isSpinnerVisible(spinner, flowNameEditText, flows)) {
                createSpinnerAdapter(spinner, flows)
                getSelectedFlow(spinner, flows)
            }

            builder.setView(view)
                .setPositiveButton("save"
                ) { dialog, id ->
                    val poseDurationText: Int = poseDurationEditText.text.toString().toInt()
                    if (flowNameEditText.visibility == View.VISIBLE) {
                        createFlow(flowNameEditText.text.toString(), poseDurationText)
                    }
                    if (spinner.visibility == View.VISIBLE) {
                        selectedFlow?.let { addPoseToFlow(it, poseDurationText) }
                    }
                }
                .setNegativeButton("cancel"
                ) { dialog, id ->
                    getDialog()?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun createSpinnerAdapter(spinner: Spinner, flows: List<Flow>) {
        val spinnerItems = flows.map { FlowSpinnerItem(it) }
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.flow_spinner_item,
            R.id.flowName,
            spinnerItems
        )

        adapter.setDropDownViewResource(R.layout.flow_spinner_item)

        spinner.adapter = adapter
    }

    private fun isSpinnerVisible(spinner: Spinner, flowNameEditText: EditText, flows: List<Flow>): Boolean {
        return if (flows.isEmpty()) {
            spinner.visibility = View.GONE
            flowNameEditText.visibility = View.VISIBLE
            false
        } else {
            flowNameEditText.visibility = View.GONE
            true
        }
    }

    private fun createFlow(name: String, duration: Int) {
        val flow = Flow(name = name, poses = mutableListOf(createFlowPose(duration)))
        viewModel.addFlow(flow)
    }

    private fun createFlowPose(duration: Int): FlowPose {
        return FlowPose(0, duration, pose)
    }

    private fun getSelectedFlow(spinner: Spinner, flows: List<Flow>) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFlow = flows[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedFlow = null
            }
        }
    }

    private fun addPoseToFlow(flow: Flow, duration: Int) {
        val flowPose = createFlowPose(duration)
        viewModel.flows.value?.find { it.id == flow.id }?.poses?.add(flowPose)
    }
}