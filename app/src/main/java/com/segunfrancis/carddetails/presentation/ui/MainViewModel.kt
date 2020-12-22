package com.segunfrancis.carddetails.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfrancis.carddetails.domain.BinListResponse
import com.segunfrancis.carddetails.presentation.util.Result
import com.segunfrancis.carddetails.usecase.GetResponseUseCae
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val getResponseUseCae: GetResponseUseCae) : ViewModel() {

    private val _binResponse = MutableLiveData<Result<BinListResponse>>()
    val binResponse get() = _binResponse as LiveData<Result<BinListResponse>>

    fun getBinResponse(cardNumber: String) {
        viewModelScope.launch {
            getResponseUseCae(cardNumber)
                .onStart { _binResponse.postValue(Result.Loading) }
                .catch { _binResponse.postValue(Result.Error(it)) }
                .collect { _binResponse.postValue(Result.Success(it)) }
        }
    }
}