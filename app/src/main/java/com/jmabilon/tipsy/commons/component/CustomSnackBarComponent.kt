package com.jmabilon.tipsy.commons.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.jmabilon.tipsy.databinding.ComponentCustomSnackbarBinding

class CustomSnackBarComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyle), ContentViewCallback {

    private var binding = ComponentCustomSnackbarBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    var title: TextView = binding.customSnackbarAlertMessage

    override fun animateContentIn(delay: Int, duration: Int) {
        // do nothing
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        // do nothing
    }
}