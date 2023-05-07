package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersAddButtonBinding
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayerItemViewPresentation

class AddPlayersAddButtonViewHolder(val binding: ItemTruthOrDareAddPlayersAddButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AddPlayerItemViewPresentation, listener: AddPlayersAddButtonListener) {
        binding.addPlayerField.setOnEditorActionListener { textView, actionId, event ->
            if (
                (event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) &&
                !textView.text.isNullOrEmpty()
            ) {
                if (item.playersNameList?.contains(cleanPlayerName(textView.text.toString())) == true) {
                    listener.displayError()
                } else {
                    listener.onAddButtonClicked(
                        TruthOrDarePlayer(
                            playerName = cleanPlayerName(textView.text.toString())
                        )
                    )
                    binding.addPlayerField.text = null
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun cleanPlayerName(name: String): String {
        return name
            .replace("\\s".toRegex(), "")
            .replaceFirstChar { it.uppercase() }
    }

    interface AddPlayersAddButtonListener {
        fun displayError()
        fun onAddButtonClicked(newPlayer: TruthOrDarePlayer)
    }
}