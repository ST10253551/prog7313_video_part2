package vcmsa.projects.prog7313_prog2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MonthlyGoal::class, UserEntity::class, ExpenseEntity::class, IncomeEntity::class, CategoryEntity::class],
    version = 4, // Incremented version to reflect schema change
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDAO // Added Category
    abstract fun userDao(): UserDAO
    abstract fun expenseDao(): ExpenseDAO
    abstract fun incomeDao(): IncomeDAO  // Added IncomeDAO
    abstract fun budgetDao(): BudgetDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Prog7313_Prog2_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
