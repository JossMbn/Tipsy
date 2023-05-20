package com.jmabilon.tipsy.ui.drinkgame.viewholder

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import com.jmabilon.tipsy.databinding.ItemDrinkGameAddPlayersEditTextBinding
import com.jmabilon.tipsy.extensions.android.cleanPlayerName
import com.jmabilon.tipsy.ui.drinkgame.DrinkGameAddPlayersItemViewPresentation

class DrinkGameAddPlayerEditTextViewHolder(val binding: ItemDrinkGameAddPlayersEditTextBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: DrinkGameAddPlayersItemViewPresentation,
        listener: DrinkGameAddPLayerEditTextListener
    ) {
        binding.addPlayerField.setOnEditorActionListener { textView, actionId, event ->
            if (
                (event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) &&
                !textView.text.isNullOrEmpty()
            ) {
                if (item.playersNameList?.contains(textView.text.toString().cleanPlayerName()) == true) {
                    listener.displayError()
                } else {
                    listener.addPlayer(
                        DrinkGamePlayer(playerName = textView.text.toString().cleanPlayerName())
                    )
                    binding.addPlayerField.text = null
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    interface DrinkGameAddPLayerEditTextListener {
        fun addPlayer(newPlayer: DrinkGamePlayer)
        fun displayError()
    }
}