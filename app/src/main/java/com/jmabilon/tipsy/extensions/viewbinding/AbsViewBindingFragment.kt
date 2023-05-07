package com.jmabilon.tipsy.extensions.viewbinding

import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.jmabilon.tipsy.commons.CustomSnackBar

abstract class AbsViewBindingFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        if (_binding == null) {
            throw IllegalArgumentException("Binding must not be null")
        }

        initViewModelObservation()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun initViewModelObservation() {
        /* do something in fragment */
    }

    fun performHapticFeedback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view?.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
        }
    }

    fun launchBackgroundAnimation() {
        val animatorBackground: AnimationDrawable = binding.root.background as AnimationDrawable
        animatorBackground.setEnterFadeDuration(1000)
        animatorBackground.setExitFadeDuration(3500)
        animatorBackground.start()
    }

    fun displayError(title: String) {
        CustomSnackBar.make(
            requireActivity().findViewById(android.R.id.content),
            title = title
        ).show()
    }
}