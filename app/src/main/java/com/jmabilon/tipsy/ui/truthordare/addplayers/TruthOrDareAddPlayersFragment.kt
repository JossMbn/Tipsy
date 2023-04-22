package com.jmabilon.tipsy.ui.truthordare.addplayers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareAddPlayersBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import com.jmabilon.tipsy.ui.truthordare.addplayers.adapter.TruthOrDareAddPLayersAdapter
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersAddButtonViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersBottomViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersTextFieldViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TruthOrDareAddPlayersFragment :
    AbsViewBindingFragment<FragmentTruthOrDareAddPlayersBinding>(),
    AddPlayersBottomViewHolder.AddPLayersBottomListener,
    AddPlayersAddButtonViewHolder.AddPlayersAddButtonListener,
    AddPlayersTextFieldViewHolder.AddPlayersTextFieldListener {

    private var adapter: TruthOrDareAddPLayersAdapter? = null
    private val viewModel: TruthOrDareAddPlayersViewModel by viewModels()

    override fun getViewBinding(): FragmentTruthOrDareAddPlayersBinding {
        return FragmentTruthOrDareAddPlayersBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllPlayers()

        adapter = TruthOrDareAddPLayersAdapter(
            requireContext(),
            view,
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
                val (playerList) = uiState

                adapter?.initData(playerList, resources.displayMetrics.density)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onBackClicked() {
        val directions = TruthOrDareAddPlayersFragmentDirections
            .actionTruthOrDareAddPlayersFragmentToTruthOrDareFragment()
        safeNavigation(directions)
    }

    override fun onAddButtonClicked(newPlayer: TruthOrDarePlayer) {
        adapter?.updateTextFieldsList(newPlayer)
        viewModel.addPlayer(newPlayer)
    }

    override fun onRemoveClicked(player: TruthOrDarePlayer, position: Int) {
        adapter?.removeTextField(position)
        viewModel.deletePlayer(player)
    }

    override fun onUpdateTextFieldClicked(
        playerPosition: Int,
        player: TruthOrDarePlayer,
        newPlayerName: String
    ) {
        if (newPlayerName.isEmpty()) {
            adapter?.removeTextField(playerPosition)
            viewModel.deletePlayer(player)
        } else {
            viewModel.updatePlayer(player.id, newPlayerName)
        }
    }
}