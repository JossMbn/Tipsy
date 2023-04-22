package com.jmabilon.tipsy.extensions.android

import android.content.SharedPreferences

fun SharedPreferences.setBoolean(key: String, value: Boolean) {
    this.edit().putBoolean(key, value).apply()
}