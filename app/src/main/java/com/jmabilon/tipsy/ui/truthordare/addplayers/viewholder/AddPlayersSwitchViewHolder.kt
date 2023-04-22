package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersToggleBinding
import com.jmabilon.tipsy.helper.PrefHelper

class AddPlayersSwitchViewHolder(val binding: ItemTruthOrDareAddPlayersToggleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.playOptionSwitch.isChecked = PrefHelper.getTodPlayerSetting()

        binding.playOptionSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> PrefHelper.setTodPlayerSetting(true)
                false -> PrefHelper.setTodPlayerSetting(false)
            }
        }
    }
}