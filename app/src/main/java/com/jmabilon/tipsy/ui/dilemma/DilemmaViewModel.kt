package com.jmabilon.tipsy.ui.dilemma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmabilon.tipsy.data.Dilemma
import com.jmabilon.tipsy.data.dilemmaList

class DilemmaViewModel : ViewModel() {

    private var _dilemmaData = MutableLiveData<Dilemma>()

    val dilemmaData: LiveData<Dilemma>
        get() = _dilemmaData

    private var data: List<Dilemma> = dilemmaList
    private var dataPosition = 0

    fun loadData() {
        dataPosition += 1
        _dilemmaData.postValue(data.first())
    }

    fun getNextDilemma() {
        if (dataPosition < data.size) {
            _dilemmaData.postValue(data[dataPosition])
            dataPosition += 1
        }
    }
}