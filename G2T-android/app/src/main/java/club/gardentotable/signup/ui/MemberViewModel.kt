package club.gardentotable.signup.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import club.gardentotable.signup.db.Member
import club.gardentotable.signup.db.MemberRepository
import club.gardentotable.signup.db.MemberRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberViewModel(app: Application): AndroidViewModel(app) {
    private val repository: MemberRepository

    val allMembers : LiveData<List<Member>>


    init {
        val membersDAO = MemberRoomDatabase.getDatabase(app, viewModelScope).memberDAO()
        repository = MemberRepository(membersDAO)
        allMembers = membersDAO.getAllOrderedLast()

    }

    fun insert(context: Context, member: Member) = viewModelScope.launch {
        repository.insert(member)

        launch(Dispatchers.IO) {
            repository.addMoreMembers()
            repository.exportToJSON(context)

        }



    }



}