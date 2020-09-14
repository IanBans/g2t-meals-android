package club.gardentotable.meals.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SlotDAO {

    @Query("SELECT * FROM slot_table")
    fun getAllSlots(): LiveData<List<Slot>>

    @Query("SELECT * FROM slot_table ORDER BY day ")
    fun getAllSlotsByDay(): LiveData<List<Slot>>

    @Query("DELETE FROM slot_table")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(slot: Slot)
}