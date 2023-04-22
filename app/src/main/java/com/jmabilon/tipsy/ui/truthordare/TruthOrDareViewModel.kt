package com.jmabilon.tipsy.ui.truthordare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.data.TruthOrDare
import com.jmabilon.tipsy.data.dareList
import com.jmabilon.tipsy.data.repository.ITruthOrDarePlayerRepository
import com.jmabilon.tipsy.data.truthList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TruthOrDareViewModel @Inject constructor(
    private val truthOrDarePlayerRepository: ITruthOrDarePlayerRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(value = TruthOrDareUiState())

    val uiState: StateFlow<TruthOrDareUiState>
        get() = _uiState

    private val dareData: List<String> = dareList
    private val truthData: List<String> = truthList
    private var dareDataPosition = 0
    private var truthDataPosition = 0
    private var playerNameListPosition = 0

    fun getNextCard(playerList: List<String>?, playerChoice: TruthOrDareConstants.Choice) {
        if (truthData.size <= truthDataPosition || dareData.size <= dareDataPosition) {
            updateIsGameFinish(true)
        } else {
            playerList?.let {
                if (playerNameListPosition < playerList.size) {
                    playerNameListPosition = 0
                }
                when (playerChoice) {
                    TruthOrDareConstants.Choice.TRUTH -> {
                        updateNextCard(
                            TruthOrDare(
                                type = TruthOrDareConstants.Type.GAME,
                                playerName = playerList[playerNameListPosition],
                                playerChoice = TruthOrDareConstants.Choice.TRUTH,
                                playerChoiceChallenge = truthData[truthDataPosition]
                            )
                        )
                        truthDataPosition += 1
                    }

                    TruthOrDareConstants.Choice.DARE -> {
                        updateNextCard(
                            TruthOrDare(
                                type = TruthOrDareConstants.Type.GAME,
                                playerName = playerList[playerNameListPosition],
                                playerChoice = TruthOrDareConstants.Choice.DARE,
                                playerChoiceChallenge = dareData[dareDataPosition]
                            )
                        )
                        dareDataPosition += 1
                    }

                    TruthOrDareConstants.Choice.NONE -> {
                        truthDataPosition += 1
                        updateNextCard(
                            TruthOrDare(
                                type = TruthOrDareConstants.Type.GAME,
                                playerName = null,
                                playerChoice = TruthOrDareConstants.Choice.TRUTH,
                                playerChoiceChallenge = truthData[0]
                            )
                        )
                    }
                }
                playerNameListPosition += 1
            } ?: run {
                when (playerChoice) {
                    TruthOrDareConstants.Choice.TRUTH -> {
                        updateNextCard(
                            TruthOrDare(
                                type = TruthOrDareConstants.Type.GAME,
                                playerName = null,
                                playerChoice = TruthOrDareConstants.Choice.TRUTH,
                                playerChoiceChallenge = truthData[truthDataPosition]
                            )
                        )
                        truthDataPosition += 1
                    }

                    TruthOrDareConstants.Choice.DARE -> {
                        updateNextCard(
                            TruthOrDare(
                                type = TruthOrDareConstants.Type.GAME,
                                playerName = null,
                                playerChoice = TruthOrDareConstants.Choice.DARE,
                                playerChoiceChallenge = dareData[dareDataPosition]
                            )
                        )
                        dareDataPosition += 1
                    }

                    TruthOrDareConstants.Choice.NONE -> {
                        truthDataPosition += 1
                        updateNextCard(
                            TruthOrDare(
                                type = TruthOrDareConstants.Type.GAME,
                                playerName = null,
                                playerChoice = TruthOrDareConstants.Choice.TRUTH,
                                playerChoiceChallenge = truthData[0]
                            )
                        )
                    }
                }
            }
        }
    }

    fun getPlayersName() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayersNames(truthOrDarePlayerRepository.getAllPlayersName())
        }
    }

    private fun updateNextCard(card: TruthOrDare?) {
        _uiState.update { currentState ->
            currentState.copy(
                nextCard = card
            )
        }
    }

    private fun updatePlayersNames(list: List<String>?) {
        _uiState.update { currentState ->
            currentState.copy(
                playersNamesList = list?.shuffled()
            )
        }
    }

    private fun updateIsGameFinish(isFinish: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isGameFinish = isFinish
            )
        }
    }
}