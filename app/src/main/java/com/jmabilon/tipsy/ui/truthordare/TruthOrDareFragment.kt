package com.jmabilon.tipsy.ui.truthordare

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.data.TruthOrDare
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TruthOrDareFragment :
    AbsViewBindingFragment<FragmentTruthOrDareBinding>() {

    private val viewModel: TruthOrDareViewModel by viewModels()
    private val args: TruthOrDareFragmentArgs by navArgs()
    private var fadeOutAnim: Animation? = null
    private var fadeInAnim: Animation? = null
    private var nextCard: TruthOrDare? = null
    private var currentCardType: TruthOrDareConstants.Type = TruthOrDareConstants.Type.TUTORIAL

    override fun getViewBinding(): FragmentTruthOrDareBinding {
        return FragmentTruthOrDareBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fadeOutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_animation)
        fadeInAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_animation)

        binding.backIcon.setOnClickListener {
            performHapticFeedback()
            findNavController().popBackStack()
        }

        binding.addPlayersIcon.setOnClickListener {
            performHapticFeedback()
            val directions = TruthOrDareFragmentDirections
                .actionTruthOrDareFragmentToTruthOrDareAddPlayersFragment()
            safeNavigation(directions)
        }

        binding.truthButton.setOnClickListener {
            viewModel.getNextCard(args.playerList?.toList(), TruthOrDareConstants.Choice.TRUTH)
            truthOrDareButtonLogic()
        }

        binding.dareButton.setOnClickListener {
            viewModel.getNextCard(args.playerList?.toList(), TruthOrDareConstants.Choice.DARE)
            truthOrDareButtonLogic()
        }

        binding.skipTutorialButton.setOnClickListener {
            viewModel.getNextCard(args.playerList?.toList(), TruthOrDareConstants.Choice.TRUTH)
            skipTutorialButtonLogic()
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { uiState ->
            val (nextCard, isGameFinish) = uiState

            nextCard?.let {
                this.nextCard = it
            }
            if (isGameFinish) {
                // go to end game card
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun truthOrDareButtonLogic() {
        if (currentCardType == TruthOrDareConstants.Type.GAME) {
            binding.gameCardContainer.startAnimation(fadeOutAnim)
        } else {
            binding.nextPlayerContainer.startAnimation(fadeOutAnim)
        }
        when (nextCard?.type) {
            TruthOrDareConstants.Type.NEXT_PLAYER -> {
                nextCard?.playerName?.let {
                    fadeOutAnim?.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                            // do nothing
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            setNextCard()
                            binding.gameCardContainer.visibility = View.GONE
                            binding.nextPlayerContainer.startAnimation(fadeInAnim)
                            binding.nextPlayerContainer.visibility = View.VISIBLE
                            currentCardType = TruthOrDareConstants.Type.NEXT_PLAYER
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                            // do nothing
                        }
                    })
                }
            }
            TruthOrDareConstants.Type.GAME -> {
                fadeOutAnim?.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                        // do nothing
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        setNextCard()
                        if (currentCardType == TruthOrDareConstants.Type.NEXT_PLAYER) {
                            binding.nextPlayerContainer.visibility = View.GONE
                        } else {
                            binding.gameCardContainer.visibility = View.GONE
                        }
                        binding.gameCardContainer.startAnimation(fadeInAnim)
                        binding.gameCardContainer.visibility = View.VISIBLE
                        currentCardType = TruthOrDareConstants.Type.GAME
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                        // do nothing
                    }
                })
            }
            TruthOrDareConstants.Type.TUTORIAL,
            null -> {
                // do nothing
            }
        }
    }

    private fun skipTutorialButtonLogic() {
        listOf(binding.tutorialContainer, binding.skipTutorialButton).onEach {
            it.startAnimation(fadeOutAnim)
        }
        args.playerList?.let {
            currentCardType = TruthOrDareConstants.Type.NEXT_PLAYER
            fadeOutAnim?.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    // do nothing
                }

                override fun onAnimationEnd(p0: Animation?) {
                    setNextCard()
                    binding.tutorialContainer.visibility = View.GONE
                    binding.skipTutorialButton.visibility = View.GONE
                    listOf(
                        binding.nextPlayerContainer,
                        binding.dareButton,
                        binding.truthButton
                    ).onEach {
                        it.startAnimation(fadeInAnim)
                    }
                    binding.nextPlayerContainer.visibility = View.VISIBLE
                    binding.dareButton.visibility = View.VISIBLE
                    binding.truthButton.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(p0: Animation?) {
                    // do nothing
                }
            })
        } ?: run {
            currentCardType = TruthOrDareConstants.Type.GAME
            fadeOutAnim?.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    // do nothing
                }

                override fun onAnimationEnd(p0: Animation?) {
                    setNextCard()
                    binding.tutorialContainer.visibility = View.GONE
                    binding.skipTutorialButton.visibility = View.GONE
                    listOf(
                        binding.gameCardContainer,
                        binding.dareButton,
                        binding.truthButton
                    ).onEach {
                        it.startAnimation(fadeInAnim)
                    }
                    binding.gameCardContainer.visibility = View.VISIBLE
                    binding.dareButton.visibility = View.VISIBLE
                    binding.truthButton.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(p0: Animation?) {
                    // do nothing
                }
            })
        }
    }

    private fun setNextCard() {
        nextCard?.let { card ->
            when (card.type) {
                TruthOrDareConstants.Type.NEXT_PLAYER -> {
                    binding.nextPlayerName.text = card.playerName
                }
                TruthOrDareConstants.Type.GAME -> {
                    card.playerName?.let {
                        binding.gamePlayerName.visibility = View.VISIBLE
                        binding.gamePlayerName.text = it
                    } ?: run {
                        binding.gamePlayerName.visibility = View.GONE
                    }
                    binding.gameTruthOrDareText.text = card.playerChoiceChallenge
                    if (card.playerChoice == TruthOrDareConstants.Choice.TRUTH) {
                        binding.gamePlayerChoice.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_blure_blue_35_rounded
                        )
                        binding.gamePlayerChoice.text = "Truth"
                    } else {
                        binding.gamePlayerChoice.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_blure_red_35_rounded
                        )
                        binding.gamePlayerChoice.text = "Dare"
                    }
                }
                TruthOrDareConstants.Type.TUTORIAL -> {
                    // do nothing
                }
            }
        }
    }
}