package club.gardentotable.meals.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import club.gardentotable.meals.db.Member
import club.gardentotable.meals.db.SharedRepository
import club.gardentotable.meals.db.MemberRoomDatabase
import club.gardentotable.meals.db.Slot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class MemberViewModel(private val repository: SharedRepository): ViewModel() {


    val currentUserSlots : LiveData<List<Slot>> =  runBlocking {  getCurrentUserSlots().asLiveData() }


    private suspend fun getCurrentUser(): Member = withContext(Dispatchers.IO) {

        return@withContext repository.getCurrentUser()

    }
   private suspend fun getCurrentUserSlots(): Flow<List<Slot>> = withContext(Dispatchers.IO) {

       return@withContext repository.getCurrentSlotsAssigned()
    }

    fun insert(context: Context, member: Member) = viewModelScope.launch {
        repository.insertMember(member)

        launch(Dispatchers.IO) {
            repository.addMoreMembers()
            repository.exportToJSON(context)

        }


    }

    fun export(context: Context) = viewModelScope.launch {

        launch(Dispatchers.IO) {
            repository.exportToJSON(context)
        }

    }
}

class MemberViewModelFactory constructor(private val repository: SharedRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
           MemberViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("MemberViewModel Not Found")
        }

    }
}