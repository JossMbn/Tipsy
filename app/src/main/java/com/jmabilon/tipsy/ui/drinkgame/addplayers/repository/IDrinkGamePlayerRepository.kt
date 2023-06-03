package com.jmabilon.tipsy.ui.drinkgame.addplayers.repository

import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer

interface IDrinkGamePlayerRepository {

    fun getAllPlayers(): List<DrinkGamePlayer>

    fun getAllPlayersName(): List<String>

    suspend fun addPlayer(player: DrinkGamePlayer)

    suspend fun deletePlayer(playerId: Int)

    suspend fun deletePlayerFromList(playersIdList: List<Int>)

    suspend fun updatePlayer(playerId: Int, newPlayerName: String)
}