package com.jmabilon.tipsy.extensions.android

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.ui.home.HomeActivity


fun Activity.showFeedback(context: Context, message: String) {
    Snackbar.make(findViewById(R.id.root_view), message, Snackbar.LENGTH_SHORT)
        .setTextColor(ContextCompat.getColor(context, R.color.white))
        .setBackgroundTint(ContextCompat.getColor(context, R.color.red))
        .show()
}

fun Activity.getAbsActivity(): HomeActivity? {
    return this as? HomeActivity
}