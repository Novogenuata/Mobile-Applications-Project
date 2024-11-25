package com.example.expensetracker.ui.notifications

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.R
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.data.entity.Transaction
import com.example.expensetracker.data.entity.Category
import com.example.expensetracker.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.launch
import java.util.*

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private var entryType = "Expense" // Default entry type

    private lateinit var appDatabase: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationsBinding.bind(view)

        activity?.let { context ->
            appDatabase = AppDatabase.getDatabase(context) // Initialize Room Database
        }

        val etAmount = binding.etAmount
        val etCategory = binding.etCategory
        val datePicker = binding.datePicker
        val btnSubmit = binding.btnSubmit
        val radioGroup = binding.radioGroup

        // Handle Expense/Income Toggle
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = group.findViewById(checkedId)
            entryType = radioButton.text.toString()
        }

        // Date Picker Setup
        datePicker.setOnClickListener {
            val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%02d/%02d/%04d", month + 1, dayOfMonth, year)
                datePicker.setText(formattedDate)
            }

            DatePickerDialog(
                requireContext(),
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Submit Button Logic
        btnSubmit.setOnClickListener {
            val amountText = etAmount.text.toString()
            val category = etCategory.text.toString()
            val selectedDate = datePicker.text.toString()

            if (amountText.isNotEmpty() && category.isNotEmpty() && selectedDate.isNotEmpty()) {
                try {
                    val amount = amountText.toDouble()

                    // Retrieve the category ID by name
                    lifecycleScope.launch {
                        var categoryEntity = appDatabase.categoryDao().getCategoryByName(category)

                        if (categoryEntity == null) {
                            // If category does not exist, create a new one
                            categoryEntity = Category(name = category)
                            val categoryId = appDatabase.categoryDao().insert(categoryEntity) // Insert and get the ID
                            categoryEntity.id = categoryId // Update the category entity with the newly generated ID
                            // Notify the user that a new category was created
                            Toast.makeText(requireContext(), "New category added", Toast.LENGTH_SHORT).show()
                        }

                        // Create a new Transaction object with the categoryId
                        val transaction = Transaction(
                            categoryId = categoryEntity.id, // Use the categoryId retrieved or newly created
                            amount = amount,
                            date = selectedDate,
                            entryType = entryType
                        )

                        // Insert the transaction into the database using a coroutine
                        appDatabase.transactionDao().insert(transaction)
                        Toast.makeText(requireContext(), "Transaction Added", Toast.LENGTH_SHORT).show()
                    }

                    // Clear the input fields after submission
                    etAmount.text?.clear()
                    etCategory.text?.clear()
                    datePicker.text?.clear()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
