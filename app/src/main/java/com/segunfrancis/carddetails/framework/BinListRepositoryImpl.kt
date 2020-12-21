package com.segunfrancis.carddetails.framework

import com.segunfrancis.carddetails.data.BinListRepository
import com.segunfrancis.carddetails.domain.BinListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BinListRepositoryImpl(private val api: BinListApi) : BinListRepository {
    override fun getResponse(cardNumber: String): Flow<BinListResponse> {
        return flow {
            emit(api.getResponse(cardNumber))
        }
    }
}