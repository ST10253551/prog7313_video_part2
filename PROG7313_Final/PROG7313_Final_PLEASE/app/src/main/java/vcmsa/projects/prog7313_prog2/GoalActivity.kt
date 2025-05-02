package vcmsa.projects.prog7313_prog2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.prog7313_prog2.ui.Monthly_Activity
import vcmsa.projects.prog7313_prog2.databinding.ActivityGoalBinding

class GoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityGoalBinding.inflate(layoutInflater)
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

        binding.WklyBtn.setOnClickListener {
            // Reload the current activity (GoalActivity)
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.MthlyBtn.setOnClickListener {
            // Navigate to Monthly_Activity
            val intent = Intent(this, Monthly_Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.YrlyBtn.setOnClickListener {
            // Navigate to Yearly_Activity
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

    }
}
