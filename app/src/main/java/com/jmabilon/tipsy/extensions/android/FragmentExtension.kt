package com.jmabilon.tipsy.extensions.android

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

fun Fragment.safeNavigation(directions: NavDirections) {
    try {
        val navController = findNavController()
        val currentDestination =
            (navController.currentDestination as? FragmentNavigator.Destination)?.className
                ?: (navController.currentDestination as? DialogFragmentNavigator.Destination)?.className

        if (currentDestination == this.javaClass.name) {
            navController.navigate(directions)
        }
    } catch (e: Throwable) {
        Log.d("NAVIGATION_SAFE_TAG", "navigation error for action $directions : ${e.message}")
    }
}