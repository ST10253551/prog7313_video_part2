package vcmsa.projects.prog7313_prog2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vcmsa.projects.prog7313_prog2.databinding.ActivityAddExpenseBinding
import java.io.ByteArrayOutputStream
import java.util.*

class Add_ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding

    private val expenseDAO by lazy {
        AppDatabase.getDatabase(applicationContext).expenseDao()
    }

    private var selectedImageBytes: ByteArray? = null

    // Image picker result launcher
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val imageUri = result.data?.data
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        binding.ExpenseImage.setImageBitmap(bitmap)
        selectedImageBytes = bitmapToByteArray(bitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up category spinner
        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            val categories = db.categoryDao().getAllCategoryNames()

            // If no categories exist, show a placeholder
            val categoryList = if (categories.isEmpty()) listOf("No Categories Found") else categories

            val spinnerAdapter = ArrayAdapter(
                this@Add_ExpenseActivity,
                android.R.layout.simple_spinner_dropdown_item,
                categoryList
            )
            binding.ExpenseCategory.adapter = spinnerAdapter
        }

        // Date and Time Picker combined
        binding.Date.setOnClickListener { showDateTimePicker() }

        // Image Picker
        binding.btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        // Save expense logic
        binding.buttonsave.setOnClickListener {
            val title = binding.Expense.text.toString()
            val amount = binding.ExpenseAmount.text.toString()
            val dateTime = binding.Date.text.toString()
            val desc = binding.ExpenseDescription.text.toString()
            val category = binding.ExpenseCategory.selectedItem.toString()

            if (title.isBlank() || amount.isBlank() || dateTime.isBlank()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expense = ExpenseEntity(
                expenseTitle = title,
                expenseAmount = amount,
                expenseDateTime = dateTime,
                expenseDescription = desc,
                expenseCategory = category,
                expenseImage = selectedImageBytes
            )

            lifecycleScope.launch(Dispatchers.IO) {
                expenseDAO.insertExpense(expense)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Add_ExpenseActivity, "Expense saved!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Add_ExpenseActivity, Display_ExpenseActivity::class.java))
                    finish()
                }
            }
        }

        // Back to report navigation
        binding.BackToReport.setOnClickListener {
            val intent = Intent(this, FinancialActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            TimePickerDialog(this, { _, hour, minute ->
                val dateTime = String.format("%04d-%02d-%02d %02d:%02d", year, month + 1, day, hour, minute)
                binding.Date.setText(dateTime)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
