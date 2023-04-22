package com.jmabilon.tipsy

import android.app.Application
import com.jmabilon.tipsy.helper.PrefHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    // do nothing for now

    override fun onCreate() {
        super.onCreate()

        PrefHelper.init(this)
    }
}