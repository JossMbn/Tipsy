package com.jmabilon.tipsy.extensions.android

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jmabilon.tipsy.BaseApplication

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getBaseApplication(): BaseApplication? {
    return this.applicationContext as? BaseApplication
}