package com.jmabilon.tipsy.ui.dilemma

import android.os.Bundle
import android.view.View
import com.jmabilon.tipsy.databinding.FragmentDilemmaOnBoardingBinding
import com.jmabilon.tipsy.extensions.abstract.AbsFragment
import com.jmabilon.tipsy.extensions.android.safeNavigation

class DilemmaOnBoardingFragment :
    AbsFragment<FragmentDilemmaOnBoardingBinding>(FragmentDilemmaOnBoardingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            performHapticFeedback()
            val directions = DilemmaOnBoardingFragmentDirections.actionDilemmaOnBoardingFragmentToDilemmaFragment()
            safeNavigation(directions)
        }
    }
}