package com.jmabilon.tipsy.ui.drinkgame.addplayers

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import com.jmabilon.tipsy.databinding.FragmentDrinkGameAddPlayersBinding
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.extensions.viewbinding.AbsViewBindingFragment
import com.jmabilon.tipsy.ui.drinkgame.addplayers.viewholder.DrinkGameAddPlayerEditTextViewHolder
import com.jmabilon.tipsy.ui.drinkgame.addplayers.viewholder.DrinkGameAddPlayerPlayerFieldViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DrinkGameAddPlayersFragment :
    AbsViewBindingFragment<FragmentDrinkGameAddPlayersBinding>(),
    DrinkGameAddPlayerEditTextViewHolder.DrinkGameAddPLayerEditTextListener,
    DrinkGameAddPlayerPlayerFieldViewHolder.DrinkGameAddPLayerPlayerFieldListener {

    private var adapter: DrinkGameAddPlayersAdapter? = null
    private val viewModel: DrinkGameAddPlayersViewModel by viewModels()

    override fun getViewBinding(): FragmentDrinkGameAddPlayersBinding {
        return FragmentDrinkGameAddPlayersBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        viewModel.loadData()

        adapter = DrinkGameAddPlayersAdapter(
            this@DrinkGameAddPlayersFragment,
            this@DrinkGameAddPlayersFragment
        )

        binding.backIcon.setOnClickListener {
            viewModel.removePlayer()
            findNavController().popBackStack()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = this@DrinkGameAddPlayersFragment.adapter
        }

        binding.button.setOnClickListener {
            viewModel.removePlayer()
            val directions =
                DrinkGameAddPlayersFragmentDirections.actionDrinkGameAddPlayersFragmentToDrinkGameFragment()
            safeNavigation(directions)
        }

        binding.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            binding.headerTitle.alpha =
                (-1f / binding.recyclerView.computeVerticalScrollOffset()) * 260 + 2
        }
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { uiState ->
                val (playerList) = uiState

                adapter?.initData(playerList)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun addPlayer(newPlayer: DrinkGamePlayer) {
        adapter?.addPlayer(newPlayer)
        viewModel.addPlayer(newPlayer)
    }

    override fun removePlayer(player: DrinkGamePlayer) {
        adapter?.removePlayer(player.playerName.toString())
        viewModel.updatePlayersIdList(player.id)
    }

    override fun displayError() {
        displayError(getString(R.string.tod_error_player_already_exist))
    }
}