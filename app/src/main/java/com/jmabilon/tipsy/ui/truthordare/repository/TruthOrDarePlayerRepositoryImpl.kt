package com.jmabilon.tipsy.ui.truthordare.repository

import com.jmabilon.tipsy.data.room.dao.TruthOrDarePlayerDao
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import javax.inject.Inject

class TruthOrDarePlayerRepositoryImpl @Inject constructor(
    private val truthOrDarePlayerDao: TruthOrDarePlayerDao
) : ITruthOrDarePlayerRepository {

    override fun getAllPlayers(): List<TruthOrDarePlayer> = truthOrDarePlayerDao.getAll()

    override fun getAllPlayersName(): List<String> = truthOrDarePlayerDao.getAllPlayersName()

    override suspend fun addPlayer(player: TruthOrDarePlayer) {
        truthOrDarePlayerDao.insertPlayer(player)
    }

    override suspend fun deletePlayer(playerId: Int) {
        truthOrDarePlayerDao.deletePlayer(playerId)
    }

    override suspend fun deletePlayerFromList(playersIdList: List<Int>) {
        truthOrDarePlayerDao.deletePlayerFromList(playersIdList)
    }

    override suspend fun updatePlayer(playerId: Int, newPlayerName: String) {
        truthOrDarePlayerDao.updatePlayer(playerId, newPlayerName)
    }
}