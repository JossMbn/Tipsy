package com.jmabilon.tipsy.ui.truthordare.repository

import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

interface ITruthOrDarePlayerRepository {

    fun getAllPlayers(): List<TruthOrDarePlayer>

    fun getAllPlayersName(): List<String>

    suspend fun addPlayer(player: TruthOrDarePlayer)

    suspend fun deletePlayer(playerId: Int)

    suspend fun deletePlayerFromList(playersIdList: List<Int>)

    suspend fun updatePlayer(playerId: Int, newPlayerName: String)
}