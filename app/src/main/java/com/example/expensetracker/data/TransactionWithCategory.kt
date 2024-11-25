package com.example.expensetracker.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.expensetracker.data.entity.Category
import com.example.expensetracker.data.entity.Transaction

data class TransactionWithCategory(
    @Embedded val transaction: Transaction,
    @Relation(
        parentColumn = "categoryId",  // Foreign key in Transaction
        entityColumn = "id"          // Primary key in Category
    )
    val category: Category
)
