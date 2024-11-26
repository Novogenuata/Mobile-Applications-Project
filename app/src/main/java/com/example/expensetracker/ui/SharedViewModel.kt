package com.example.expensetracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _transactionAdded = MutableLiveData<String>()
    val transactionAdded: LiveData<String> get() = _transactionAdded

    fun notifyTransactionAdded(date: String) {
        _transactionAdded.value = date
    }
}
