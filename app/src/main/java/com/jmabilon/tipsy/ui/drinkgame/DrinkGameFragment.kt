package com.jmabilon.tipsy.ui.drinkgame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.FragmentDrinkGameBinding
import com.jmabilon.tipsy.extensions.android.getBaseApplication
import com.jmabilon.tipsy.extensions.android.safeNavigation
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
        listOf(binding.adViewTop, binding.adViewBottom).forEach {adView ->
            requireActivity().getBaseApplication()?.adRequest?.let {
                adView.loadAd(it)
            }
        }

        binding.backIcon.setOnClickListener {
            performHapticFeedback()
            findNavController().popBackStack()
        }

        binding.nextButton.setOnClickListener {
            performHapticFeedback()
            viewModel.updateData()
        }

        viewModel.loadData(resources.getStringArray(R.array.drink_game_player_questions_list))
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.uiState.value.gameIsFinish) {
            val directions =
                DrinkGameFragmentDirections.actionDrinkGameFragmentToEndGameDialogFragment()
            safeNavigation(directions)
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { uiState ->
                val (newSentence, gameIsFinish) = uiState

                newSentence?.let {
                    binding.gameSentence.text = it
                }
                if (gameIsFinish) {
                    requireActivity().getBaseApplication()?.interstitialAd?.show(
                        requireActivity()
                    ) ?: run {
                        val directions =
                            DrinkGameFragmentDirections.actionDrinkGameFragmentToEndGameDialogFragment()
                        safeNavigation(directions)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}