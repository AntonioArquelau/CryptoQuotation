package com.example.cryptoquotation.repository

import com.example.cryptoquotation.repository.data.Bitcoin

interface MainRepository {
    suspend fun getExchangeRate(currency: String) : Bitcoin
}