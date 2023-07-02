package com.jmabilon.tipsy.ui.endgame

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.jmabilon.tipsy.databinding.EndGameDialogBinding
import com.jmabilon.tipsy.extensions.android.getAbsActivity
import com.jmabilon.tipsy.extensions.android.safeNavigation

class EndGameDialogFragment : DialogFragment() {

    private var binding: EndGameDialogBinding? = null
    private val args: EndGameDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = EndGameDialogBinding.inflate(layoutInflater)
        args.subtitle?.let { subtitle ->
            binding?.dialogSubtitle?.text = subtitle
        }
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
            val directions =
                EndGameDialogFragmentDirections.actionEndGameDialogFragmentToHomeFragment()
            safeNavigation(directions)
        }

        requireActivity().getAbsActivity()?.showInterstitialAd()

        return dialog
    }
}