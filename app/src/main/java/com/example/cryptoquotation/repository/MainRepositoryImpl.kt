package com.example.cryptoquotation.repository

import com.example.cryptoquotation.repository.data.Bitcoin

class MainRepositoryImpl: BaseRepository(), MainRepository {
    override suspend fun getExchangeRate(currency: String) : Bitcoin {
        return cryptoAPI.getExchangeRate(currency = currency)
    }

}