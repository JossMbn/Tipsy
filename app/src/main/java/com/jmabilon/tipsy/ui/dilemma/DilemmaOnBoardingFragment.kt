package com.jmabilon.tipsy.ui.dilemma

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.FragmentDilemmaOnBoardingBinding
import com.jmabilon.tipsy.extensions.abstract.AbsFragment
import com.jmabilon.tipsy.extensions.android.safeNavigation

class DilemmaOnBoardingFragment :
    AbsFragment<FragmentDilemmaOnBoardingBinding>(FragmentDilemmaOnBoardingBinding::inflate) {

    private var dilemmaCount: String? = null
    var dropDownItems: Array<String>? = null
    var dropDownAdapter: ArrayAdapter<String>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dropdownMenuTextView.setOnItemClickListener { _, _, _, _ ->
            dilemmaCount = binding.dropdownMenuTextView.text.toString()
        }

        binding.backIcon.setOnClickListener {
            performHapticFeedback()
            findNavController().popBackStack()
        }

        binding.button.setOnClickListener {
            performHapticFeedback()
            dilemmaCount?.let { count ->
                val directions = DilemmaOnBoardingFragmentDirections
                    .actionDilemmaOnBoardingFragmentToDilemmaFragment(
                        dilemmaCount = count
                    )
                safeNavigation(directions)
            } ?: run {
                val directions = DilemmaOnBoardingFragmentDirections
                    .actionDilemmaOnBoardingFragmentToDilemmaFragment()
                safeNavigation(directions)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dropDownItems = resources.getStringArray(R.array.dilemma_number_item_list)
        dropDownItems?.let { items ->
            dropDownAdapter = ArrayAdapter(requireContext(), R.layout.item_dilemma_dropdown_list, items)
            binding.dropdownMenuTextView.setAdapter(dropDownAdapter)
            dropDownAdapter?.let { adapter ->
                binding.dropdownMenuTextView.setText(adapter.getItem(1).toString(), false)
            }
        } ?: run {
            binding.dropdownMenu.visibility = View.GONE
        }
    }
}