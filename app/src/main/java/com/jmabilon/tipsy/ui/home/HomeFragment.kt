package com.jmabilon.tipsy.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.FragmentHomeBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import com.jmabilon.tipsy.ui.home.adapter.HomeAdapter
import com.jmabilon.tipsy.ui.home.viewholder.HomeGameCardViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment :
    AbsViewBindingFragment<FragmentHomeBinding>(),
    HomeGameCardViewHolder.HomeGameCardListener {

    private var homeAdapter: HomeAdapter? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchBackgroundAnimation()

        homeAdapter = HomeAdapter(requireContext(), this)
        homeAdapter?.initData()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = homeAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkWarningVisibility()
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { uiState ->
                val displayWarning = uiState.displayWarning

                if (displayWarning) {
                    val directions = HomeFragmentDirections.actionHomeFragmentToWarningFragment()
                    safeNavigation(directions)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onCardClick(type: HomeCardTypeEnum) {
        val directions = when (type) {
            HomeCardTypeEnum.DRINK_GAME -> {
                HomeFragmentDirections.actionHomeFragmentToNestedDrinkGameGraph()
            }

            HomeCardTypeEnum.TRUTH_OR_DARE -> {
                HomeFragmentDirections.actionHomeFragmentToTrueOrDareOnBoardingFragment()
            }

            HomeCardTypeEnum.DILEMMA -> {
                HomeFragmentDirections.actionHomeFragmentToNestedDilemmaGraph()
            }

            HomeCardTypeEnum.MOST_LIKELY_TO -> {
                HomeFragmentDirections.actionHomeFragmentToNestedMostLikelyToGraph()
            }

            HomeCardTypeEnum.NEVER_HAVE_I_EVER -> {
                HomeFragmentDirections.actionHomeFragmentToNestedNeverHaveIEverGraph()
            }
        }
        safeNavigation(directions)
    }
}