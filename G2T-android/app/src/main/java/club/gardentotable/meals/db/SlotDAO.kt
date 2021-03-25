package club.gardentotable.meals.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SlotDAO {

    @Query("SELECT * FROM slot_table")
    fun getAllSlots(): LiveData<List<Slot>>

    @Query("SELECT * FROM slot_table")
    fun getAllSlotsAsList(): List<Slot>

    @Query("SELECT * FROM slot_table ORDER BY date ")
    fun getAllSlotsByDate(): LiveData<List<Slot>>

    @Query("DELETE FROM slot_table")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(slot: Slot)

    @Query("UPDATE slot_table SET assignee = :member WHERE taskID = :slotID ")
    suspend fun assignSlotToUser(slotID: Int, member: Member)
}