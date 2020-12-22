package com.segunfrancis.carddetails.presentation.di

import com.google.gson.GsonBuilder
import com.segunfrancis.carddetails.data.BinListRepository
import com.segunfrancis.carddetails.framework.BinListApi
import com.segunfrancis.carddetails.framework.BinListRepositoryImpl
import com.segunfrancis.carddetails.presentation.ui.MainViewModel
import com.segunfrancis.carddetails.presentation.util.AppConstants.BASE_URL
import com.segunfrancis.carddetails.presentation.util.AppConstants.TIMEOUT
import com.segunfrancis.carddetails.usecase.GetResponseUseCae
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val useCaseModule = module {
    single { Dispatchers.IO }
    single<BinListRepository> { BinListRepositoryImpl(provideAip()) }
    single { GetResponseUseCae(get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

fun provideAip(): BinListApi {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY

    val gson = GsonBuilder().setLenient().create()

    val client = OkHttpClient
        .Builder()
        .addInterceptor(logger)
        .callTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    return retrofit.create(BinListApi::class.java)
}