package com.jmabilon.tipsy.ui.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.databinding.ItemHomeAdsBinding
import com.jmabilon.tipsy.databinding.ItemHomeFooterBinding
import com.jmabilon.tipsy.databinding.ItemHomeGameCardBinding
import com.jmabilon.tipsy.databinding.ItemHomeHeaderBinding
import com.jmabilon.tipsy.databinding.ItemHomeSectionBinding
import com.jmabilon.tipsy.ui.home.HomeCardTypeEnum
import com.jmabilon.tipsy.ui.home.HomeItemViewEnum
import com.jmabilon.tipsy.ui.home.HomeItemViewPresentation
import com.jmabilon.tipsy.ui.home.viewholder.HomeAdsViewHolder
import com.jmabilon.tipsy.ui.home.viewholder.HomeFooterViewHolder
import com.jmabilon.tipsy.ui.home.viewholder.HomeGameCardViewHolder
import com.jmabilon.tipsy.ui.home.viewholder.HomeHeaderViewHolder
import com.jmabilon.tipsy.ui.home.viewholder.HomeSectionViewHolder

class HomeAdapter(
    private val context: Context,
    private val homeGameCardListener: HomeGameCardViewHolder.HomeGameCardListener
) :
    ListAdapter<HomeItemViewPresentation, RecyclerView.ViewHolder>(DiffCallBack()) {

    fun initData() {
        val listLocal = mutableListOf<HomeItemViewPresentation>()
        val popularGameList = listOf(
            CardData(
                HomeCardTypeEnum.DRINK_GAME,
                "Drink Game",
                "For an unforgettable evening !",
                ContextCompat.getDrawable(context, R.drawable.ico_bottles)
            ),
            CardData(
                HomeCardTypeEnum.TRUTH_OR_DARE,
                "Truth or Dare",
                "The best game to get to know your friends better !",
                ContextCompat.getDrawable(context, R.drawable.ico_heart)
            )
        )
        val softGameList = listOf(
            CardData(
                HomeCardTypeEnum.DILEMMA,
                "Dilemma",
                "What would you do if you need to choose between two dilemmas ?",
                ContextCompat.getDrawable(context, R.drawable.ic_question_mark)
            ),
            CardData(
                HomeCardTypeEnum.MOST_LIKELY_TO,
                "Most likely to",
                "The best game to get to know your friends better !",
                ContextCompat.getDrawable(context, R.drawable.ico_flash_bolt)
            ),
            CardData(
                HomeCardTypeEnum.NEVER_HAVE_I_EVER,
                "Never have I ever",
                "The best game to get to know your friends better !",
                ContextCompat.getDrawable(context, R.drawable.ico_cross)
            )
        )

        listLocal.add(
            HomeItemViewPresentation(
                type = HomeItemViewEnum.HOME_HEADER_ITEM
            )
        )

        listLocal.add(
            HomeItemViewPresentation(
                type = HomeItemViewEnum.HOME_SECTION_ITEM,
                sectionTitle = context.resources.getString(R.string.home_popular_section)
            )
        )

        popularGameList.forEach { item ->
            listLocal.add(
                HomeItemViewPresentation(
                    type = HomeItemViewEnum.HOME_GAME_CARD_ITEM,
                    gameType = item.type,
                    cardTitle = item.title,
                    cardDescription = item.description,
                    cardIcon = item.icon
                )
            )
        }

        listLocal.add(
            HomeItemViewPresentation(
                type = HomeItemViewEnum.HOME_ADS_ITEM
            )
        )

        listLocal.add(
            HomeItemViewPresentation(
                type = HomeItemViewEnum.HOME_SECTION_ITEM,
                sectionTitle = context.resources.getString(R.string.home_soft_section)
            )
        )

        softGameList.forEach { item ->
            listLocal.add(
                HomeItemViewPresentation(
                    type = HomeItemViewEnum.HOME_GAME_CARD_ITEM,
                    gameType = item.type,
                    cardTitle = item.title,
                    cardDescription = item.description,
                    cardIcon = item.icon
                )
            )
        }

        listLocal.add(
            HomeItemViewPresentation(
                type = HomeItemViewEnum.HOME_FOOTER_ITEM
            )
        )

        this.submitList(
            listLocal.toList()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HomeItemViewEnum.HOME_HEADER_ITEM.viewType -> {
                val binding = ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeHeaderViewHolder(binding)
            }

            HomeItemViewEnum.HOME_SECTION_ITEM.viewType -> {
                val binding = ItemHomeSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeSectionViewHolder(binding)
            }

            HomeItemViewEnum.HOME_GAME_CARD_ITEM.viewType -> {
                val binding = ItemHomeGameCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeGameCardViewHolder(binding)
            }

            HomeItemViewEnum.HOME_ADS_ITEM.viewType -> {
                val binding = ItemHomeAdsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeAdsViewHolder(binding)
            }

            else -> {
                val binding = ItemHomeFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeFooterViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder.itemViewType) {
                HomeItemViewEnum.HOME_HEADER_ITEM.viewType -> {
                    (holder as HomeHeaderViewHolder).bind()
                }

                HomeItemViewEnum.HOME_SECTION_ITEM.viewType -> {
                    (holder as HomeSectionViewHolder).bind(item)
                }

                HomeItemViewEnum.HOME_GAME_CARD_ITEM.viewType -> {
                    (holder as HomeGameCardViewHolder).bind(item, homeGameCardListener)
                }

                HomeItemViewEnum.HOME_ADS_ITEM.viewType -> {
                    (holder as HomeAdsViewHolder).bind()
                }

                HomeItemViewEnum.HOME_FOOTER_ITEM.viewType -> {
                    (holder as HomeFooterViewHolder).bind(context)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.viewType

    class DiffCallBack : DiffUtil.ItemCallback<HomeItemViewPresentation>() {

        override fun areItemsTheSame(
            oldItem: HomeItemViewPresentation,
            newItem: HomeItemViewPresentation
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HomeItemViewPresentation,
            newItem: HomeItemViewPresentation
        ): Boolean {
            return oldItem == newItem
        }
    }

    data class CardData(
        val type: HomeCardTypeEnum = HomeCardTypeEnum.DRINK_GAME,
        val title: String? = null,
        val description: String? = null,
        val icon: Drawable? = null
    )
}