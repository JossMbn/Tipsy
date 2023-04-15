package com.jmabilon.tipsy.data

import com.jmabilon.tipsy.ui.truthordare.TruthOrDareConstants

data class TruthOrDare(
    var type: TruthOrDareConstants.Type,
    var playerName: String? = null,
    var playerChoice: TruthOrDareConstants.Choice = TruthOrDareConstants.Choice.TRUTH,
    var playerChoiceChallenge: String? = null
)


val dareList = listOf(
    "dare1",
    "dare2",
    "dare3"
).shuffled()

val truthList = listOf(
    "truth1",
    "truth2",
    "truth3"
).shuffled()