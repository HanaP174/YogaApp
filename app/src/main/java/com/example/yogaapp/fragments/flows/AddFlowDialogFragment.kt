package com.example.yogaapp.fragments.flows

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.yogaapp.R
import com.example.yogaapp.viewmodel.YogaViewModel

class AddFlowDialogFragment : DialogFragment() {

    private val viewModel: YogaViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.add_flow_dialog, null)
            val flowNameEditText = view.findViewById<EditText>(R.id.flowName)

            builder.setView(view)
                .setPositiveButton("save"
                ) { dialog, id ->
                    val enteredText = flowNameEditText.text.toString()
                    createFlow(enteredText)
                }
                .setNegativeButton("cancel"
                ) { dialog, id ->
                    getDialog()?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun createFlow(name: String) {
        viewModel.addFlow(name, mutableListOf())
    }
}