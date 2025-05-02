package vcmsa.projects.prog7313_prog2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDAO {

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM Expense")
    fun getAllExpensesLive(): LiveData<List<ExpenseEntity>>

    @Query("SELECT * FROM Expense ORDER BY expenseDateTime ASC")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM Expense WHERE expenseDateTime BETWEEN :startDate AND :endDate ORDER BY expenseDateTime ASC")
    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenseEntity>

    @Query("SELECT expenseCategory AS category, SUM(CAST(expenseAmount AS REAL)) AS total FROM Expense GROUP BY expenseCategory")
    suspend fun getTotalPerCategory(): List<CategoryTotal>

}
