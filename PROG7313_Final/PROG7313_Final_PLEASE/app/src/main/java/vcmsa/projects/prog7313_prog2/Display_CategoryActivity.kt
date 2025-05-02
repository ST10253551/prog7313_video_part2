package vcmsa.projects.prog7313_prog2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vcmsa.projects.prog7313_prog2.databinding.ActivityDisplayCategoryBinding
import vcmsa.projects.prog7313_prog2.databinding.ActivityGoalBinding

class Display_CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayCategoryBinding
    private lateinit var categoryDao: CategoryDAO
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryDao = AppDatabase.getDatabase(applicationContext).categoryDao()

        setupRecyclerView()
        loadCategories()

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

        binding.AddCatBtn.setOnClickListener {
            startActivity(Intent(this, Add_CategoryActivity::class.java))
        }

        binding.ViewTotalsBtn.setOnClickListener {
            startActivity(Intent(this, CategoryTotals::class.java))
        }

        binding.CategoryRecyclerView.setOnClickListener {
            startActivity(Intent(this, CategoryTotals::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = CategoryAdapter(emptyList()) { category ->
            showDeleteDialog(category)
        }
        binding.CategoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.CategoryRecyclerView.adapter = adapter
    }

    private fun loadCategories() {
        lifecycleScope.launch(Dispatchers.IO) {
            val categories = categoryDao.getAllCategories()
            launch(Dispatchers.Main) {
                adapter.updateData(categories)
            }
        }
    }

    private fun showDeleteDialog(category: CategoryEntity) {
        AlertDialog.Builder(this)
            .setTitle("Delete Category")
            .setMessage("Are you sure you want to delete '${category.categoryName}'?")
            .setPositiveButton("Delete") { _, _ ->
                deleteCategory(category)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteCategory(category: CategoryEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            categoryDao.deleteCategory(category)
            loadCategories()
        }
    }
}
