package com.jmabilon.tipsy.ui.truthordare

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareOnBoardingBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment

class TruthOrDareOnBoardingFragment :
    AbsViewBindingFragment<FragmentTruthOrDareOnBoardingBinding>() {
    override fun getViewBinding(): FragmentTruthOrDareOnBoardingBinding {
        return FragmentTruthOrDareOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchBackgroundAnimation()

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.button.setOnClickListener {
            performHapticFeedback()
            val directions = TruthOrDareOnBoardingFragmentDirections
                .actionTruthOrDareOnBoardingFragmentToTruthOrDareFragment()
            safeNavigation(directions)
        }
    }
}