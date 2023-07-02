package com.jmabilon.tipsy.ui.home.viewholder

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.ItemHomeAdsBinding
import com.jmabilon.tipsy.extensions.android.getBaseApplication

class HomeAdsViewHolder(val binding: ItemHomeAdsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(activity: Activity) {
        activity.getBaseApplication()?.adRequest?.let {
            binding.adView.loadAd(it)
        }
    }
}