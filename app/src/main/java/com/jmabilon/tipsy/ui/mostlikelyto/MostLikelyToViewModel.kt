package com.jmabilon.tipsy.ui.mostlikelyto

import androidx.lifecycle.ViewModel
import com.jmabilon.tipsy.data.MostLikelyTo
import com.jmabilon.tipsy.data.mltList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MostLikelyToViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(value = MostLikelyTo())

    val uiState: StateFlow<MostLikelyTo>
        get() = _uiState

    private val mltData: List<String> = mltList.shuffled()
    private var mltDataPosition = 0

    fun loadData() {
        updateLoadSentence(mltData[0])
        updateNextSentence(mltData[1])
        mltDataPosition += 2
    }

    fun getNextSentence() {
        if (mltDataPosition >= mltData.size) {
            updateIsGameFinish(true)
        } else {
            updateNextSentence(mltData[mltDataPosition])
            mltDataPosition += 1
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