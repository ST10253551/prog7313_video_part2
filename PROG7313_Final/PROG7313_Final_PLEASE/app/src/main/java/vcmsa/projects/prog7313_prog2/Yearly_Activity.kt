package vcmsa.projects.prog7313_prog2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.prog7313_prog2.ui.Monthly_Activity
import vcmsa.projects.prog7313_prog2.databinding.ActivityYearlyBinding

class Yearly_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityYearlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding setup
        binding = ActivityYearlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.WklyBtn.setOnClickListener {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.MthlyBtn.setOnClickListener {
            val intent = Intent(this, Monthly_Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.YrlyBtn.setOnClickListener {
            val intent = Intent(this, Yearly_Activity::class.java)
            startActivity(intent)
            finish()
        }

        //AddGoalsBtn
        binding.AddGoalsBtn.setOnClickListener {
            val intent = Intent(this, Add_GoalActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.GoalsNav.setOnClickListener {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.DebtNav.setOnClickListener {
            val intent = Intent(this, Display_CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.FinancialNav.setOnClickListener {
            val intent = Intent(this, FinancialActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
