package com.jmabilon.tipsy.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jmabilon.tipsy.extensions.android.getBoolean
import com.jmabilon.tipsy.extensions.android.setBoolean
import kotlinx.coroutines.flow.first

class PrefHelper(context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStore = context.datastore

    companion object {
        // Truth Or Dare
        private val TOD_PLAYER_SETTING = booleanPreferencesKey("TOD_PLAYER_SETTING")
    }

    suspend fun setTodPlayerSetting(value: Boolean) {
        dataStore.setBoolean(TOD_PLAYER_SETTING, value)
    }

    suspend fun getTodPlayerSetting(): Boolean {
        return dataStore.getBoolean(TOD_PLAYER_SETTING).first()
    }
}