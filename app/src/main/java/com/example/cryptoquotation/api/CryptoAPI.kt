package com.example.cryptoquotation.api

import com.example.cryptoquotation.repository.data.Bitcoin
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CryptoAPI {

    @GET("BTC/{currency}")
    suspend fun getExchangeRate(
        @Header("X-CoinApi-key") key:String = "F11A11C0-5E03-A564-6B00-AF0B9884F0AF",
        @Path("currency") currency: String
    ) : Bitcoin
}