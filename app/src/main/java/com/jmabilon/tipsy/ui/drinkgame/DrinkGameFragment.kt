package com.jmabilon.tipsy.ui.drinkgame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.databinding.FragmentDrinkGameBinding
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DrinkGameFragment : AbsViewBindingFragment<FragmentDrinkGameBinding>() {

    private val viewModel: DrinkGameViewModel by viewModels()

    override fun getViewBinding(): FragmentDrinkGameBinding {
        return FragmentDrinkGameBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { uiState ->
                val (test) = uiState

            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}