package com.example.cryptoquotation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.cryptoquotation.repository.FakeRepositoryImpl
import com.example.cryptoquotation.repository.MainRepository
import com.example.cryptoquotation.repository.MainRepositoryImpl
import com.example.cryptoquotation.repository.data.Bitcoin
import com.example.cryptoquotation.repository.data.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val repository: MainRepository by lazy {
        FakeRepositoryImpl()
    }

    fun getExchangeRate(
        mainCurrency: String,
        targetCurrency: String,
        data: MutableLiveData<DataStatus<Bitcoin>>
    ){
        viewModelScope.launch(Dispatchers.Main) {
            data.postValue(DataStatus.Loading())
            runCatching {
                val response = withContext(Dispatchers.IO) {
                    repository.getExchangeRate(mainCurrency = mainCurrency, targetCurrency = targetCurrency)
                }
                data.postValue(DataStatus.Success(response))
            }.onFailure {
                data.postValue(it.message?.let { ex -> DataStatus.Error(null, ex) })
            }.also {

            }
        }
    }
}