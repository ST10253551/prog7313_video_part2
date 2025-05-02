package vcmsa.projects.prog7313_prog2
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BudgetDAO {


    @Insert
    suspend fun insertMonthlyGoal(monthlyGoal: MonthlyGoal)

    @Insert
    suspend fun insertExpenseEntity(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM monthly_goals WHERE month = :month")
    suspend fun getMonthlyGoal(month: String): MonthlyGoal?

    @Query("SELECT SUM(CAST(expenseAmount AS REAL)) FROM Expense WHERE strftime('%Y-%m', expenseDateTime) = :monthYear")
    suspend fun getTotalExpensesForMonth(monthYear: String): Double?

}