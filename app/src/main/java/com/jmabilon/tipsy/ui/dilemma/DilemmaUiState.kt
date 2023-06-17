package com.jmabilon.tipsy.ui.dilemma

import com.jmabilon.tipsy.data.Dilemma

data class DilemmaUiState(
    val dilemmaData: Dilemma? = null,
    val isGameFinish: Boolean = false,
    val count: Int = 0
)
