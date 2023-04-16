package com.jmabilon.tipsy.ui.truthordare.addplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.data.repository.ITruthOrDarePlayerRepository
import com.jmabilon.tipsy.data.room.data.TruthOrDarePlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TruthOrDareAddPlayersViewModel @Inject constructor(
    private val truthOrDarePlayerRepository: ITruthOrDarePlayerRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(value = TruthOrDareAddPlayersUiState())

    val uiState: StateFlow<TruthOrDareAddPlayersUiState>
        get() = _uiState

    fun getAllPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayerList(truthOrDarePlayerRepository.getAllPLayers())
        }
    }

    fun addPlayer(newPLayer: TruthOrDarePlayer) {
        viewModelScope.launch(Dispatchers.IO) {
            truthOrDarePlayerRepository.addPlayer(newPLayer)
        }
    }

    fun deletePlayer(player: TruthOrDarePlayer) {
        viewModelScope.launch(Dispatchers.IO) {
            truthOrDarePlayerRepository.deletePlayer(player)
        }
    }

    private fun updatePlayerList(playerList: List<TruthOrDarePlayer>?) {
        _uiState.update { currentState ->
            currentState.copy(
                playersList = playerList
            )
        }
    }
}