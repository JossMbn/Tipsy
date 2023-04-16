package com.jmabilon.tipsy.data.repository

import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

interface ITruthOrDarePlayerRepository {

    fun getAllPLayers(): List<TruthOrDarePlayer>

    suspend fun addPlayer(player: TruthOrDarePlayer)

    suspend fun deletePlayer(player: TruthOrDarePlayer)
}