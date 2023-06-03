package com.jmabilon.tipsy.ui.drinkgame

import androidx.lifecycle.ViewModel
import com.jmabilon.tipsy.ui.drinkgame.addplayers.repository.IDrinkGamePlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DrinkGameViewModel @Inject constructor(
    private val drinkGameAddGamePlayerRepository: IDrinkGamePlayerRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(value = DrinkGameUiState())

    val uiState: StateFlow<DrinkGameUiState>
        get() = _uiState

    fun loadData() {
        // do nothing
    }

    /*private fun updatePlayerList(list: List<DrinkGamePlayer>) {
        _uiState.update { currentState ->
            currentState.copy(
                playerList = list
            )
        }
    }*/
}