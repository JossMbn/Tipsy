package com.jmabilon.tipsy.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.ItemHomeGameCardBinding
import com.jmabilon.tipsy.ui.home.HomeCardTypeEnum
import com.jmabilon.tipsy.ui.home.HomeItemViewPresentation

class HomeGameCardViewHolder(val binding: ItemHomeGameCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeItemViewPresentation, listener: HomeGameCardListener) {
        item.cardTitle?.let { title ->
            binding.cardTitle.text = title
        }
        item.cardDescription?.let { description ->
            binding.cardDescription.text = description
        }
        item.cardIcon?.let { icon ->
            binding.cardIcon.setImageDrawable(icon)
        }
        binding.root.setOnClickListener {
            listener.onCardClick(item.gameType)
        }
    }

    interface HomeGameCardListener {
        fun onCardClick(type: HomeCardTypeEnum)
    }
}