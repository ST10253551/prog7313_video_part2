package vcmsa.projects.prog7313_prog2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import vcmsa.projects.prog7313_prog2.R


class AddCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        val db = AppDatabase.getDatabase(this)
        val categoryInput = findViewById<EditText>(R.id.categoryNameInput)
        val saveButton = findViewById<Button>(R.id.saveCategoryButton)

        saveButton.setOnClickListener {
            val name = categoryInput.text.toString().trim()
            if (name.isNotEmpty()) {
                lifecycleScope.launch {
                    db.categoryDao().insertCategory(CategoryEntity(categoryName = name))
                    finish()
                }
            }
        }
    }
}