package com.jmabilon.tipsy.ui.truthordare

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareOnBoardingBinding
import com.jmabilon.tipsy.extensions.abstract.AbsViewBindingFragment

class TruthOrDareOnBoardingFragment :
    AbsViewBindingFragment<FragmentTruthOrDareOnBoardingBinding>(
        FragmentTruthOrDareOnBoardingBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchBackgroundAnimation()

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}