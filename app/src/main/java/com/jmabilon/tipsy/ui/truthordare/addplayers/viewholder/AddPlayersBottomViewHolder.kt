package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersBottomBinding

class AddPlayersBottomViewHolder(val binding: ItemTruthOrDareAddPlayersBottomBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: AddPLayersBottomListener) {
        binding.closeButton.setOnClickListener {
            listener.onBackClicked()
        }
    }

    interface AddPLayersBottomListener {
        fun onBackClicked()
    }
}