package com.jmabilon.tipsy.ui.drinkgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.tipsy.data.simpleQuestionsList
import com.jmabilon.tipsy.ui.drinkgame.addplayers.repository.IDrinkGamePlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkGameViewModel @Inject constructor(
    private val drinkGameAddGamePlayerRepository: IDrinkGamePlayerRepository
) : ViewModel() {
    companion object {
        const val MAX_GAME_QUESTION: Int = 40
    }

    private var _uiState = MutableStateFlow(value = DrinkGameUiState())

    val uiState: StateFlow<DrinkGameUiState>
        get() = _uiState

    private var playersNames: List<String>? = null

    private var simpleQuestionsData: List<String> = simpleQuestionsList.shuffled()
    private var playerQuestionsData: List<String>? = null

    private var simpleQuestionsDataPosition: Int = 0
    private var playerQuestionsDataPosition: Int = 0

    private var randomChoose: Int = 0
    private var questionNumber: Int = 0

    fun loadData(playerQuestionArray: Array<String>) {
        getPlayersName()
        playerQuestionsData = playerQuestionArray.toList().shuffled()
        updateData()
    }

    fun updateData() {
        if (playersNames.isNullOrEmpty()) {
            updateData()
        } else {
            randomChoose = (0..1).random()
            when (randomChoose) {
                0 -> {
                    if (questionNumber >= MAX_GAME_QUESTION) {
                        updateGameIsFinish(true)
                    } else {
                        updateSentence(simpleQuestionsData[simpleQuestionsDataPosition])
                        simpleQuestionsDataPosition += 1
                    }
                }

                1 -> {
                    playerQuestionsData?.let {
                        if (questionNumber >= MAX_GAME_QUESTION) {
                            updateGameIsFinish(true)
                        } else {
                            updateSentence(String.format(it[playerQuestionsDataPosition], nextPlayer()))
                            playerQuestionsDataPosition += 1
                        }
                    }
                }
            }
            questionNumber += 1
        }
    }

    private fun getPlayersName() {
        viewModelScope.launch(Dispatchers.IO) {
            playersNames = drinkGameAddGamePlayerRepository.getAllPlayersName().shuffled()
        }
    }

    private fun nextPlayer(): String {
        playersNames?.let { playersList ->
            val nextPlayerPosition: Int = playersList.indices.random()

            return playersList[nextPlayerPosition]
        }
        return ""
    }

    private fun updateSentence(newSentence: String) {
        _uiState.update { currentState ->
            currentState.copy(
                sentence = newSentence
            )
        }
    }

    private fun updateGameIsFinish(isFinish: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                gameIsFinish = isFinish
            )
        }
    }
}