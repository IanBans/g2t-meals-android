package club.gardentotable.meals.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import club.gardentotable.meals.db.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class SlotViewModel(private val repository: SharedRepository): ViewModel(){


        val allSlots : LiveData<List<Slot>> = runBlocking { getAllSlotsByDate().asLiveData() }


    fun insert(context: Context, slot: Slot) = viewModelScope.launch {
            repository.insertSlot(slot)

            launch(Dispatchers.IO) {
                repository.addSlot()
                repository.exportToJSON(context)
            }

        }

    private suspend fun getAllSlotsByDate(): Flow<List<Slot>> = withContext(Dispatchers.IO){
        return@withContext repository.getAllSlotsByDate()

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

class SlotViewModelFactory constructor(private val repository: SharedRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SlotViewModel::class.java)) {
            SlotViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("SlotViewModel Not Found")
        }

    }
}