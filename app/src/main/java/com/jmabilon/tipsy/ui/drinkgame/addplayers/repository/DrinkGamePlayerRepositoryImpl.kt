package com.jmabilon.tipsy.ui.drinkgame.addplayers.repository

import com.jmabilon.tipsy.data.room.dao.DrinkGamePlayerDao
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import javax.inject.Inject

class DrinkGamePlayerRepositoryImpl @Inject constructor(
    private val drinkGamePlayerDao: DrinkGamePlayerDao
) : IDrinkGamePlayerRepository {

    override fun getAllPlayers(): List<DrinkGamePlayer> = drinkGamePlayerDao.getAll()

    override fun getAllPlayersName(): List<String> = drinkGamePlayerDao.getAllPlayersName()

    override suspend fun addPlayer(player: DrinkGamePlayer) {
        drinkGamePlayerDao.insertPlayer(player)
    }

    override suspend fun deletePlayer(playerId: Int) {
        drinkGamePlayerDao.deletePlayer(playerId)
    }

    override suspend fun deletePlayerFromList(playersIdList: List<Int>) {
        drinkGamePlayerDao.deletePlayerFromList(playersIdList)
    }

    override suspend fun updatePlayer(playerId: Int, newPlayerName: String) {
        drinkGamePlayerDao.updatePlayer(playerId, newPlayerName)
    }
}