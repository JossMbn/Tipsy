package com.jmabilon.tipsy.ui.dilemma.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jmabilon.tipsy.databinding.ComponentDilemmaCardBinding

class DilemmaCardComponent(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs, 0) {

    private val binding = ComponentDilemmaCardBinding
        .inflate(LayoutInflater.from(context), this, true)
    private var cardText: String? = null
    private var listener: DilemmaComponentListener? = null

    init {
        binding.cardComponentText.setOnClickListener {
            listener?.onCardClick()
        }
    }

    fun setListener(listener: DilemmaComponentListener) {
        this.listener = listener
    }

    fun setCardText(cardText: String? = null) {
        this.cardText = cardText
        updateComponent()
    }

    private fun updateComponent() {
        binding.cardComponentText.text = this.cardText
    }

    interface DilemmaComponentListener {
        fun onCardClick()
    }
}