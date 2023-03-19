package com.jmabilon.tipsy.ui.dilemma

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.databinding.FragmentDilemmaBinding
import com.jmabilon.tipsy.extensions.abstract.AbsFragment
import com.jmabilon.tipsy.ui.dilemma.component.DilemmaCardComponent

class DilemmaFragment :
    AbsFragment<FragmentDilemmaBinding>(FragmentDilemmaBinding::inflate),
    DilemmaCardComponent.DilemmaComponentListener {

    private val viewModel: DilemmaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData()
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

        viewModel.dilemmaData.observe(viewLifecycleOwner) { dilemma ->
            binding.firstCard.setCardText(dilemma.firstDilemma?.replaceFirstChar(Char::uppercaseChar))
            binding.secondCard.setCardText(dilemma.secondDilemma?.replaceFirstChar(Char::uppercaseChar))
        }
    }

    override fun onCardClick() {
        viewModel.getNextDilemma()
    }
}