package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersTextFieldBinding
import com.jmabilon.tipsy.extensions.android.hideKeyboard
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayerItemViewPresentation

class AddPlayersTextFieldViewHolder(val binding: ItemTruthOrDareAddPlayersTextFieldBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: AddPlayerItemViewPresentation,
        context: Context,
        view: View,
        listener: AddPlayersTextFieldListener
    ) {
        item.player?.let {
            binding.playerField.text = item.player?.playerName
        }

        binding.removeButton.setOnClickListener {
            item.playerPosition?.let { position ->
                item.player?.let { player ->
                    listener.onRemoveFromAdapterClicked(player, position)
                    binding.playerField.clearFocus()
                    context.hideKeyboard(view)
                }
            }
        }
    }

    interface AddPlayersTextFieldListener {
        fun onRemoveFromAdapterClicked(player: TruthOrDarePlayer, position: Int)
    }
}