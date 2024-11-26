package com.example.expensetracker.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetracker.data.TransactionWithCategory
import com.example.expensetracker.data.entity.Transaction




@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)



    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId")
    suspend fun getTransactionsWithCategory(categoryId: Long): List<TransactionWithCategory>

        @Query("SELECT * FROM transactions WHERE date = :date")
        suspend fun getTransactionsByDate(date: String): List<Transaction>


}