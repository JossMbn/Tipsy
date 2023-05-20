package com.jmabilon.tipsy.ui.truthordare.addplayers

import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

enum class AddPlayersItemViewEnum(val viewType: Int) {
    ITEM_ADD_PLAYERS_HEADER(0),
    ITEM_ADD_PLAYERS_SWITCH(1),
    ITEM_ADD_PLAYERS_TEXT_FIELD(2),
    ITEM_ADD_PLAYERS_ADD_BUTTON(3),
    ITEM_ADD_PLAYERS_BOTTOM(4)
}

data class AddPlayerItemViewPresentation(
    var type: AddPlayersItemViewEnum,
    var player: TruthOrDarePlayer? = null,
    var playerPosition: Int? = null,
    var playerSettings: Boolean = false,
    var playersNameList: MutableList<String>? = null
)
