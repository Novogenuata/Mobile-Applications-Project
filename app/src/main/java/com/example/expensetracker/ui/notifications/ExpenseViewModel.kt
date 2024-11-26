package com.example.expensetracker.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.data.dao.TransactionDao
import com.example.expensetracker.data.entity.Transaction
import kotlinx.coroutines.launch

class ExpenseViewModel(private val appDatabase: AppDatabase) : ViewModel() {

    private val transactionDao: TransactionDao = appDatabase.transactionDao()

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.insert(transaction)
        }
    }

    fun getTransactionsByDate(date: String, callback: (List<Transaction>) -> Unit) {
        viewModelScope.launch {
            val transactions = transactionDao.getTransactionsByDate(date)
            callback(transactions)
        }
    }
}
