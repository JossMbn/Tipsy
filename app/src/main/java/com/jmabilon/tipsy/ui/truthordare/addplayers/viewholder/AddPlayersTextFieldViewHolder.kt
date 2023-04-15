package com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersTextFieldBinding
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayerItemViewPresentation

class AddPlayersTextFieldViewHolder(val binding: ItemTruthOrDareAddPlayersTextFieldBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: AddPlayerItemViewPresentation,
        context: Context,
        listener: AddPlayersTextFieldListener
    ) {
        item.playerName?.let {
            binding.playerField.setText(item.playerName)
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

        binding.removeButton.setOnClickListener {
            item.playerPosition?.let { position ->
                listener.onRemoveClicked(position)
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
        fun onRemoveClicked(position: Int)
    }
}