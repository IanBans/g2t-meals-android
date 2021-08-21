package club.gardentotable.meals.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import club.gardentotable.meals.db.Member
import club.gardentotable.meals.db.MemberRepository
import club.gardentotable.meals.db.MemberRoomDatabase
import club.gardentotable.meals.db.Slot
import kotlinx.coroutines.*

class MemberViewModel(app: Application): AndroidViewModel(app) {
    private val repository: MemberRepository

    private val allMembers : LiveData<List<Member>>
    val currentUserSlots :LiveData<List<Slot>>


    init {
        val membersDB = MemberRoomDatabase.getDatabase(app, viewModelScope)
        repository = MemberRepository(membersDB.memberDAO(), membersDB.slotDAO())
        allMembers = runBlocking { membersDB.memberDAO().getAllOrderedLast() }
        currentUserSlots = runBlocking { getCurrentUserSlots() }



    }

   private suspend fun getCurrentUserSlots(): LiveData<List<Slot>> = withContext(Dispatchers.IO) {
      return@withContext repository.getCurrentSlotsAssigned()
    }

    fun insert(context: Context, member: Member) = viewModelScope.launch {
        repository.insertMember(member)

        launch(Dispatchers.IO) {
            repository.addMoreMembers()
            repository.exportToJSON(context)

        }


    }

}