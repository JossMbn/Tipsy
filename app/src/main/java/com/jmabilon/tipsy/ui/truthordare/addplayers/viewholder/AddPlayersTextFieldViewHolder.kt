package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersTextFieldBinding
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayerItemViewPresentation

class AddPlayersTextFieldViewHolder(val binding: ItemTruthOrDareAddPlayersTextFieldBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: AddPlayerItemViewPresentation,
        listener: AddPlayersTextFieldListener
    ) {
        item.player?.let {
            binding.playerField.text = item.player?.playerName
        }

        binding.removeButton.setOnClickListener {
            item.player?.let { player ->
                listener.removePlayer(player)
                binding.playerField.clearFocus()
            }
        }
    }

    interface AddPlayersTextFieldListener {
        fun removePlayer(player: TruthOrDarePlayer)
    }
}