package com.jmabilon.tipsy.ui.truthordare

import com.jmabilon.tipsy.data.TruthOrDare

data class TruthOrDareUiState(
    val nextCard: TruthOrDare? = null,
    val playersNamesList: List<String>? = null,
    val isGameFinish: Boolean = false
)
