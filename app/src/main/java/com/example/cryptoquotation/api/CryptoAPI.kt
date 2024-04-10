package com.example.cryptoquotation.api

import com.example.cryptoquotation.repository.data.Bitcoin
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CryptoAPI {

    @GET("{main_currency}/{target_currency}")
    suspend fun getExchangeRate(
        @Header("X-CoinApi-key") key:String = "F11A11C0-5E03-A564-6B00-AF0B9884F0AF",
        @Path("main_currency") mainCurrency: String? = "BTC",
        @Path("target_currency") targetCurrency: String
    ) : Bitcoin
}