package com.jmabilon.tipsy.data

data class Dilemma(
    val firstDilemma: String? = null,
    val secondDilemma: String? = null
)

val dilemmaList = listOf(
    Dilemma("drink water", "never eat cookies ever"),
    Dilemma("go see your friend", "go see your girlfriend"),
    Dilemma("test1", "test9"),
    Dilemma("test2", "test8"),
    Dilemma("test3", "test7"),
    Dilemma("test4", "test6"),
    Dilemma("test5", "test5.5"),
    Dilemma("test6", "test4"),
    Dilemma("test7", "test3"),
    Dilemma("test8", "test2")
).shuffled()
