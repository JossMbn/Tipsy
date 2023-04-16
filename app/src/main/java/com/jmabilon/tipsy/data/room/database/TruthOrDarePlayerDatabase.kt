package com.jmabilon.tipsy.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jmabilon.tipsy.data.room.dao.TruthOrDarePlayerDao
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer

@Database(entities = [TruthOrDarePlayer::class], version = 1)
abstract class TruthOrDarePlayerDatabase : RoomDatabase() {

    abstract fun truthOrDarePlayerDao(): TruthOrDarePlayerDao

    companion object {
        @Volatile
        private var INSTANCE: TruthOrDarePlayerDatabase? = null

        fun getDatabase(context: Context): TruthOrDarePlayerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TruthOrDarePlayerDatabase::class.java,
                    "truth_or_dare_player_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}