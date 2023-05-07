package com.jmabilon.tipsy.ui.truthordare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.data.TruthOrDare
import com.jmabilon.tipsy.data.dareList
import com.jmabilon.tipsy.data.truthList
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
class TruthOrDareViewModel @Inject constructor(
    private val prefHelper: PrefHelper,
    private val truthOrDarePlayerRepository: ITruthOrDarePlayerRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(value = TruthOrDareUiState())

    val uiState: StateFlow<TruthOrDareUiState>
        get() = _uiState

    private val dareData: List<String> = dareList.shuffled()
    private val truthData: List<String> = truthList.shuffled()
    private var dareDataPosition = 0
    private var truthDataPosition = 0
    private var playerNameListPosition = 0

    fun getNextCard(playerList: List<String>?, playerChoice: TruthOrDareConstants.Choice) {
        if (truthDataPosition >= truthData.size || dareDataPosition >= dareData.size) {
            updateIsGameFinish(true)
        } else {
            playerList?.let {
                if (playerNameListPosition >= playerList.size) {
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

                    TruthOrDareConstants.Choice.NEXT_PLAYER -> {
                        updateNextCard(
                            TruthOrDare(
                                type = TruthOrDareConstants.Type.NEXT_PLAYER,
                                playerName = playerList[playerNameListPosition],
                                playerChoice = TruthOrDareConstants.Choice.NONE,
                                playerChoiceChallenge = null
                            )
                        )
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

                    TruthOrDareConstants.Choice.NEXT_PLAYER -> {
                        // do nothing
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

    fun getTodPlayerSetting() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayerSettings(prefHelper.getTodPlayerSetting())
        }
    }

    fun resetTodPlayerSetting() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayerSettings(null)
        }
    }

    fun updatePlayerNamesPosition() {
        this.playerNameListPosition += 1
    }

    fun getPlayersName() {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlayersNames(truthOrDarePlayerRepository.getAllPlayersName().shuffled())
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
        if (!list.isNullOrEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    playersNamesList = list
                )
            }
        }
    }

    private fun updateIsGameFinish(isFinish: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isGameFinish = isFinish
            )
        }
    }

    private fun updatePlayerSettings(settings: Boolean?) {
        _uiState.update { currentState ->
            currentState.copy(
                playerSettings = settings
            )
        }
    }
}