package com.jmabilon.tipsy.ui.neverhaveiever

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
import com.jmabilon.tipsy.databinding.FragmentNeverHaveIEverBinding
import com.jmabilon.tipsy.extensions.android.getAbsActivity
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NeverHaveIEverFragment :
    AbsViewBindingFragment<FragmentNeverHaveIEverBinding>() {

    private var toLeft: Animation? = null
    private var fromRight: Animation? = null

    private val viewModel: NeverHaveIEverViewModel by viewModels()

    private var nextSentence: String? = null
    private var isGameFinish: Boolean = false

    override fun getViewBinding(): FragmentNeverHaveIEverBinding {
        return FragmentNeverHaveIEverBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().getAbsActivity()?.adRequest?.let { adRequest ->
            listOf(binding.nhieAdViewTop, binding.nhieAdViewBottom).forEach {
                it.loadAd(adRequest)
            }
        }
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
                            NeverHaveIEverFragmentDirections.actionNeverHaveIEverFragmentToEndGameDialogFragment()
                        safeNavigation(directions)
                    } else {
                        binding.question.startAnimation(fromRight)
                        binding.question.text = this@NeverHaveIEverFragment.nextSentence
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