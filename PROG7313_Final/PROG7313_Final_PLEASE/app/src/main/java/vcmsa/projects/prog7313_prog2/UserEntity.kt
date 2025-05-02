package vcmsa.projects.prog7313_prog2

import androidx.room.Entity
import androidx.room.PrimaryKey

//this annotation marks the class as an entity (table) in your db, user table
@Entity(tableName = "user")
//this is a data class which is automatically used to represent the user table in your room db
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String
)

