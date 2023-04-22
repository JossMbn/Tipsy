package com.jmabilon.tipsy.helper

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.extensions.android.setBoolean
import javax.inject.Inject

class PrefHelper @Inject constructor(application: Application) {

    init {
        sPrefs = application.getSharedPreferences(
            application.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
    }

    companion object {
        private lateinit var sPrefs: SharedPreferences

        // Truth Or Dare
        private const val TOD_PLAYER_SETTING = "TOD_PLAYER_SETTING"

        fun init(application: Application) {
            PrefHelper(application)
        }

        fun setTodPlayerSetting(value: Boolean) {
            sPrefs.setBoolean(TOD_PLAYER_SETTING, value)
        }

        fun getTodPlayerSetting(): Boolean =
            sPrefs.getBoolean(TOD_PLAYER_SETTING, false)
    }
}