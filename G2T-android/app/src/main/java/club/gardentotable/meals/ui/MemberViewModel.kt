package club.gardentotable.meals.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import club.gardentotable.meals.db.Member
import club.gardentotable.meals.db.MemberRepository
import club.gardentotable.meals.db.MemberRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberViewModel(app: Application): AndroidViewModel(app) {
    private val repository: MemberRepository

    val allMembers : LiveData<List<Member>>


    init {
        val membersDB = MemberRoomDatabase.getDatabase(app, viewModelScope)
        repository = MemberRepository(membersDB.memberDAO(), membersDB.slotDAO())
        allMembers = membersDB.memberDAO().getAllOrderedLast()

    }

    fun insert(context: Context, member: Member) = viewModelScope.launch {
        repository.insertMember(member)

        launch(Dispatchers.IO) {
            repository.addMoreMembers()
            repository.exportToJSON(context)

        }


    }

}