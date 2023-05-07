package com.jmabilon.tipsy.data.room.dao

import androidx.room.*
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

@Dao
interface TruthOrDarePlayerDao {

    @Query("SELECT * FROM truth_or_dare_player_table")
    fun getAll(): List<TruthOrDarePlayer>

    @Query("SELECT player_name FROM truth_or_dare_player_table")
    fun getAllPlayersName(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: TruthOrDarePlayer)

    @Query("DELETE FROM truth_or_dare_player_table WHERE id=:playerId")
    suspend fun deletePlayer(playerId: Int)

    @Query("DELETE FROM truth_or_dare_player_table WHERE id IN (:playersIdList)")
    suspend fun deletePlayerFromList(playersIdList: List<Int>)

    @Query("UPDATE truth_or_dare_player_table SET player_name=:newPlayerName WHERE id=:playerId")
    suspend fun updatePlayer(playerId: Int, newPlayerName: String)
}