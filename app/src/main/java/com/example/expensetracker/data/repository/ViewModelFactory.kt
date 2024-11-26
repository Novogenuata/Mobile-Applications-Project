package com.example.expensetracker.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.ui.notifications.ExpenseViewModel

class ExpenseViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            return ExpenseViewModel(appDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
