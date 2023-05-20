package com.jmabilon.tipsy.ui.drinkgame

import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer

enum class DrinkGameAddPlayersItemViewEnum(val viewType: Int) {
    ITEM_ADD_PLAYERS_TITLE_FIELD(0),
    ITEM_ADD_PLAYERS_EDIT_TEXT(1),
    ITEM_ADD_PLAYERS_PLAYER_FIELD(2)
}

data class DrinkGameAddPlayersItemViewPresentation(
    var type: DrinkGameAddPlayersItemViewEnum,
    var player: DrinkGamePlayer? = null,
    var playersNameList: List<String>? = null
)
