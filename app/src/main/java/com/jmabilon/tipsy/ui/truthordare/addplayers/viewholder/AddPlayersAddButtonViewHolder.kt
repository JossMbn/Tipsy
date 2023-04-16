package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersAddButtonBinding

class AddPlayersAddButtonViewHolder(val binding: ItemTruthOrDareAddPlayersAddButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: AddPlayersAddButtonListener) {
        binding.addPlayerField.setOnEditorActionListener { textView, actionId, event ->
            if (
                (event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) &&
                !textView.text.isNullOrEmpty()
            ) {
                listener.onAddButtonClicked(
                    TruthOrDarePlayer(playerName = textView.text.toString())
                )
                binding.addPlayerField.text = null
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    interface AddPlayersAddButtonListener {
        fun onAddButtonClicked(newPlayer: TruthOrDarePlayer)
    }
}