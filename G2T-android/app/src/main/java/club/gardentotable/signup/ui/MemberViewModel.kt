package club.gardentotable.signup.ui

import android.app.Application
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
        allMembers = repository.allMembers

    }

    fun insert(member: Member) = viewModelScope.launch {
        repository.insert(member)
        launch(Dispatchers.IO) {
            repository.addMoreMembers()
        }
    }



}