package vcmsa.projects.prog7313_prog2



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_goals")
data class MonthlyGoal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val month: String,
    val maxSpending: Double,
    val minGoal: Double
)
