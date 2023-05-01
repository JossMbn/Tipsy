package com.jmabilon.tipsy.di

import android.content.Context
import com.jmabilon.tipsy.BaseApplication
import com.jmabilon.tipsy.helper.PrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context): BaseApplication {
        return context as BaseApplication
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): PrefHelper {
        return PrefHelper(context)
    }
}