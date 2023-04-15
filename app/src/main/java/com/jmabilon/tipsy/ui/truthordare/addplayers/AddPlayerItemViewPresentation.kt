package com.jmabilon.tipsy.ui.truthordare.addplayers

enum class AddPlayersItemViewEnum(val viewType: Int) {
    ITEM_ADD_PLAYERS_HEADER(0),
    ITEM_ADD_PLAYERS_TOGGLE(1),
    ITEM_ADD_PLAYERS_TEXT_FIELD(2),
    ITEM_ADD_PLAYERS_ADD_BUTTON(3),
    ITEM_ADD_PLAYERS_BOTTOM(4)
}

data class AddPlayerItemViewPresentation(
    var type: AddPlayersItemViewEnum,
    var playerName: String? = null,
    var playerPosition: Int? = null,
    var screenDensity: Float? = null
)
