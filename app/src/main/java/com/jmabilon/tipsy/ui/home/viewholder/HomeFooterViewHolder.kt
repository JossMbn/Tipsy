package com.jmabilon.tipsy.ui.home.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.BuildConfig
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.ItemHomeFooterBinding

class HomeFooterViewHolder(val binding: ItemHomeFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context) {
        binding.appVersion.text = context.resources.getString(
            R.string.app_version,
            BuildConfig.VERSION_NAME
        )
    }
}