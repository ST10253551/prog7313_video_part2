package vcmsa.projects.prog7313_prog2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import vcmsa.projects.prog7313_prog2.databinding.ActivityDisplayIncomeBinding
import java.util.*

class Display_IncomeActivity : AppCompatActivity(), IncomeAdapter.OnIncomeClickListener {

    private lateinit var binding: ActivityDisplayIncomeBinding
    private lateinit var adapter: IncomeAdapter
    private val incomeDAO by lazy { AppDatabase.getDatabase(applicationContext).incomeDao() }

    private var fromDate: String = ""
    private var toDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up ViewBinding
        binding = ActivityDisplayIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView and adapter
        adapter = IncomeAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe all income entries initially
        incomeDAO.getAllIncomeLive().observe(this) {
            adapter.submitList(it)
        }

        // Date picker buttons for filtering by date range
        binding.fromDateButton.setOnClickListener {
            showDatePicker { date ->
                fromDate = date
                binding.fromDateButton.text = "From: $date"
            }
        }

        binding.toDateButton.setOnClickListener {
            showDatePicker { date ->
                toDate = date
                binding.toDateButton.text = "To: $date"
            }
        }

        // Filter by date range
        binding.search.setOnClickListener {
            if (fromDate.isNotEmpty() && toDate.isNotEmpty()) {
                lifecycleScope.launch {
                    val filtered = incomeDAO.getIncomeBetweenDates(fromDate, toDate)
                    adapter.submitList(filtered)
                }
            }
        }

        // Navigation buttons for different activities
        binding.Graph.setOnClickListener {
            startActivity(Intent(this, FinancialActivity::class.java))
            finish()
        }

        binding.addExpenseBtn.setOnClickListener {
            startActivity(Intent(this, Display_ExpenseActivity::class.java))
            finish()
        }

        binding.FinancialNav.setOnClickListener {
            startActivity(Intent(this, Display_IncomeActivity::class.java))
            finish()
        }

        binding.AddIncome.setOnClickListener {
            startActivity(Intent(this, Add_IncomeActivity::class.java))
            finish()
        }
    }

    // Function to show the DatePickerDialog
    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            // Format the selected date as "yyyy-MM-dd 00:00:00"
            val selectedDate = String.format("%04d-%02d-%02d 00:00:00", year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    // Handle click events for income item actions like edit and delete
    override fun onDeleteClick(income: IncomeEntity) {
        // TODO: Implement delete logic for income
    }

    override fun onEditClick(income: IncomeEntity) {
        // TODO: Implement edit logic for income
    }
}
