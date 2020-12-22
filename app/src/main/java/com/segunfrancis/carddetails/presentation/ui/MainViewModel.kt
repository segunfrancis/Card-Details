package com.segunfrancis.carddetails.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfrancis.carddetails.domain.BinListResponse
import com.segunfrancis.carddetails.presentation.util.Result
import com.segunfrancis.carddetails.presentation.util.asLiveData
import com.segunfrancis.carddetails.usecase.GetResponseUseCae
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val getResponseUseCae: GetResponseUseCae) : ViewModel() {

    private val _binResponse = MutableLiveData<Result<BinListResponse>>()
    val binResponse get() = _binResponse.asLiveData()

    private val _cardNumber = MutableLiveData("")
    val cardNumber get() = _cardNumber.asLiveData()

    fun getBinResponse(cardNumber: String) {
        viewModelScope.launch {
            getResponseUseCae(cardNumber)
                .onStart { _binResponse.postValue(Result.Loading) }
                .catch { _binResponse.postValue(Result.Error(it)) }
                .collect { _binResponse.postValue(Result.Success(it)) }
        }
    }

    fun setCardNumber(number: String) {
        _cardNumber.value = number
    }
}