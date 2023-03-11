package com.jmabilon.tipsy.ui.home

import android.os.Bundle
import android.view.View
import com.jmabilon.tipsy.databinding.FragmentHomeBinding
import com.jmabilon.tipsy.extensions.abstract.AbsFragment
import com.jmabilon.tipsy.extensions.android.safeNavigation

class HomeFragment : AbsFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dilemmaGameButton.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToNestedDilemmaGraph()
            safeNavigation(directions)
        }
    }
}