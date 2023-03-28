package com.jmabilon.tipsy.ui.truthordare

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareBinding
import com.jmabilon.tipsy.extensions.abstract.AbsViewBindingFragment

class TruthOrDareFragment :
    AbsViewBindingFragment<FragmentTruthOrDareBinding>(FragmentTruthOrDareBinding::inflate) {

    private var flipFront: AnimatorSet? = null
    private var flipBack: AnimatorSet? = null
    private var isFront = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flipFront = AnimatorInflater.loadAnimator(requireContext(), R.animator.flip_front) as AnimatorSet
        flipBack = AnimatorInflater.loadAnimator(requireContext(), R.animator.flip_back) as AnimatorSet

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.truthButton.setOnClickListener {
            launchCardFlipAnimation()
        }

        binding.dareButton.setOnClickListener {
            launchCardFlipAnimation()
        }
    }

    private fun launchCardFlipAnimation() {
        try {
            val scale = requireContext().applicationContext.resources.displayMetrics.density
            binding.todCardFront.root.cameraDistance = 8000 * scale
            binding.todCardBack.root.cameraDistance = 8000 * scale

            if (isFront) {
                flipFront?.setTarget(binding.todCardFront.root)
                flipBack?.setTarget(binding.todCardBack.root)
                flipFront?.start()
                flipBack?.start()
                isFront = false

            } else {
                flipFront?.setTarget(binding.todCardBack.root)
                flipBack?.setTarget(binding.todCardFront.root)
                flipBack?.start()
                flipFront?.start()
                isFront = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}