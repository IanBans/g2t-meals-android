package club.gardentotable.meals.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import club.gardentotable.meals.db.Member
import club.gardentotable.meals.db.SharedRepository
import club.gardentotable.meals.db.MemberRoomDatabase
import club.gardentotable.meals.db.Slot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(private val repository: SharedRepository): ViewModel() {


    val currentUserSlots : LiveData<List<Slot>> =  runBlocking {  getCurrentUserSlots().asLiveData() }


    private suspend fun getCurrentUser(): Member = withContext(Dispatchers.IO) {

        return@withContext repository.getCurrentUser()

    }
   private fun getCurrentUserSlots(): Flow<List<Slot>> = withContext(Dispatchers.IO) {

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

