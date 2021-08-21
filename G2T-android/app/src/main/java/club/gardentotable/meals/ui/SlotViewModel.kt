package club.gardentotable.meals.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import club.gardentotable.meals.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SlotViewModel(app:Application): AndroidViewModel(app) {

        private val repository: MemberRepository

        val allSlots : LiveData<List<Slot>>

        init {
            val db =  MemberRoomDatabase.getDatabase(app, viewModelScope)
            val memberDAO = db.memberDAO()
            val slotDAO = db.slotDAO()
            repository = MemberRepository(memberDAO, slotDAO)
            allSlots = runBlocking {  repository.getAllSlotsByDate() }

        }

        fun insert(context: Context, slot: Slot) = viewModelScope.launch {
            repository.insertSlot(slot)

            launch(Dispatchers.IO) {
                repository.addSlot()
                repository.exportToJSON(context)
            }

        }

    fun signup(slot : Slot) = viewModelScope.launch {
        if(slot.assignee == null && slot.slotID != null) {
            launch(Dispatchers.IO) {
                repository.signupUser(slot)

            }
        }
    }

    fun drop(slot : Slot) = viewModelScope.launch {
        //add check that current user matches
            launch(Dispatchers.IO) {
                repository.dropSlot(slot)

            }
    }

    fun export(context: Context) = viewModelScope.launch {

        launch(Dispatchers.IO) {
            repository.exportToJSON(context)
        }

    }

    }