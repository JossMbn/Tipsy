package com.jmabilon.tipsy.ui.mostlikelyto

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.FragmentMostLikelyToBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MostLikelyToFragment :
    AbsViewBindingFragment<FragmentMostLikelyToBinding>() {

    private var toLeft: Animation? = null
    private var fromRight: Animation? = null

    private val viewModel: MltViewModel by viewModels()

    private var nextSentence: String? = null
    private var isGameFinish: Boolean = false

    override fun getViewBinding(): FragmentMostLikelyToBinding {
        return FragmentMostLikelyToBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toLeft = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_center_to_left_text_animation
        )
        fromRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_right_to_left_text_animation
        )
        viewModel.loadData()

        binding.backIcon.setOnClickListener {
            performHapticFeedback()
            findNavController().popBackStack()
        }

        binding.button.setOnClickListener {
            performHapticFeedback()
            binding.button.isEnabled = false
            binding.question.startAnimation(toLeft)
            toLeft?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    // do nothing
                }

                override fun onAnimationEnd(p0: Animation?) {
                    if (isGameFinish) {
                        val directions =
                            MostLikelyToFragmentDirections.actionMltOnBoardingFragmentToEndGameDialogFragment()
                        safeNavigation(directions)
                    } else {
                        binding.question.startAnimation(fromRight)
                        binding.question.text = this@MostLikelyToFragment.nextSentence
                        viewModel.getNextSentence()
                        binding.button.isEnabled = true
                    }
                }

                override fun onAnimationRepeat(p0: Animation?) {
                    // do nothing
                }
            })
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { uiState ->
                val (loadSentence, nextSentence, isGameFinish) = uiState

                loadSentence?.let {
                    binding.question.text = it
                    viewModel.resetLoadSentence()
                }

                nextSentence?.let {
                    this.nextSentence = it
                }

                this.isGameFinish = isGameFinish
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}