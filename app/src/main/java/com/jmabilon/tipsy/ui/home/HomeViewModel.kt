package com.jmabilon.tipsy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.helper.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefHelper: PrefHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = HomeUiState())

    val uiState: StateFlow<HomeUiState>
        get() = _uiState

    fun checkWarningVisibility() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!prefHelper.getWarningVisibility()) {
                withContext(Dispatchers.Main) {
                    updateDisplayWarning(true)
                }
            }
        }
    }

    private fun updateDisplayWarning(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                displayWarning = isVisible
            )
        }
    }
}