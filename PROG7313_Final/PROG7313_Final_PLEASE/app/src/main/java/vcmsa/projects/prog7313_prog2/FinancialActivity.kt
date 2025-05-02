package vcmsa.projects.prog7313_prog2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.prog7313_prog2.ui.Monthly_Activity
import vcmsa.projects.prog7313_prog2.databinding.ActivityFinancialBinding


class FinancialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinancialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding setup
        binding = ActivityFinancialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.GoalsNav.setOnClickListener {
            val intent = Intent(this, Monthly_Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.DebtNav.setOnClickListener {
            val intent = Intent(this, Display_CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.Graph.setOnClickListener {
            val intent = Intent(this, FinancialActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.addExpenseBtn.setOnClickListener {
            val intent = Intent(this, Display_ExpenseActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.IncomeBtn.setOnClickListener {
            val intent = Intent(this, Display_IncomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
