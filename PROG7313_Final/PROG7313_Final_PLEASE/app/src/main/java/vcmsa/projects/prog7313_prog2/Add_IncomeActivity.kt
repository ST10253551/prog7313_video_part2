package vcmsa.projects.prog7313_prog2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vcmsa.projects.prog7313_prog2.databinding.ActivityAddIncomeBinding
import java.io.ByteArrayOutputStream
import java.util.*

class Add_IncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddIncomeBinding

    private val incomeDAO by lazy {
        AppDatabase.getDatabase(applicationContext).incomeDao()
    }

    private var selectedImageBytes: ByteArray? = null

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
        binding = ActivityAddIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Show date-time picker
        binding.incomeDateInput.setOnClickListener {
            showDateTimePicker()
        }

        // Image picker
        binding.selectIncomeImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        // Save income
        binding.saveIncomeButton.setOnClickListener {
            val title = binding.incomeNameInput.text.toString()
            val amount = binding.incomeAmountInput.text.toString()
            val dateTime = binding.incomeDateInput.text.toString()

            if (title.isBlank() || amount.isBlank() || dateTime.isBlank()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val income = IncomeEntity(
                incomeTitle = title,
                incomeAmount = amount,
                incomeDateTime = dateTime,
                incomeImage = selectedImageBytes
            )

            lifecycleScope.launch(Dispatchers.IO) {
                incomeDAO.insertIncome(income)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Add_IncomeActivity, "Income saved!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Add_IncomeActivity, Display_IncomeActivity::class.java))
                    finish()
                }
            }
        }

        // Back button
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
                binding.incomeDateInput.setText(dateTime)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
