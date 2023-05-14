package com.jmabilon.tipsy.ui.neverhaveiever

import androidx.lifecycle.ViewModel
import com.jmabilon.tipsy.data.NeverHaveIEver
import com.jmabilon.tipsy.data.nhieList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NeverHaveIEverViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(value = NeverHaveIEver())

    val uiState: StateFlow<NeverHaveIEver>
        get() = _uiState

    private val nhieData: List<String> = nhieList.shuffled()
    private var nhieDataPosition = 0

    fun loadData() {
        updateLoadSentence(nhieData[0])
        updateNextSentence(nhieData[1])
        nhieDataPosition += 2
    }

    fun getNextSentence() {
        if (nhieDataPosition >= nhieData.size) {
            updateIsGameFinish(true)
        } else {
            updateNextSentence(nhieData[nhieDataPosition])
            nhieDataPosition += 1
        }
    }

    fun resetLoadSentence() {
        updateLoadSentence(null)
    }

    private fun updateLoadSentence(sentence: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                loadSentence = sentence
            )
        }
    }

    private fun updateNextSentence(sentence: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                nextSentence = sentence
            )
        }
    }

    private fun updateIsGameFinish(value: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isGameFinish = value
            )
        }
    }
}