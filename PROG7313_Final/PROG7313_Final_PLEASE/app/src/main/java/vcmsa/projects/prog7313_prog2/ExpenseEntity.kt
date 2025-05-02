package vcmsa.projects.prog7313_prog2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val expenseTitle: String,
    val expenseDescription: String,
    val expenseCategory: String,
    val expenseAmount: String,
    val expenseDateTime: String,
    val expenseImage: ByteArray?
)
