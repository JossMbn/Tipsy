package com.jmabilon.tipsy.ui.warning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.helper.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarningViewModel @Inject constructor(
    private val prefHelper: PrefHelper
) : ViewModel() {

    fun disableWarningMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            prefHelper.setWarningVisibility(true)
        }
    }
}