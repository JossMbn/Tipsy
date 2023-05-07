package com.jmabilon.tipsy.ui.truthordare.repository

import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

interface ITruthOrDarePlayerRepository {

    fun getAllPlayers(): List<TruthOrDarePlayer>

    fun getAllPlayersName(): List<String>

    suspend fun addPlayer(player: TruthOrDarePlayer)

    suspend fun deletePlayer(player: TruthOrDarePlayer)

    suspend fun updatePlayer(playerId: Int, newPlayerName: String)
}