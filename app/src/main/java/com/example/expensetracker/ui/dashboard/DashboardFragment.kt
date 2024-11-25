package com.example.expensetracker.ui.dashboard

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup CalendarView logic
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            Toast.makeText(context, "Selected Date: $date", Toast.LENGTH_SHORT).show()
        }

        // Initialize and configure Pie Chart
        setupPieChart()

        // Send Email Button logic
        binding.sendEmailButton.setOnClickListener {
            Toast.makeText(context, "Email Sent!", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun setupPieChart() {
        val pieChart = binding.pieChart

        // General Pie Chart settings
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.setDrawCenterText(true)
        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.setHighlightPerTapEnabled(true)


        pieChart.animateY(1400, Easing.EaseInOutQuad)


        pieChart.legend.isEnabled = false


        setPieChartData(pieChart)
    }

    private fun setPieChartData(pieChart: com.github.mikephil.charting.charts.PieChart) {

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(40f, "Income"))
        entries.add(PieEntry(35f, "Expense"))
        entries.add(PieEntry(25f, "Total"))


        val dataSet = PieDataSet(entries, "Mobile OS")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f


        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.purple_200))
        colors.add(resources.getColor(R.color.Lturq))
        colors.add(resources.getColor(R.color.indigo))
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)


        pieChart.data = data


        pieChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
