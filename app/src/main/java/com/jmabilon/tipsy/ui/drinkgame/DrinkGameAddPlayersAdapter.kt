package com.jmabilon.tipsy.ui.drinkgame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import com.jmabilon.tipsy.databinding.ItemDrinkGameAddPlayersEditTextBinding
import com.jmabilon.tipsy.databinding.ItemDrinkGameAddPlayersPlayerFieldBinding
import com.jmabilon.tipsy.databinding.ItemDrinkGameAddPlayersTitleFieldBinding
import com.jmabilon.tipsy.ui.drinkgame.viewholder.DrinkGameAddPlayerEditTextViewHolder
import com.jmabilon.tipsy.ui.drinkgame.viewholder.DrinkGameAddPlayerPlayerFieldViewHolder
import com.jmabilon.tipsy.ui.drinkgame.viewholder.DrinkGameAddPlayerTitleFieldViewHolder

class DrinkGameAddPlayersAdapter(
    private val drinkGameAddPlayerEditTextListener: DrinkGameAddPlayerEditTextViewHolder.DrinkGameAddPLayerEditTextListener,
    private val drinkGameAddPLayerPlayerFieldListener: DrinkGameAddPlayerPlayerFieldViewHolder.DrinkGameAddPLayerPlayerFieldListener
) : ListAdapter<DrinkGameAddPlayersItemViewPresentation, RecyclerView.ViewHolder>(DiffCallBack()) {

    fun initData(
        playerList: List<DrinkGamePlayer>?
    ) {
        val listLocal = mutableListOf<DrinkGameAddPlayersItemViewPresentation>()
        val playersNameList = mutableListOf<String>()

        playerList?.forEach { player ->
            playersNameList.add(player.playerName.toString())
        }

        listLocal.add(
            DrinkGameAddPlayersItemViewPresentation(
                type = DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TITLE_FIELD
            )
        )

        listLocal.add(
            DrinkGameAddPlayersItemViewPresentation(
                type = DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_EDIT_TEXT,
                playersNameList = playersNameList
            )
        )

        playerList?.forEach { player ->
            listLocal.add(
                DrinkGameAddPlayersItemViewPresentation(
                    type = DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_PLAYER_FIELD,
                    player = player
                )
            )
        }

        this.submitList(
            listLocal.toList()
        )
    }

    fun addPlayer(newPlayer: DrinkGamePlayer) {
        val listLocal = mutableListOf<DrinkGameAddPlayersItemViewPresentation>()

        for (item in currentList.toList()) {
            listLocal.add(item.copy())
        }

        listLocal.add(
            DrinkGameAddPlayersItemViewPresentation(
                type = DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_PLAYER_FIELD,
                player = newPlayer
            )
        )

        this.submitList(
            listLocal.toList()
        )
    }

    fun removePlayer(playerName: String) {
        val listLocal = mutableListOf<DrinkGameAddPlayersItemViewPresentation>()

        for (item in currentList.toList()) {
            if (item.type == DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_PLAYER_FIELD) {
                if (item.player?.playerName != playerName) {
                    listLocal.add(item.copy())
                }
            } else {
                listLocal.add(item.copy())
            }
        }

        this.submitList(
            listLocal.toList()
        )
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TITLE_FIELD.viewType -> {
                val binding =
                    ItemDrinkGameAddPlayersTitleFieldBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                DrinkGameAddPlayerTitleFieldViewHolder(binding)
            }

            DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_EDIT_TEXT.viewType -> {
                val binding =
                    ItemDrinkGameAddPlayersEditTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                DrinkGameAddPlayerEditTextViewHolder(binding)
            }

            else -> {
                val binding =
                    ItemDrinkGameAddPlayersPlayerFieldBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                DrinkGameAddPlayerPlayerFieldViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder.itemViewType) {
                DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TITLE_FIELD.viewType -> {
                    (holder as DrinkGameAddPlayerTitleFieldViewHolder).bind()
                }

                DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_EDIT_TEXT.viewType -> {
                    (holder as DrinkGameAddPlayerEditTextViewHolder).bind(
                        item,
                        drinkGameAddPlayerEditTextListener
                    )
                }

                DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_PLAYER_FIELD.viewType -> {
                    (holder as DrinkGameAddPlayerPlayerFieldViewHolder).bind(
                        item,
                        drinkGameAddPLayerPlayerFieldListener
                    )
                }

                else -> {
                    // do nothing
                }
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<DrinkGameAddPlayersItemViewPresentation>() {

        override fun areItemsTheSame(
            oldItem: DrinkGameAddPlayersItemViewPresentation,
            newItem: DrinkGameAddPlayersItemViewPresentation
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DrinkGameAddPlayersItemViewPresentation,
            newItem: DrinkGameAddPlayersItemViewPresentation
        ): Boolean {
            return if (oldItem.type != newItem.type) {
                false
            } else {
                when (oldItem.type) {
                    DrinkGameAddPlayersItemViewEnum.ITEM_ADD_PLAYERS_PLAYER_FIELD -> {
                        oldItem.player?.playerName == newItem.player?.playerName
                    }

                    else -> {
                        oldItem == newItem
                    }
                }
            }
        }
    }
}