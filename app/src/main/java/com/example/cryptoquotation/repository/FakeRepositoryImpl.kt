package com.example.cryptoquotation.repository

import com.example.cryptoquotation.repository.data.Bitcoin
import kotlinx.coroutines.delay

class FakeRepositoryImpl: MainRepository {

    private val previousRandomValue = mutableMapOf<String, Double>()
    override suspend fun getExchangeRate(mainCurrency: String?, targetCurrency: String): Bitcoin {
        delay(1000)

        val range = if (previousRandomValue.containsKey(mainCurrency+targetCurrency)) {
            getRandomRange(previousRandomValue[mainCurrency + targetCurrency])
        } else{
            previousRandomValue[mainCurrency + targetCurrency] = Math.random() * 1000.0
            getRandomRange(previousRandomValue[mainCurrency + targetCurrency])
        }

        val currentValue = range.first + Math.random() * (range.second - range.first);
        previousRandomValue[mainCurrency+targetCurrency] = currentValue
        return Bitcoin(
            mainCurrency,
            targetCurrency,
            previousRandomValue[mainCurrency+targetCurrency].toString(),
            time = null
        )
    }

    private fun getRandomRange(previousValue: Double?): Pair<Double, Double> {
        var seed = previousValue!!
        if (seed == 0.0) {
            seed = 1000.0
        }
        return Pair(seed - 10, seed + 10)
    }
}