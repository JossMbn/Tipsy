package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersToggleBinding
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayerItemViewPresentation

class AddPlayersSwitchViewHolder(val binding: ItemTruthOrDareAddPlayersToggleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AddPlayerItemViewPresentation, listener: AddPLayerSwitchListener) {
        binding.playOptionSwitch.isChecked = item.playerSettings

        binding.playOptionSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> listener.onSwitchClicked(true)
                false -> listener.onSwitchClicked(false)
            }
        }
    }

    interface AddPLayerSwitchListener {
        fun onSwitchClicked(value: Boolean)
    }
}