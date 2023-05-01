package com.jmabilon.tipsy.extensions.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun DataStore<Preferences>.setBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
    this.edit { pref ->
        pref[key] = value
    }
}

fun DataStore<Preferences>.getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> {
    return this.data.map { prefs ->
        prefs[key] ?: false
    }
}