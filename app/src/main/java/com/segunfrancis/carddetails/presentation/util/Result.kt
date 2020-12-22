package com.segunfrancis.carddetails.presentation.util

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class Result<out T> {
    data class Success<T>(val data: T?) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>() {
        val formattedErrorMessage = when (error) {
            is UnknownHostException -> {
                "Make sure you are connected to the internet"
            }
            is SocketTimeoutException -> {
                "Internet might be slow, try again later"
            }
            is HttpException -> {
                when {
                    error.code() == 404 -> {
                        "The numbers you entered might be incorrect, check the numbers and try again"
                    }
                    error.code() == 429 -> {
                        "You have reached your limit, try again after some time"
                    }
                    else -> {
                        "Something went wrong"
                    }
                }
            }
            else -> {
                "Something went wrong"
            }
        }
    }

    object Loading : Result<Nothing>()
}
