package com.jmabilon.tipsy.ui.truthordare.addplayers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareAddPlayersBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.android.showFeedback
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import com.jmabilon.tipsy.ui.home.HomeActivity
import com.jmabilon.tipsy.ui.truthordare.addplayers.adapter.TruthOrDareAddPlayersAdapter
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersAddButtonViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersBottomViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersSwitchViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersTextFieldViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TruthOrDareAddPlayersFragment :
    AbsViewBindingFragment<FragmentTruthOrDareAddPlayersBinding>(),
    AddPlayersBottomViewHolder.AddPLayersBottomListener,
    AddPlayersAddButtonViewHolder.AddPlayersAddButtonListener,
    AddPlayersTextFieldViewHolder.AddPlayersTextFieldListener,
    AddPlayersSwitchViewHolder.AddPLayerSwitchListener {

    private var adapter: TruthOrDareAddPlayersAdapter? = null
    private val viewModel: TruthOrDareAddPlayersViewModel by viewModels()

    override fun getViewBinding(): FragmentTruthOrDareAddPlayersBinding {
        return FragmentTruthOrDareAddPlayersBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData()

        adapter = TruthOrDareAddPlayersAdapter(
            this,
            this,
            this,
            this
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = this@TruthOrDareAddPlayersFragment.adapter
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                val (playerList, playerSettings) = uiState

                adapter?.setupData(playerList, playerSettings)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun redirectToGame() {
        val directions = TruthOrDareAddPlayersFragmentDirections
            .actionTruthOrDareAddPlayersFragmentToTruthOrDareFragment()
        safeNavigation(directions)
    }

    override fun displayError() {
        (requireActivity() as HomeActivity).showFeedback(
            requireContext(),
            getString(R.string.tod_error_player_already_exist)
        )
    }

    override fun addPlayer(newPlayer: TruthOrDarePlayer) {
        viewModel.addPlayer(newPlayer)
    }

    override fun removePlayer(player: TruthOrDarePlayer) {
        viewModel.deletePlayer(player.id)

    }

    override fun backToGame() {
        redirectToGame()
    }

    override fun onSwitchClicked(value: Boolean) {
        viewModel.setPlayerSettings(value)
    }
}