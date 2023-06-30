package com.jmabilon.tipsy.ui.home

import android.graphics.drawable.Drawable

enum class HomeItemViewEnum(val viewType: Int) {
    HOME_HEADER_ITEM(0),
    HOME_SECTION_ITEM(1),
    HOME_GAME_CARD_ITEM(2),
    HOME_FOOTER_ITEM(3),
    HOME_ADS_ITEM(4)
}

enum class HomeCardTypeEnum(val type: Int) {
    DRINK_GAME(0),
    TRUTH_OR_DARE(1),
    DILEMMA(2),
    MOST_LIKELY_TO(3),
    NEVER_HAVE_I_EVER(4)
}

data class HomeItemViewPresentation(
    val type: HomeItemViewEnum,
    val gameType: HomeCardTypeEnum = HomeCardTypeEnum.DRINK_GAME,
    val cardTitle: String? = null,
    val cardDescription: String? = null,
    val cardIcon: Drawable? = null,
    val sectionTitle: String? = null
)
