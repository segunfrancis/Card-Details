package com.segunfrancis.carddetails.usecase

import com.segunfrancis.carddetails.data.BinListRepository
import com.segunfrancis.carddetails.domain.BinListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetResponseUseCae(
    private val repository: BinListRepository,
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(cardNumber: String): Flow<BinListResponse> {
        return repository.getResponse(cardNumber).flowOn(dispatcher)
    }
}