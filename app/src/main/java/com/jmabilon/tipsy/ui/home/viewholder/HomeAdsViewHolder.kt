package com.jmabilon.tipsy.ui.home.viewholder

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.ItemHomeAdsBinding
import com.jmabilon.tipsy.extensions.android.getAbsActivity

class HomeAdsViewHolder(val binding: ItemHomeAdsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(activity: Activity) {
        activity.getAbsActivity()?.adRequest?.let {
            binding.adView.loadAd(it)
        }
    }
}