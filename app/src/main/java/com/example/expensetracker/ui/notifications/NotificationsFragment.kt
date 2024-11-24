package com.example.expensetracker.ui.notifications

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentNotificationsBinding
import java.util.*

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private var entryType = "Expense" // Default entry type

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationsBinding.bind(view)

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
            val amount = etAmount.text.toString()
            val category = etCategory.text.toString()
            val selectedDate = datePicker.text.toString()

            if (amount.isNotEmpty() && category.isNotEmpty() && selectedDate.isNotEmpty()) {
                Toast.makeText(requireContext(), "Type: $entryType, Amount: $amount, Category: $category, Date: $selectedDate", Toast.LENGTH_SHORT).show()
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
