package com.jmabilon.tipsy.ui.home

import android.os.Bundle
import android.view.View
import com.jmabilon.tipsy.databinding.FragmentHomeBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : AbsViewBindingFragment<FragmentHomeBinding>() {
    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dilemmaGameButton.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToNestedDilemmaGraph()
            safeNavigation(directions)
        }

        binding.trueOrDareGameButton.setOnClickListener {
            val directions =
                HomeFragmentDirections.actionHomeFragmentToTrueOrDareOnBoardingFragment()
            safeNavigation(directions)
        }
    }
}