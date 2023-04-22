package com.jmabilon.tipsy.data.repository

import com.jmabilon.tipsy.data.room.dao.TruthOrDarePlayerDao
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import javax.inject.Inject

class TruthOrDarePLayerRepositoryImpl @Inject constructor(
    private val truthOrDarePlayerDao: TruthOrDarePlayerDao
) : ITruthOrDarePlayerRepository {

    override fun getAllPlayers(): List<TruthOrDarePlayer> = truthOrDarePlayerDao.getAll()

    override fun getAllPlayersName(): List<String> = truthOrDarePlayerDao.getAllPlayersName()

    override suspend fun addPlayer(player: TruthOrDarePlayer) {
        truthOrDarePlayerDao.insertPlayer(player)
    }

    override suspend fun deletePlayer(player: TruthOrDarePlayer) {
        truthOrDarePlayerDao.deletePlayer(player)
    }

    override suspend fun updatePlayer(playerId: Int, newPlayerName: String) {
        truthOrDarePlayerDao.updatePlayer(playerId, newPlayerName)
    }
}