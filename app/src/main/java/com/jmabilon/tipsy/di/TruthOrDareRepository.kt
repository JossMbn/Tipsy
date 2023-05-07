package com.jmabilon.tipsy.di

import android.content.Context
import com.jmabilon.tipsy.ui.truthordare.repository.ITruthOrDarePlayerRepository
import com.jmabilon.tipsy.ui.truthordare.repository.TruthOrDarePlayerRepositoryImpl
import com.jmabilon.tipsy.data.room.dao.TruthOrDarePlayerDao
import com.jmabilon.tipsy.data.room.database.TruthOrDarePlayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TruthOrDareRepository {

    @Singleton
    @Provides
    fun provideTruthOrDarePlayerDao(
        @ApplicationContext context: Context
    ): TruthOrDarePlayerDao {
        return TruthOrDarePlayerDatabase.getDatabase(context).truthOrDarePlayerDao()
    }

    @Singleton
    @Provides
    fun provideTruthOrdDareRepository(
        truthOrDarePlayerDao: TruthOrDarePlayerDao
    ): ITruthOrDarePlayerRepository {
        return TruthOrDarePlayerRepositoryImpl(truthOrDarePlayerDao)
    }
}