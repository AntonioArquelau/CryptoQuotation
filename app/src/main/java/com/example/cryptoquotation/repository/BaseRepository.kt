package com.example.cryptoquotation.repository

import com.example.cryptoquotation.api.CryptoAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseRepository {
    companion object{
        private const val BASE_URL = "https://rest.coinapi.io/v1/exchangerate/"
        private const val TIMEOUT = 50L
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    val cryptoAPI: CryptoAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CryptoAPI::class.java)
    }
}