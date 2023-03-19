package com.jmabilon.tipsy.ui.dilemma

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.DialogFragment
import com.jmabilon.tipsy.databinding.DilemmaEndGameDialogBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation

class DilemmaDialogFragment: DialogFragment() {

    private var binding: DilemmaEndGameDialogBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DilemmaEndGameDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding?.root)
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        // Prevent dialog close on back press button
        dialog.setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK
        }

        binding?.dialogButton?.setOnClickListener {
            val directions = DilemmaDialogFragmentDirections.actionDilemmaDialogFragmentToHomeFragment()
            safeNavigation(directions)
        }

        return dialog
    }
}