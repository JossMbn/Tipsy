package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.R
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
            binding.playerField.setText(item.player?.playerName)
        }

        item.playerPosition?.let {
            binding.playerField.hint =
                context.getString(R.string.tod_hint_text_view, (it + 1).toString())
            if (item.playerPosition == 0) {
                item.screenDensity?.let { scale ->
                    val paddingLeft = (30 * scale + 0.5f).toInt()
                    val paddingRight = (30 * scale + 0.5f).toInt()
                    val paddingTop = (20 * scale + 0.5f).toInt()

                    binding.root.setPadding(paddingLeft, paddingTop, paddingRight, 0)
                }
            }
        }

        binding.playerField.setOnEditorActionListener { textView, actionId, event ->
            if (
                (event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE))
            ) {
                item.player?.let { player ->
                    item.playerPosition?.let { position ->
                        listener.onUpdateTextFieldClicked(
                            position,
                            player,
                            textView.text.toString()
                        )
                    }
                }
                textView.clearFocus()
                context.hideKeyboard(view)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.removeButton.setOnClickListener {
            item.playerPosition?.let { position ->
                item.player?.let { player ->
                    listener.onRemoveClicked(player, position)
                    binding.playerField.clearFocus()
                    context.hideKeyboard(view)
                }
            }
        }

        hideRemoveButtonIfNeeded(item)
    }

    private fun hideRemoveButtonIfNeeded(item: AddPlayerItemViewPresentation) {
        if ((item.playerPosition == 0 || item.playerPosition == 1) && binding.playerField.text.isNullOrEmpty()) {
            binding.removeButton.visibility = View.GONE
        } else {
            binding.removeButton.visibility = View.VISIBLE
        }
    }

    interface AddPlayersTextFieldListener {
        fun onRemoveClicked(player: TruthOrDarePlayer, position: Int)
        fun onUpdateTextFieldClicked(
            playerPosition: Int,
            player: TruthOrDarePlayer,
            newPlayerName: String
        )
    }
}