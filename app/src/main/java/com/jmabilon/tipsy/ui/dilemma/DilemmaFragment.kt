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
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import com.jmabilon.tipsy.ui.dilemma.component.DilemmaCardComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DilemmaFragment :
    AbsViewBindingFragment<FragmentDilemmaBinding>(),
    DilemmaCardComponent.DilemmaComponentListener {

    private val viewModel: DilemmaViewModel by viewModels()
    private val args: DilemmaFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentDilemmaBinding {
        return FragmentDilemmaBinding.inflate(layoutInflater)
    }

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
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                val (dilemma, isGameFinish, dilemmaCount) = uiState
                binding.firstCard.setCardText(dilemma?.firstDilemma?.replaceFirstChar(Char::uppercaseChar))
                binding.secondCard.setCardText(dilemma?.secondDilemma?.replaceFirstChar(Char::uppercaseChar))
                binding.countText.text = resources.getString(
                    R.string.dilemma_session_count,
                    dilemmaCount,
                    args.dilemmaCount.toInt()
                )
                if (isGameFinish) {
                    val directions =
                        DilemmaFragmentDirections.actionDilemmaFragmentToEndGameDialogFragment(
                            subtitle = getString(R.string.dilemma_dialog_subtitle)
                        )
                    safeNavigation(directions)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onCardClick() {
        viewModel.getNextDilemma()
    }
}