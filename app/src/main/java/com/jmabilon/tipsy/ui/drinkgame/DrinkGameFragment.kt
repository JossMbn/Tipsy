package com.jmabilon.tipsy.ui.drinkgame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.FragmentDrinkGameBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DrinkGameFragment : AbsViewBindingFragment<FragmentDrinkGameBinding>() {

    private val viewModel: DrinkGameViewModel by viewModels()

    private var interstitialAd: InterstitialAd? = null
    private var adRequest: AdRequest? = null

    override fun getViewBinding(): FragmentDrinkGameBinding {
        return FragmentDrinkGameBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adRequest = AdRequest.Builder().build()
        listOf(binding.adViewTop, binding.adViewBottom).forEach {
            adRequest?.let { ad ->
                it.loadAd(ad)
            }
        }
        setupInterstitialAds()

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
            val directions = DrinkGameFragmentDirections.actionDrinkGameFragmentToEndGameDialogFragment()
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
                    if (interstitialAd != null) {
                        interstitialAd?.show(requireActivity())
                    } else {
                        val directions = DrinkGameFragmentDirections.actionDrinkGameFragmentToEndGameDialogFragment()
                        safeNavigation(directions)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupInterstitialAds() {
        adRequest?.let {
            InterstitialAd.load(
                requireContext(),
                "ca-app-pub-2132066617984288/9567106784",
                it,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        interstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        this@DrinkGameFragment.interstitialAd = interstitialAd
                    }
                })
        }

        interstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
            }
        }
    }
}