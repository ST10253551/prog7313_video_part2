package vcmsa.projects.prog7313_prog2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Income")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val incomeTitle: String,
    val incomeAmount: String,
    val incomeDateTime: String,  // Added this field to store selected date and time
    val incomeImage: ByteArray?
)
