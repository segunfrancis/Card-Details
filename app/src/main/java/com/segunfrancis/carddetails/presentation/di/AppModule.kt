package com.segunfrancis.carddetails.presentation.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.segunfrancis.carddetails.data.BinListRepository
import com.segunfrancis.carddetails.framework.BinListApi
import com.segunfrancis.carddetails.framework.BinListRepositoryImpl
import com.segunfrancis.carddetails.presentation.util.AppConstants.BASE_URL
import com.segunfrancis.carddetails.usecase.GetResponseUseCae
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

@UnstableDefault
val useCaseModule = module {
    single { Dispatchers.IO }
    single<BinListRepository> { BinListRepositoryImpl(provideAip()) }
    single { GetResponseUseCae(get(), get()) }
}

val viewModelModule = module { }

@UnstableDefault
fun provideAip(): BinListApi {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder().addInterceptor(logger).build()

    val contentType =  "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    return retrofit.create(BinListApi::class.java)
}