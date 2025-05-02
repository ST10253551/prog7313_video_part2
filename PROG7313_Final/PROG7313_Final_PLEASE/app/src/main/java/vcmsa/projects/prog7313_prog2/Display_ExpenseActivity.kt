package vcmsa.projects.prog7313_prog2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import vcmsa.projects.prog7313_prog2.databinding.ActivityDisplayExpenseBinding
import java.util.*

class Display_ExpenseActivity : AppCompatActivity(), ExpenseAdapter.OnEventClickListener {

    private lateinit var binding: ActivityDisplayExpenseBinding
    private lateinit var adapter: ExpenseAdapter
    private val expenseDAO by lazy { AppDatabase.getDatabase(applicationContext).expenseDao() }

    private var fromDate: String = ""
    private var toDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up ViewBinding
        binding = ActivityDisplayExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView and adapter
        adapter = ExpenseAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe all expenses initially
        expenseDAO.getAllExpensesLive().observe(this) {
            adapter.submitList(it)
        }

        // Date picker buttons
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
                    val filtered = expenseDAO.getExpensesBetweenDates(fromDate, toDate)
                    adapter.submitList(filtered)
                }
            }
        }

        // Navigation buttons
        binding.Graph.setOnClickListener {
            startActivity(Intent(this, FinancialActivity::class.java))
            finish()
        }

        binding.addIncomeBtn.setOnClickListener {
            startActivity(Intent(this, Display_IncomeActivity::class.java))
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

        binding.AddExpense.setOnClickListener {
            startActivity(Intent(this, Add_ExpenseActivity::class.java))
            finish()
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            val selectedDate = String.format("%04d-%02d-%02d 00:00:00", year, month + 1, day)
            onDateSelected(selectedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onDeleteClick(event: ExpenseEntity) {
        // TODO: Implement delete logic if needed
    }

    override fun onEditClick(event: ExpenseEntity) {
        // TODO: Implement edit logic if needed
    }
}
