package com.jmabilon.tipsy.ui.drinkgame.addplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.data.room.data.DrinkGamePlayer
import com.jmabilon.tipsy.ui.drinkgame.addplayers.repository.IDrinkGamePlayerRepository
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
    private var _playersCount: Int = 0

    val uiState: StateFlow<DrinkGameAddPlayersUiState>
        get() = _uiState

    val playersCount: Int
        get() = _playersCount

    fun loadData() {
        getAllPlayers()
        getPlayersCount()
    }

    private fun getAllPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayerList(drinkGameAddGamePlayerRepository.getAllPlayers())
        }
    }

    private fun getPlayersCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _playersCount = drinkGameAddGamePlayerRepository.getAllPlayers().size
        }
    }

    fun addPlayer(newPLayer: DrinkGamePlayer) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkGameAddGamePlayerRepository.addPlayer(newPLayer)
            getPlayersCount()
            loadData()
        }
    }

    fun removePlayer(playerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkGameAddGamePlayerRepository.deletePlayer(playerId)
            getPlayersCount()
            loadData()
        }
    }

    private fun updatePlayerList(list: List<DrinkGamePlayer>) {
        _uiState.update { currentState ->
            currentState.copy(
                playerList = list
            )
        }
    }
}