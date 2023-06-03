package com.jmabilon.tipsy.di

import android.content.Context
import com.jmabilon.tipsy.data.room.dao.DrinkGamePlayerDao
import com.jmabilon.tipsy.data.room.database.DrinkGamePlayerDatabase
import com.jmabilon.tipsy.ui.drinkgame.addplayers.repository.DrinkGamePlayerRepositoryImpl
import com.jmabilon.tipsy.ui.drinkgame.addplayers.repository.IDrinkGamePlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DrinkGameRepository {

    @Singleton
    @Provides
    fun provideDrinkGamePlayerDao(
        @ApplicationContext context: Context
    ): DrinkGamePlayerDao {
        return DrinkGamePlayerDatabase.getDatabase(context).drinkGamePlayerDao()
    }

    @Singleton
    @Provides
    fun provideDrinkGameRepository(
        drinkGamePlayerDao: DrinkGamePlayerDao
    ): IDrinkGamePlayerRepository {
        return DrinkGamePlayerRepositoryImpl(drinkGamePlayerDao)
    }
}