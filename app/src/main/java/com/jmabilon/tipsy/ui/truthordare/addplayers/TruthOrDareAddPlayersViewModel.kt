package com.jmabilon.tipsy.ui.truthordare.addplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import com.jmabilon.tipsy.helper.PrefHelper
import com.jmabilon.tipsy.ui.truthordare.repository.ITruthOrDarePlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TruthOrDareAddPlayersViewModel @Inject constructor(
    private val prefHelper: PrefHelper,
    private val truthOrDarePlayerRepository: ITruthOrDarePlayerRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(value = TruthOrDareAddPlayersUiState())

    val uiState: StateFlow<TruthOrDareAddPlayersUiState>
        get() = _uiState

    fun loadData() {
        getTodPlayerSetting()
        getAllPlayers()
    }

    private fun getAllPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayerList(truthOrDarePlayerRepository.getAllPlayers())
        }
    }

    fun addPlayer(newPLayer: TruthOrDarePlayer) {
        viewModelScope.launch(Dispatchers.IO) {
            truthOrDarePlayerRepository.addPlayer(newPLayer)
            getAllPlayers()
        }
    }

    fun deletePlayer(playerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            truthOrDarePlayerRepository.deletePlayer(playerId)
            getAllPlayers()
        }
    }

    fun setPlayerSettings(settings: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            prefHelper.setTodPlayerSetting(settings)
        }
    }

    private fun getTodPlayerSetting() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayerSettings(prefHelper.getTodPlayerSetting())
        }
    }

    private fun updatePlayerList(playerList: List<TruthOrDarePlayer>?) {
        _uiState.update { currentState ->
            currentState.copy(
                playersList = playerList
            )
        }
    }

    private fun updatePlayerSettings(settings: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                playerSettings = settings
            )
        }
    }
}