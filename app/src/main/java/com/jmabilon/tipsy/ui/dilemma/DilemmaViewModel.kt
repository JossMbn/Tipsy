package com.jmabilon.tipsy.ui.dilemma

import androidx.lifecycle.ViewModel
import com.jmabilon.tipsy.data.Dilemma
import com.jmabilon.tipsy.data.dilemmaList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DilemmaViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(value = DilemmaUiState())

    val uiState: StateFlow<DilemmaUiState>
        get() = _uiState

    private var data: List<Dilemma> = dilemmaList
    private var dataPosition = 0
    private var dilemmaCount: Int = 30

    fun loadData(dilemmaCount: String, dilemmaCountArray: Array<String>) {
        setDilemmaCount(dilemmaCount, dilemmaCountArray)
        dataPosition += 1
        updateDilemmaData(data.first())
        updateDilemmaCount(dataPosition)
    }

    fun getNextDilemma() {
        if (dataPosition < dilemmaCount) {
            updateDilemmaData(data[dataPosition])
            dataPosition += 1
            updateDilemmaCount(dataPosition)
        } else {
            updateIsGameFinish(true)
        }
    }

    private fun setDilemmaCount(count: String, dilemmaCountArray: Array<String>) {
        for (item in dilemmaCountArray) {
            if (item == count) {
                dilemmaCount = item.toInt()
            }
        }
    }

    private fun updateDilemmaData(newData: Dilemma) {
        _uiState.update { currentState ->
            currentState.copy(
                dilemmaData =  newData
            )
        }
    }

    private fun updateDilemmaCount(newCount: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                count =  newCount
            )
        }
    }

    private fun updateIsGameFinish(isFinish: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isGameFinish =  isFinish
            )
        }
    }
}