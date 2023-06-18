package com.jmabilon.tipsy.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.ItemHomeSectionBinding
import com.jmabilon.tipsy.ui.home.HomeItemViewPresentation

class HomeSectionViewHolder(val binding: ItemHomeSectionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeItemViewPresentation) {
        item.sectionTitle?.let {
            binding.sectionTitle.text = it
        }
    }
}