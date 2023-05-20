package com.jmabilon.tipsy.extensions.android

fun String.cleanPlayerName(): String {
    return this
        .replace("\\s".toRegex(), "")
        .replaceFirstChar { it.uppercase() }
}