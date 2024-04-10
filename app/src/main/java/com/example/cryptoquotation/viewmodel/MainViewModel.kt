package com.example.cryptoquotation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.cryptoquotation.repository.MainRepository
import com.example.cryptoquotation.repository.MainRepositoryImpl
import com.example.cryptoquotation.repository.data.Bitcoin
import com.example.cryptoquotation.repository.data.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val repository: MainRepository by lazy {
        MainRepositoryImpl()
    }

    private val _rate = MediatorLiveData<DataStatus<Bitcoin>>()
    val rateLiveData: LiveData<DataStatus<Bitcoin>> = _rate.map { it }

    fun getExchangeRate(currency: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _rate.postValue(DataStatus.Loading())
            runCatching {
                val response = withContext(Dispatchers.IO) {
                    repository.getExchangeRate(currency)
                }
                _rate.postValue(DataStatus.Success(response))
            }.onFailure {
                _rate.postValue(it.message?.let { ex -> DataStatus.Error(null, ex) })
            }.also {

            }
        }
    }
}