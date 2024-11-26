package com.example.expensetracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.R
import com.example.expensetracker.data.database.AppDatabase
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

        // Fetch and display total expenses and income
        fetchAndDisplayFinancialData()

        // Set delete button logic for each card
        binding.deleteButton1.setOnClickListener {
            binding.cardView1.visibility = View.GONE
        }
        binding.deleteButton2.setOnClickListener {
            binding.cardView2.visibility = View.GONE
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
