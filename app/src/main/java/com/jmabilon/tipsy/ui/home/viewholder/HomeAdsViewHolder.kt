package com.jmabilon.tipsy.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.jmabilon.tipsy.databinding.ItemHomeAdsBinding

class HomeAdsViewHolder(val binding: ItemHomeAdsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.adView.loadAd(AdRequest.Builder().build())
    }
}