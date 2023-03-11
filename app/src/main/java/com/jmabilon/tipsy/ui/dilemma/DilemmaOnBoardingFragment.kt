package com.jmabilon.tipsy.ui.dilemma

import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import com.jmabilon.tipsy.databinding.FragmentDilemmaOnBoardingBinding
import com.jmabilon.tipsy.extensions.abstract.AbsFragment
import com.jmabilon.tipsy.extensions.android.safeNavigation

class DilemmaOnBoardingFragment :
    AbsFragment<FragmentDilemmaOnBoardingBinding>(FragmentDilemmaOnBoardingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            }
            val directions = DilemmaOnBoardingFragmentDirections.actionDilemmaOnBoardingFragmentToDilemmaFragment()
            safeNavigation(directions)
        }
    }
}