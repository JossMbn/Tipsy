package com.jmabilon.tipsy.data.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "truth_or_dare_player_table")
data class TruthOrDarePlayer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "player_name") val playerName: String? = null
)
