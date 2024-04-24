package com.example.cryptoquotation.repository

import com.example.cryptoquotation.repository.data.Bitcoin
import kotlinx.coroutines.delay

class FakeRepositoryImpl: MainRepository {

    companion object{
        private const val SEED = 1000.0
        private const val DEFAULT_SEED = 0.0
        private const val OFFSET = 10
    }

    private val previousRandomValue = mutableMapOf<String, Double>()
    override suspend fun getExchangeRate(mainCurrency: String?, targetCurrency: String): Bitcoin {
        val randomDelay = Math.random() * SEED
        delay(randomDelay.toLong())

        val range = if (previousRandomValue.containsKey(mainCurrency+targetCurrency)) {
            getRandomRange(previousRandomValue[mainCurrency + targetCurrency])
        } else{
            previousRandomValue[mainCurrency + targetCurrency] = Math.random() * SEED
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
        if (seed == DEFAULT_SEED) {
            seed = SEED
        }
        return Pair(seed - OFFSET, seed + OFFSET)
    }
}