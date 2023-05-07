package com.jmabilon.tipsy.commons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.jmabilon.tipsy.R
import com.jmabilon.tipsy.commons.component.CustomSnackBarComponent

class CustomSnackBar(
    parent: ViewGroup,
    content: CustomSnackBarComponent
) : BaseTransientBottomBar<CustomSnackBar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
    }

    companion object {

        fun make(
            viewGroup: ViewGroup,
            anchorView: View? = null,
            title: String
        ): CustomSnackBar {
            val binding = LayoutInflater.from(viewGroup.context).inflate(
                R.layout.custom_snackbar,
                viewGroup,
                false
            ) as CustomSnackBarComponent

            binding.title.text = title

            return CustomSnackBar(viewGroup, binding).setDuration(Snackbar.LENGTH_SHORT)
        }
    }
}