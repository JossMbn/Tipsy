package com.jmabilon.tipsy.ui.truthordare.addplayers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersAddButtonBinding
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersBottomBinding
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersHeaderBinding
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersTextFieldBinding
import com.jmabilon.tipsy.databinding.ItemTruthOrDareAddPlayersToggleBinding
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayerItemViewPresentation
import com.jmabilon.tipsy.ui.truthordare.addplayers.AddPlayersItemViewEnum
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersAddButtonViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersBottomViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersHeaderViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersSwitchViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersTextFieldViewHolder

class TruthOrDareAddPlayersAdapter(
    private val addPLayersBottomListener: AddPlayersBottomViewHolder.AddPLayersBottomListener,
    private val addPLayersAddButtonListener: AddPlayersAddButtonViewHolder.AddPlayersAddButtonListener,
    private val addPlayersTextFieldListener: AddPlayersTextFieldViewHolder.AddPlayersTextFieldListener,
    private val addPLayerSwitchListener: AddPlayersSwitchViewHolder.AddPLayerSwitchListener
) :
    ListAdapter<AddPlayerItemViewPresentation, RecyclerView.ViewHolder>(DiffCallBack()) {

    fun setupData(
        playerList: List<TruthOrDarePlayer>?,
        playerSettings: Boolean
    ) {
        val listLocal = mutableListOf<AddPlayerItemViewPresentation>()
        var playerPosition = 0
        val playersNameList = mutableListOf<String>()

        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_HEADER
            )
        )
        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_SWITCH,
                playerSettings = playerSettings
            )
        )

        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_ADD_BUTTON
            )
        )

        playerList?.let {
            for (item in it) {
                item.playerName?.let { name ->
                    playersNameList.add(name)
                }
                listLocal.add(
                    AddPlayerItemViewPresentation(
                        type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD,
                        player = item
                    )
                )
                playerPosition += 1
            }
        }

        listLocal.add(
            AddPlayerItemViewPresentation(
                type = AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_BOTTOM
            )
        )

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

            AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_SWITCH.viewType -> {
                val binding =
                    ItemTruthOrDareAddPlayersToggleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                AddPlayersSwitchViewHolder(binding)
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

                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_SWITCH.viewType -> {
                    (holder as AddPlayersSwitchViewHolder).bind(item, addPLayerSwitchListener)
                }

                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_TEXT_FIELD.viewType -> {
                    (holder as AddPlayersTextFieldViewHolder).bind(
                        item,
                        addPlayersTextFieldListener
                    )
                }

                AddPlayersItemViewEnum.ITEM_ADD_PLAYERS_ADD_BUTTON.viewType -> {
                    (holder as AddPlayersAddButtonViewHolder).bind(
                        addPLayersAddButtonListener
                    )
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
            return oldItem == newItem
        }
    }
}