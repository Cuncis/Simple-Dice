package com.cuncis.simpledice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class DiceViewModel(var number: MutableLiveData<Int> = MutableLiveData()): ViewModel() {
    init {
        number.value = 1
    }
}