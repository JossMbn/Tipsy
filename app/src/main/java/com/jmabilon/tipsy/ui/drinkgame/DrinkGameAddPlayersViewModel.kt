package com.jmabilon.tipsy.ui.drinkgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import com.jmabilon.tipsy.ui.drinkgame.repository.IDrinkGamePlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkGameAddPlayersViewModel @Inject constructor(
    private val drinkGameAddGamePlayerRepository: IDrinkGamePlayerRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(value = DrinkGameAddPlayersUiState())
    private var playersIdList = mutableListOf<Int>()

    val uiState: StateFlow<DrinkGameAddPlayersUiState>
        get() = _uiState

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayerList(drinkGameAddGamePlayerRepository.getAllPlayers())
        }
    }

    fun addPlayer(newPLayer: DrinkGamePlayer) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkGameAddGamePlayerRepository.addPlayer(newPLayer)
        }
    }

    fun removePlayer() {
        viewModelScope.launch(Dispatchers.IO) {
            if (playersIdList.isNotEmpty()) {
                if (playersIdList.size > 1) {
                    drinkGameAddGamePlayerRepository.deletePlayerFromList(playersIdList)
                } else {
                    drinkGameAddGamePlayerRepository.deletePlayer(playersIdList.first())
                }
                playersIdList.clear()
            }
        }
    }

    fun updatePlayersIdList(playerId: Int) {
        this.playersIdList.add(playerId)
    }

    private fun updatePlayerList(list: List<DrinkGamePlayer>) {
        _uiState.update { currentState ->
            currentState.copy(
                playerList = list
            )
        }
    }
}