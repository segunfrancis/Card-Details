package com.segunfrancis.carddetails.presentation.util

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun View.enableState(enable: Boolean) {
    if (enable) {
        alpha = 1F
        isEnabled = true
    } else {
        alpha = 0.7F
        isEnabled = false
    }
}


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this
