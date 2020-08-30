package club.gardentotable.meals.db

import androidx.lifecycle.LiveData

class SlotRepository(private val slotDAO: SlotDAO) {

    val allSlots:LiveData<List<Slot>> = slotDAO.getAllSlots()

    suspend fun insert(slot: Slot) {
        slotDAO.insert(slot)
    }

    suspend fun addSlot() {


    }

}
