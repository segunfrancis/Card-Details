package com.segunfrancis.carddetails.framework

import com.segunfrancis.carddetails.domain.BinListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BinListApi {

    @GET("{number}")
    suspend fun getResponse(@Path("number") cardNumber: String): BinListResponse
}