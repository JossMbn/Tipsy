package com.jmabilon.tipsy.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jmabilon.tipsy.data.room.dao.DrinkGamePlayerDao
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer

@Database(entities = [DrinkGamePlayer::class], version = 1)
abstract class DrinkGamePlayerDatabase : RoomDatabase() {

    abstract fun drinkGamePlayerDao(): DrinkGamePlayerDao

    companion object {
        @Volatile
        private var INSTANCE: DrinkGamePlayerDatabase? = null

        fun getDatabase(context: Context): DrinkGamePlayerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinkGamePlayerDatabase::class.java,
                    "drink_game_player_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}