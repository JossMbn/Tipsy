package com.jmabilon.tipsy.ui.truthordare

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.data.TruthOrDare
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareBinding
import com.jmabilon.tipsy.extensions.android.getAbsActivity
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TruthOrDareFragment :
    AbsViewBindingFragment<FragmentTruthOrDareBinding>() {

    private val viewModel: TruthOrDareViewModel by viewModels()
    private var fadeOutAnim: Animation? = null
    private var fadeInAnim: Animation? = null
    private var nextCard: TruthOrDare? = null
    private var currentCardType: TruthOrDareConstants.Type = TruthOrDareConstants.Type.TUTORIAL
    private var playerList: List<String>? = null
    private var havePlayers: Boolean = false

    override fun getViewBinding(): FragmentTruthOrDareBinding {
        return FragmentTruthOrDareBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().getAbsActivity()?.adRequest?.let { adRequest ->
            listOf(binding.todAdViewTop, binding.todAdViewBottom).forEach {
                it.loadAd(adRequest)
            }
        }
        fadeOutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_animation)
        fadeInAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_animation)

        viewModel.getTodPlayerSetting()

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
            allButtonStatus(false)
            viewModel.getNextCard(playerList, TruthOrDareConstants.Choice.TRUTH)
            truthOrDareButtonLogic()
        }

        binding.dareButton.setOnClickListener {
            allButtonStatus(false)
            viewModel.getNextCard(playerList, TruthOrDareConstants.Choice.DARE)
            truthOrDareButtonLogic()
        }

        binding.nextPlayerButton.setOnClickListener {
            allButtonStatus(false)
            viewModel.updatePlayerNamesPosition()
            viewModel.getNextCard(playerList, TruthOrDareConstants.Choice.NEXT_PLAYER)
            nextPlayerButtonLogic()
        }

        binding.skipTutorialButton.setOnClickListener {
            allButtonStatus(false)
            if (havePlayers) {
                viewModel.getNextCard(playerList, TruthOrDareConstants.Choice.NEXT_PLAYER)
            } else {
                viewModel.getNextCard(playerList, TruthOrDareConstants.Choice.TRUTH)
            }
            skipTutorialButtonLogic()
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.uiState.value.isGameFinish) {
            val directions =
                TruthOrDareFragmentDirections.actionTruthOrDareFragmentToEndGameDialogFragment()
            safeNavigation(directions)
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { uiState ->
                val (nextCard, playersNameList, isGameFinish, playerSettings, showAds) = uiState

                if (showAds) {
                    viewModel.resetShowAds()
                    requireActivity().getAbsActivity()?.showInterstitialAd()
                }
                playerSettings?.let {
                    if (it) {
                        viewModel.getPlayersName()
                        viewModel.resetTodPlayerSetting()
                    }
                }
                playersNameList?.let { playersList ->
                    if (playersList.isNotEmpty()) {
                        this.playerList = playersList
                        havePlayers = true
                    }
                }
                nextCard?.let {
                    this.nextCard = it
                }
                if (isGameFinish) {
                    requireActivity().getAbsActivity()?.showInterstitialAd()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun truthOrDareButtonLogic() {
        if (currentCardType == TruthOrDareConstants.Type.GAME) {
            binding.gameCardContainer.startAnimation(fadeOutAnim)
        } else {
            listOf(binding.nextPlayerContainer, binding.truthButton, binding.dareButton).onEach {
                it.startAnimation(fadeOutAnim)
            }
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
                            allButtonStatus(true)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                            // do nothing
                        }
                    })
                }
            }

            TruthOrDareConstants.Type.GAME -> {
                if (havePlayers) {
                    fadeOutAnim?.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                            // do nothing
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            setNextCard()
                            if (currentCardType == TruthOrDareConstants.Type.NEXT_PLAYER) {
                                binding.nextPlayerContainer.visibility = View.GONE
                                binding.truthButton.visibility = View.GONE
                                binding.dareButton.visibility = View.GONE
                                binding.nextPlayerButton.startAnimation(fadeInAnim)
                                binding.nextPlayerButton.visibility = View.VISIBLE
                            } else {
                                binding.gameCardContainer.visibility = View.GONE
                            }
                            binding.gameCardContainer.startAnimation(fadeInAnim)
                            binding.gameCardContainer.visibility = View.VISIBLE
                            currentCardType = TruthOrDareConstants.Type.GAME
                            allButtonStatus(true)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                            // do nothing
                        }
                    })
                } else {
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
                            allButtonStatus(true)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                            // do nothing
                        }
                    })
                }
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
        if (havePlayers) {
            playerList?.let {
                currentCardType = TruthOrDareConstants.Type.NEXT_PLAYER
                fadeOutAnim?.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                        // do nothing
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        setNextCard()
                        binding.tutorialContainer.visibility = View.GONE
                        binding.skipTutorialButton.visibility = View.GONE
                        binding.nextPlayerName.text = nextCard?.playerName
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
                        allButtonStatus(true)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                        // do nothing
                    }
                })
            }
        } else {
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
                    allButtonStatus(true)
                }

                override fun onAnimationRepeat(p0: Animation?) {
                    // do nothing
                }
            })
        }
    }

    private fun nextPlayerButtonLogic() {
        listOf(binding.gameCardContainer, binding.nextPlayerButton).onEach {
            it.startAnimation(fadeOutAnim)
        }
        playerList?.let {
            currentCardType = TruthOrDareConstants.Type.NEXT_PLAYER
            fadeOutAnim?.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    // do nothing
                }

                override fun onAnimationEnd(p0: Animation?) {
                    setNextCard()
                    binding.gameCardContainer.visibility = View.GONE
                    binding.nextPlayerButton.visibility = View.GONE
                    binding.nextPlayerName.text = nextCard?.playerName
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
                    allButtonStatus(true)
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
                        binding.gamePlayerChoice.text = getString(R.string.tod_truth)
                    } else {
                        binding.gamePlayerChoice.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.background_blure_red_35_rounded
                        )
                        binding.gamePlayerChoice.text = getString(R.string.tod_dare)
                    }
                }

                TruthOrDareConstants.Type.TUTORIAL -> {
                    // do nothing
                }
            }
        }
    }

    private fun allButtonStatus(status: Boolean) {
        binding.addPlayersIcon.isClickable = status
        binding.truthButton.isClickable = status
        binding.dareButton.isClickable = status
        binding.nextPlayerButton.isClickable = status
        binding.skipTutorialButton.isClickable = status
    }
}