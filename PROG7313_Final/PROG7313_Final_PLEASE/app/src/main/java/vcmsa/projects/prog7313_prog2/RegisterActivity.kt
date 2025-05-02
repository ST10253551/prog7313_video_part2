package vcmsa.projects.prog7313_prog2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.prog7313_prog2.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDAO
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // initialize the db and DAO
        db = AppDatabase.getDatabase(applicationContext)
        userDao = db.userDao()


        // ViewBinding setup
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room database and DAO
        val db = AppDatabase.getDatabase(applicationContext)
        userDao = db.userDao()

        binding.signinBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val saveButton = findViewById<Button>(R.id.btnRegister)
        val usernameEditText = findViewById<EditText>(R.id.etUsername)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val passwordEditTextCon = findViewById<EditText>(R.id.etConfirmPassword)
        val emailEditText = findViewById<EditText>(R.id.etEmail)


        saveButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordConfirmation = passwordEditTextCon.text.toString()
            val email = emailEditText.text.toString()

            // Validation
            val usernameRegex = Regex("^[A-Za-z]+$") // Only letters, no digits/symbols/spaces
            val passwordRegex =
                Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%&*.]).{8,}$") // Password constraint

            if (email.contains(" ")) {
                Toast.makeText(this, "Email must not contain spaces", Toast.LENGTH_SHORT).show()
            } else {
                when {
                    username.isBlank() || password.isBlank() -> {
                        Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT)
                            .show()
                    }

                    !username.matches(usernameRegex) -> {
                        Toast.makeText(
                            this,
                            "Username must contain only letters",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    !password.matches(passwordRegex) -> {
                        Toast.makeText(
                            this,
                            "Password must be at least 8 characters long, include uppercase, lowercase, and a special character (!@#\$%&*.)",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    password != passwordConfirmation -> {
                        Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        val user = UserEntity(username = username, password = password)
                        insertUser(user)
                    }
                }
            }

        }
    }

    private fun insertUser(user: UserEntity) {
        Thread {
            userDao.insertUser(user)  // Insert the user into the database
            runOnUiThread {
                Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show()

                // Redirect to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: Closes the registration screen so the user can't go back to it
            }
        }.start()
    }
}