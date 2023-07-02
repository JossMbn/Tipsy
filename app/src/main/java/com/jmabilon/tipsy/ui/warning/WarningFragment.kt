package com.jmabilon.tipsy.ui.warning

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jmabilon.tipsy.databinding.FragmentWarningBinding
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WarningFragment : AbsViewBindingBottomSheetDialogFragment<FragmentWarningBinding>() {

    private val viewModel: WarningViewModel by viewModels()

    override fun getViewBinding(): FragmentWarningBinding {
        return FragmentWarningBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.validateButton.setOnClickListener {
            viewModel.disableWarningMessage()
            dismiss()
        }

        binding.exitButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.isDraggable = false
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK
        }
        return dialog
    }
}