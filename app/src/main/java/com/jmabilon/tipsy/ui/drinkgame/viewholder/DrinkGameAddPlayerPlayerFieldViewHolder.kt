package com.jmabilon.tipsy.ui.drinkgame.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import com.jmabilon.tipsy.databinding.ItemDrinkGameAddPlayersPlayerFieldBinding
import com.jmabilon.tipsy.ui.drinkgame.DrinkGameAddPlayersItemViewPresentation

class DrinkGameAddPlayerPlayerFieldViewHolder(val binding: ItemDrinkGameAddPlayersPlayerFieldBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: DrinkGameAddPlayersItemViewPresentation,
        listener: DrinkGameAddPLayerPlayerFieldListener
    ) {
        binding.playerField.text = item.player?.playerName.toString()

        binding.removeButton.setOnClickListener {
            item.player?.let { player ->
                listener.removePlayer(player)
            }
        }
    }

    interface DrinkGameAddPLayerPlayerFieldListener {
        fun removePlayer(player: DrinkGamePlayer)
    }
}