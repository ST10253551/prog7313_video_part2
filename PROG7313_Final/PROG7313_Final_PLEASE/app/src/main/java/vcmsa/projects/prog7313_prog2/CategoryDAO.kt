package vcmsa.projects.prog7313_prog2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface CategoryDAO {

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM category_table")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM category_table WHERE categoryName = :name LIMIT 1")
    suspend fun getCategoryByName(name: String): CategoryEntity?

    @Query("SELECT categoryName FROM category_table")
    suspend fun getAllCategoryNames(): List<String>

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}

