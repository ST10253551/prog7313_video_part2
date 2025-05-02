package vcmsa.projects.prog7313_prog2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDAO {

    @Insert
    suspend fun insertIncome(income: IncomeEntity)

    @Query("SELECT * FROM Income")
    fun getAllIncomeLive(): LiveData<List<IncomeEntity>>

    @Query("SELECT * FROM Income ORDER BY incomeDateTime ASC")
    suspend fun getAllIncome(): List<IncomeEntity>

    @Query("SELECT * FROM Income WHERE incomeDateTime BETWEEN :startDate AND :endDate ORDER BY incomeDateTime ASC")
    suspend fun getIncomeBetweenDates(startDate: String, endDate: String): List<IncomeEntity>
}
