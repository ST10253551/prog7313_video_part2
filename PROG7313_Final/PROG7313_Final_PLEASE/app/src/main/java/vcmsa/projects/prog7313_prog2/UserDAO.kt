package vcmsa.projects.prog7313_prog2
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {

    // Insert a new user into the database
    @Insert
    fun insertUser(userEntity: UserEntity)

    // Get user by username
    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): UserEntity?

    // Check if user exists with given username and password (for login)
    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): UserEntity?
}
