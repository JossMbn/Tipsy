package com.jmabilon.tipsy.data.room.dao

import androidx.room.*
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

@Dao
interface DrinkGamePlayerDao {

    @Query("SELECT * FROM drink_game_player_table")
    fun getAll(): List<DrinkGamePlayer>

    @Query("SELECT player_name FROM drink_game_player_table")
    fun getAllPlayersName(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: DrinkGamePlayer)

    @Query("DELETE FROM drink_game_player_table WHERE id=:playerId")
    suspend fun deletePlayer(playerId: Int)

    @Query("DELETE FROM drink_game_player_table WHERE id IN (:playersIdList)")
    suspend fun deletePlayerFromList(playersIdList: List<Int>)

    @Query("UPDATE drink_game_player_table SET player_name=:newPlayerName WHERE id=:playerId")
    suspend fun updatePlayer(playerId: Int, newPlayerName: String)
}