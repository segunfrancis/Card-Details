package com.segunfrancis.carddetails.data

import com.segunfrancis.carddetails.domain.BinListResponse
import kotlinx.coroutines.flow.Flow

interface BinListRepository {

    fun getResponse(cardNumber: String): Flow<BinListResponse>
}