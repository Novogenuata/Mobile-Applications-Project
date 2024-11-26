package com.example.expensetracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.R
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.data.entity.Transaction
import com.example.expensetracker.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize the database
        activity?.let { context ->
            appDatabase = AppDatabase.getDatabase(context)
        }

        // Fetch and display financial data and transactions
        fetchAndDisplayFinancialData()
        fetchAndDisplayTransactions()

        return binding.root
    }

    private fun fetchAndDisplayFinancialData() {
        lifecycleScope.launch {
            try {
                val totalExpenses = appDatabase.transactionDao().getTotalExpenses()
                val totalIncome = appDatabase.transactionDao().getTotalIncome()
                val totalAmount = totalIncome - totalExpenses

                binding.expensesAmount.text = getString(R.string.expenses_format, totalExpenses)
                binding.incomeAmount.text = getString(R.string.income_format, totalIncome)
                binding.totalAmount.text = getString(R.string.total_format, totalAmount)
            } catch (e: Exception) {
                e.printStackTrace()
                binding.expensesAmount.text = getString(R.string.expenses_error)
                binding.incomeAmount.text = getString(R.string.income_error)
                binding.totalAmount.text = getString(R.string.total_error)
            }
        }
    }

    private fun fetchAndDisplayTransactions() {
        lifecycleScope.launch {
            try {
                val transactions = appDatabase.transactionDao().getAllTransactions()

                // Clear existing transactions
                binding.transactionsLayout.removeAllViews()

                transactions.forEach { transaction ->
                    val transactionCard = createTransactionCard(transaction)
                    binding.transactionsLayout.addView(transactionCard)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createTransactionCard(transaction: Transaction): View {
        val cardView = LayoutInflater.from(context).inflate(R.layout.transaction_card, null) as CardView

        val transactionAmount = cardView.findViewById<TextView>(R.id.transactionAmount)
        val transactionDate = cardView.findViewById<TextView>(R.id.transactionDate)
        val transactionCategory = cardView.findViewById<TextView>(R.id.transactionCategory)
        val transactionType = cardView.findViewById<TextView>(R.id.transactionType)
        val deleteButton = cardView.findViewById<ImageButton>(R.id.deleteButton)

        transactionAmount.text = getString(R.string.amount_format, transaction.amount)
        transactionDate.text = transaction.date
        transactionType.text = transaction.entryType

        // Fetch and display the category name
        lifecycleScope.launch {
            try {
                val category = appDatabase.categoryDao().getCategoryById(transaction.categoryId)
                transactionCategory.text = category?.name ?: "Unknown Category"
            } catch (e: Exception) {
                e.printStackTrace()
                transactionCategory.text = "Unknown Category"
            }
        }

        deleteButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    appDatabase.transactionDao().delete(transaction)
                    binding.transactionsLayout.removeView(cardView)
                    fetchAndDisplayFinancialData() // Refresh financial data after deletion
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return cardView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
