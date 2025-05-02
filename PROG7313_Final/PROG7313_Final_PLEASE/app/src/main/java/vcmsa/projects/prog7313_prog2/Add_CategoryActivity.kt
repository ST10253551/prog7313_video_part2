package vcmsa.projects.prog7313_prog2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vcmsa.projects.prog7313_prog2.databinding.ActivityAddCategoryBinding

class Add_CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCategoryBinding
    private lateinit var categoryDao: CategoryDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup ViewBinding
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DAO
        val db = AppDatabase.getDatabase(applicationContext)
        categoryDao = db.categoryDao()

        // Handle Save button click
        binding.saveCategoryButton.setOnClickListener {
            val categoryName = binding.categoryNameInput.text.toString().trim()

            if (categoryName.contains(" ")) {
                Toast.makeText(this, "Category must not contain spaces", Toast.LENGTH_SHORT).show()
            } else if (categoryName.isBlank()) {
                Toast.makeText(this, "Please fill in Category Name", Toast.LENGTH_SHORT).show()
            } else {
                val newCategory = CategoryEntity(categoryName = categoryName)
                insertCategory(newCategory)
            }
        }

        // Handle back button click
        binding.BackToDebtDetails.setOnClickListener {
            val intent = Intent(this, Display_CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun insertCategory(category: CategoryEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            categoryDao.insertCategory(category)
            launch(Dispatchers.Main) {
                Toast.makeText(this@Add_CategoryActivity, "Category saved", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Add_CategoryActivity, Display_CategoryActivity::class.java))
                finish()
            }
        }
    }
}