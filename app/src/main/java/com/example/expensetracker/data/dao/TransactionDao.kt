package com.example.expensetracker.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetracker.data.TransactionWithCategory
import com.example.expensetracker.data.entity.Transaction


// Ensure this import exists

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    // This query will return a list of `TransactionWithCategory`

    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId")
    suspend fun getTransactionsWithCategory(categoryId: Long): List<TransactionWithCategory>
}