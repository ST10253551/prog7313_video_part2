package vcmsa.projects.prog7313_prog2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import vcmsa.projects.prog7313_prog2.R
import vcmsa.projects.prog7313_prog2.AppDatabase
import vcmsa.projects.prog7313_prog2.MonthlyGoal
import vcmsa.projects.prog7313_prog2.ExpenseEntity
import kotlinx.coroutines.*
import vcmsa.projects.prog7313_prog2.Display_CategoryActivity
import vcmsa.projects.prog7313_prog2.FinancialActivity
import vcmsa.projects.prog7313_prog2.databinding.ActivityGoalBinding
import vcmsa.projects.prog7313_prog2.databinding.ActivityMonthlyBinding

class Monthly_Activity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var progressBarMax: ProgressBar
    private lateinit var progressBarMin: ProgressBar
    private lateinit var goalAmountTextView: TextView
    private lateinit var addGoalButton: Button
    private lateinit var setGoalsButton: Button
    private lateinit var monthSpinner: Spinner
    private lateinit var binding: ActivityMonthlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly)


            // Inflate the layout using View Binding
            binding = ActivityMonthlyBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Button clicks for navigation to other activities
            binding.DebtNav.setOnClickListener {
                // Navigate to DebtTrackingActivity
                val intent = Intent(this, Display_CategoryActivity::class.java)
                startActivity(intent)
                finish() // Close this activity after opening the next
            }

            binding.FinancialNav.setOnClickListener {
                // Navigate to FinancialActivity
                val intent = Intent(this, FinancialActivity::class.java)
                startActivity(intent)
                finish()
            }

        val inputGoal = findViewById<EditText>(R.id.inputMonthlyGoal)
        val inputLimit = findViewById<EditText>(R.id.inputMonthlyLimit)
        val btnApply = findViewById<Button>(R.id.btnApplyGoals)

        val monthlyPB = findViewById<ProgressBar>(R.id.MonthlyPB)
        val tilNxtGoalPB = findViewById<ProgressBar>(R.id.TilNxtGoalPB)

        val curAmtText = findViewById<TextView>(R.id.curMthlAmtText)
        val goalText = findViewById<TextView>(R.id.WklyGoalText)
        val amtTowardGoal = findViewById<TextView>(R.id.amtTwrdsGoal)
        val nxtGoalText = findViewById<TextView>(R.id.NxtGoal)

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "Prog7313_Prog2_database").build()

        progressBarMax = monthlyPB
        progressBarMin = tilNxtGoalPB
        goalAmountTextView = findViewById(R.id.goalAmountTextView)
        addGoalButton = findViewById(R.id.AddGoalsBtn)
        setGoalsButton = findViewById(R.id.MthlyBtn)
        monthSpinner = findViewById(R.id.monthSpinner)

        monthSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getMonths())

        setGoalsButton.setOnClickListener {
            val month = monthSpinner.selectedItem.toString()
            val goal = MonthlyGoal(month = month, maxSpending = 500.0, minGoal = 300.0)

            CoroutineScope(Dispatchers.IO).launch {
                database.budgetDao().insertMonthlyGoal(goal)
                updateProgressBars()
            }
        }

        addGoalButton.setOnClickListener {
            val selectedMonth = monthSpinner.selectedItem.toString()
            val expense = ExpenseEntity(
                expenseTitle = "Test Title",
                expenseDescription = "Test Description",
                expenseCategory = "Test Category",
                expenseAmount = "100.0",
                expenseDateTime = "2025-05-01",
                expenseImage = null
            )

            CoroutineScope(Dispatchers.IO).launch {
                database.budgetDao().insertExpenseEntity(expense)
                updateProgressBars()
            }
        }

        btnApply.setOnClickListener {
            val goal = inputGoal.text.toString().toIntOrNull()
            val limit = inputLimit.text.toString().toIntOrNull()

            if (goal != null && limit != null && goal > 0 && limit > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    val monthYear = getCurrentMonthYear()
                    val currentSaved = database.budgetDao().getTotalExpensesForMonth(monthYear) ?: 0.0

                    val progressToGoal = (currentSaved * 100 / goal).toInt().coerceAtMost(100)
                    val progressToLimit = (currentSaved * 100 / limit).toInt().coerceAtMost(100)

                    runOnUiThread {
                        monthlyPB.progress = progressToGoal
                        tilNxtGoalPB.progress = progressToLimit
                        curAmtText.text = "R${currentSaved}"
                        goalText.text = "R${goal}"
                        amtTowardGoal.text = "R${currentSaved}"
                        nxtGoalText.text = "R${limit}"
                    }
                }
            } else {
                Toast.makeText(this, "Please enter valid numbers for both fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMonths(): List<String> {
        return listOf("January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December")
    }

    private fun getCurrentMonthYear(): String {
        val monthIndex = monthSpinner.selectedItemPosition + 1
        val formattedMonth = String.format("%02d", monthIndex)
        val year = "2025" // Replace with dynamic year if needed
        return "$year-$formattedMonth"
    }

    private suspend fun updateProgressBars() {
        val monthName = monthSpinner.selectedItem.toString()
        val monthYear = getCurrentMonthYear()

        val goal = database.budgetDao().getMonthlyGoal(monthName)
        val expenses = database.budgetDao().getTotalExpensesForMonth(monthYear) ?: 0.0

        goal?.let {
            val maxProgress = (expenses / it.maxSpending * 100).toInt().coerceAtMost(100)
            val minProgress = (expenses / it.minGoal * 100).toInt().coerceAtMost(100)

            runOnUiThread {
                progressBarMax.progress = maxProgress
                progressBarMin.progress = minProgress
                goalAmountTextView.text = "Max: R${it.maxSpending}, Min: R${it.minGoal}"
            }
        }
    }
}
