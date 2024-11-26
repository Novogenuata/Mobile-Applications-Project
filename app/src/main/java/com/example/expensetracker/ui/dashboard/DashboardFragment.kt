package com.example.expensetracker.ui.dashboard

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.R
import com.example.expensetracker.data.database.AppDatabase
import com.example.expensetracker.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.example.expensetracker.ui.SharedViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the database
        activity?.let { context ->
            appDatabase = AppDatabase.getDatabase(context)
        }
//
        // Observe the shared ViewModel
        sharedViewModel.transactionAdded.observe(viewLifecycleOwner, Observer { date ->
            fetchTransactionData(date)
        })

        // Display message to select a date initially
        binding.barChart.setNoDataText("Please select a date to view your analytics.")
        binding.barChart.invalidate()

        // Setup CalendarView logic
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            fetchTransactionData(selectedDate)
        }

        // Initialize and configure Bar Chart
        setupBarChart(binding.barChart)

        // Send Email Button logic
        binding.sendEmailButton.setOnClickListener {
            Toast.makeText(context, "Email Sent!", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    // Setup Bar Chart configurations
    private fun setupBarChart(barChart: BarChart) {
        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)
        barChart.animateY(1400, Easing.EaseInOutQuad)

        // X-Axis settings
        val xAxis: XAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.labelRotationAngle = 0f

        // Y-Axis settings
        val leftAxis: YAxis = barChart.axisLeft
        leftAxis.setLabelCount(5, false)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f

        val rightAxis: YAxis = barChart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(5, false)
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f

        barChart.legend.isEnabled = false

        // Clear any existing data
        barChart.clear()
    }

    private fun fetchTransactionData(selectedDate: String) {
        lifecycleScope.launch {
            try {
                // Correct the date format here
                val selectedDateFormatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDate)
                val formattedDateString = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(selectedDateFormatted)

                // Log the selected date and formatted date string
                Log.d("DashboardFragment", "Selected Date: $selectedDate")
                Log.d("DashboardFragment", "Formatted Date String: $formattedDateString")

                // Fetch transactions for the formatted date
                val transactions = appDatabase.transactionDao().getTransactionsByDate(formattedDateString)
                Log.d("DashboardFragment", "Fetched Transactions: $transactions")

                val income = transactions.filter { it.entryType == "Income" }.sumOf { it.amount }.toFloat()
                val expense = transactions.filter { it.entryType == "Expense" }.sumOf { it.amount }.toFloat()
                val total = income - expense

                Log.d("DashboardFragment", "Income: $income, Expense: $expense, Total: $total")

                updateBarChartData(income, expense, total)

            } catch (e: Exception) {
                Toast.makeText(context, "Error fetching data: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("DashboardFragment", "Error fetching data", e)
            }
        }
    }

    private fun updateBarChartData(income: Float, expense: Float, total: Float) {
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        entries.add(BarEntry(0f, income))
        labels.add("Income")

        entries.add(BarEntry(1f, expense))
        labels.add("Expense")

        entries.add(BarEntry(2f, total))
        labels.add("Total")

        Log.d("DashboardFragment", "Bar Entries: $entries")

        if (entries.isEmpty()) {
            binding.barChart.clear()
            binding.barChart.setNoDataText("No data available")
            binding.barChart.invalidate()
            return
        }

        val dataSet = BarDataSet(entries, "Financial Summary")
        val colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.mint),
            ContextCompat.getColor(requireContext(), R.color.red),
            ContextCompat.getColor(requireContext(), R.color.yellow)
        )
        dataSet.colors = colors

        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "$${String.format("%.2f", value)}"
            }
        }

        val data = BarData(dataSet)
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)

        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        Log.d("DashboardFragment", "BarData: $data")

        binding.barChart.data = data
        binding.barChart.notifyDataSetChanged()
        binding.barChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
