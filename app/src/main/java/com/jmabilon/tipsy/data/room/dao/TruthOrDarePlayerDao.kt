package com.jmabilon.tipsy.data.room.dao

import androidx.room.*
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

@Dao
interface TruthOrDarePlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: TruthOrDarePlayer)

    @Delete
    suspend fun deletePlayer(player: TruthOrDarePlayer)

    @Query("SELECT * FROM truth_or_dare_player_table")
    fun getAll(): List<TruthOrDarePlayer>
}