package club.gardentotable.signup.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {

    @Query("SELECT * from user_table ORDER BY uid ASC")
    fun getAllOrderedUID(): LiveData<List<User>>

    @Query("SELECT * from user_table ORDER BY last_name ASC")
    fun getAllOrderedLast(): LiveData<List<User>>

    @Query("SELECT * from user_table WHERE uid = :id")
    fun getMatchingUID(id: Int): LiveData<List<User>>

    @Query("SELECT * from user_table WHERE first_name LIKE :name")
    fun getMatchingFirstname(name: String): LiveData<List<User>>

    @Query("SELECT * from user_table WHERE last_name LIKE :name")
    fun getMatchingLastname(name: String): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)
}