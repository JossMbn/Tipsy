package com.jmabilon.tipsy.ui.drinkgame

import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer

data class DrinkGameAddPlayersUiState(
    val playerList: List<DrinkGamePlayer>? = null
)