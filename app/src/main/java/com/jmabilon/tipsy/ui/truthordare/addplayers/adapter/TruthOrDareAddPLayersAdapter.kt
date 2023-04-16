package com.jmabilon.tipsy.ui.truthordare.addplayers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.*
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayerItemViewPresentation
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayersItemViewEnum
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.*

class TruthOrDareAddPLayersAdapter(
    private val context: Context,
    private val view: View,
    private val addPLayersBottomListener: AddPlayersBottomViewHolder.AddPLayersBottomListener,
    private val addPLayersAddButtonListener: AddPlayersAddButtonViewHolder.AddPlayersAddButtonListener,
    private val addPlayersTextFieldListener: AddPlayersTextFieldViewHolder.AddPlayersTextFieldListener
) :
    ListAdapter<AddPlayerItemViewPresentation, RecyclerView.ViewHolder>(DiffCallBack()) {

    fun initData(
        playerList: List<TruthOrDarePlayer>?,
        screenDensity: Float
    ) {
        val listLocal = mutableListOf<AddPlayerItemViewPresentation>()
        var playerPosition = 0

        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_HEADER
            )
        )
        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TOGGLE
            )
        )

        if (playerList.isNullOrEmpty()) {
            listLocal.add(
                AddPlayerItemViewPresentation(
                    type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD,
                    player = null,
                    playerPosition = 0,
                    screenDensity = screenDensity
                )
            )
            listLocal.add(
                AddPlayerItemViewPresentation(
                    type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD,
                    player = null,
                    playerPosition = 1,
                    screenDensity = screenDensity
                )
            )
        } else {
            for (item in playerList) {
                listLocal.add(
                    AddPlayerItemViewPresentation(
                        type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD,
                        player = item,
                        playerPosition = playerPosition,
                        screenDensity = screenDensity
                    )
                )
                playerPosition += 1
            }
        }

        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_ADD_BUTTON
            )
        )

        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_BOTTOM
            )
        )

        this.submitList(
            listLocal.toList()
        )
    }

    fun updateTextFieldsList(
        newPlayer: TruthOrDarePlayer
    ) {
        val listLocal = mutableListOf<AddPlayerItemViewPresentation>()
        var playerPosition = 0

        for (item in currentList.toList()) {
            val copyItem = item.copy()

            if (copyItem.type == AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD) {
                copyItem.playerPosition?.let { position ->
                    playerPosition = position
                }
            }
            if (copyItem.type == AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_ADD_BUTTON) {
                listLocal.add(
                    AddPlayerItemViewPresentation(
                        type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD,
                        player = newPlayer,
                        playerPosition = playerPosition + 1
                    )
                )
                listLocal.add(copyItem)
            } else {
                listLocal.add(copyItem)
            }
        }

        this.submitList(
            listLocal.toList()
        )
    }

    fun removeTextField(position: Int) {
        val listLocal = mutableListOf<AddPlayerItemViewPresentation>()

        for (item in currentList.toList()) {
            val copyItem = item.copy()

            if (copyItem.type == AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD) {
                if (copyItem.playerPosition != position) {
                    listLocal.add(copyItem)
                }
            } else {
                listLocal.add(copyItem)
            }
        }

        this.submitList(
            listLocal.toList()
        )
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_HEADER.viewType -> {
                val binding =
                    ItemTruthOrDareAddPlayersHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                AddPlayersHeaderViewHolder(binding)
            }
            AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TOGGLE.viewType -> {
                val binding =
                    ItemTruthOrDareAddPlayersToggleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                AddPlayersToggleViewHolder(binding)
            }
            AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD.viewType -> {
                val binding =
                    ItemTruthOrDareAddPlayersTextFieldBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                AddPlayersTextFieldViewHolder(binding)
            }
            AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_ADD_BUTTON.viewType -> {
                val binding =
                    ItemTruthOrDareAddPlayersAddButtonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                AddPlayersAddButtonViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemTruthOrDareAddPlayersBottomBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                AddPlayersBottomViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder.itemViewType) {
                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_HEADER.viewType -> {
                    (holder as AddPlayersHeaderViewHolder).bind()
                }
                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TOGGLE.viewType -> {
                    (holder as AddPlayersToggleViewHolder).bind(item)
                }
                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD.viewType -> {
                    (holder as AddPlayersTextFieldViewHolder).bind(
                        item,
                        context,
                        view,
                        addPlayersTextFieldListener
                    )
                }
                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_ADD_BUTTON.viewType -> {
                    (holder as AddPlayersAddButtonViewHolder).bind(addPLayersAddButtonListener)
                }
                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_BOTTOM.viewType -> {
                    (holder as AddPlayersBottomViewHolder).bind(addPLayersBottomListener)
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<AddPlayerItemViewPresentation>() {

        override fun areItemsTheSame(
            oldItem: AddPlayerItemViewPresentation,
            newItem: AddPlayerItemViewPresentation
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AddPlayerItemViewPresentation,
            newItem: AddPlayerItemViewPresentation
        ): Boolean {
            return if (oldItem.type != newItem.type) {
                false
            } else {
                when (oldItem.type) {
                    AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD -> {
                        oldItem.player == newItem.player && oldItem.playerPosition == newItem.playerPosition
                    }
                    else -> {
                        oldItem == newItem
                    }
                }
            }
        }
    }
}