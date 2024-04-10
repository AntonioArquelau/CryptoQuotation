package com.example.cryptoquotation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoquotation.repository.MainRepository
import com.example.cryptoquotation.repository.MainRepositoryImpl
import com.example.cryptoquotation.repository.data.Bitcoin
import com.example.cryptoquotation.repository.data.DataStatus

class MainViewModel: ViewModel() {

    private val repository: MainRepository by lazy {
        MainRepositoryImpl()
    }
    suspend fun getExchangeRate(currency: String): DataStatus<Bitcoin> {
        val response = repository.getExchangeRate(currency)
        return DataStatus.Success(response)
    }
}