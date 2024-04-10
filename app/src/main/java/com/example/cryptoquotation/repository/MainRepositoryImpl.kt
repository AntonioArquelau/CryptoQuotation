package com.example.cryptoquotation.repository

import com.example.cryptoquotation.repository.data.Bitcoin

class MainRepositoryImpl: BaseRepository(), MainRepository {
    override suspend fun getExchangeRate(mainCurrency: String?, targetCurrency: String) : Bitcoin {
        return cryptoAPI.getExchangeRate(mainCurrency = mainCurrency, targetCurrency = targetCurrency)
    }

}