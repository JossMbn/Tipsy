package com.jmabilon.tipsy.ui.dilemma

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.FragmentDilemmaBinding
import com.jmabilon.tipsy.extensions.abstract.AbsViewBindingFragment
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.ui.dilemma.component.DilemmaCardComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DilemmaFragment :
    AbsViewBindingFragment<FragmentDilemmaBinding>(FragmentDilemmaBinding::inflate),
    DilemmaCardComponent.DilemmaComponentListener {

    private val viewModel: DilemmaViewModel by viewModels()
    private val args: DilemmaFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData(
            args.dilemmaCount,
            resources.getStringArray(R.array.dilemma_number_item_list)
        )
        binding.firstCard.setListener(this)
        binding.secondCard.setListener(this)

        binding.backIcon.setOnClickListener {
            performHapticFeedback()
            findNavController().popBackStack()
        }

        binding.skipText.setOnClickListener {
            performHapticFeedback()
            viewModel.getNextDilemma()
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach { uiState ->
            val (dilemma, isGameFinish) = uiState
            binding.firstCard.setCardText(dilemma?.firstDilemma?.replaceFirstChar(Char::uppercaseChar))
            binding.secondCard.setCardText(dilemma?.secondDilemma?.replaceFirstChar(Char::uppercaseChar))
            if (isGameFinish) {
                val directions =
                    DilemmaFragmentDirections.actionDilemmaFragmentToDilemmaDialogFragment()
                safeNavigation(directions)
            }
        }.launchIn(lifecycleScope)
    }

    override fun onCardClick() {
        viewModel.getNextDilemma()
    }
}