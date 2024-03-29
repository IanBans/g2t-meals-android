package club.gardentotable.meals.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDAO {

    @Query("SELECT * from member_table ORDER BY memberID DESC")
     fun getAllOrderedMID(): Flow<List<Member>>

    @Query("SELECT * from member_table")
    fun getAllAsList(): List<Member>

    @Query("SELECT LAST from member_table ORDER BY memberID ASC")
    fun getLargestMID(): Int

    @Query("SELECT * from member_table ORDER BY last ASC")
     fun getAllOrderedLast(): Flow<List<Member>>

    @Query("SELECT * from member_table WHERE memberID = :id")
    fun getMatchingMID(id: Int): Member

    @Query("SELECT * from member_table WHERE first LIKE :name")
     fun getMatchingFirstname(name: String): Member

    @Query("SELECT * from member_table WHERE last LIKE :name")
     fun getMatchingLastname(name: String): Member

    @Query("DELETE FROM member_table")
     suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.ABORT)
     suspend fun insert(member: Member)
}