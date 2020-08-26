package club.gardentotable.meals.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import club.gardentotable.meals.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SlotViewModel(app:Application): AndroidViewModel(app) {

        private val repository: SlotRepository

        val allSlots : LiveData<List<Slot>>

        init {
            val slotDAO = MemberRoomDatabase.getDatabase(app, viewModelScope).SlotDAO()
            repository = SlotRepository(slotDAO)
            allSlots = slotDAO.getAllSlots()

        }

        fun insert(context: Context, slot: Slot) = viewModelScope.launch {
            repository.insert(slot)

            launch(Dispatchers.IO) {
                repository.addSlot()

            }

        }

    }