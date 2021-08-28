package club.gardentotable.meals

import android.app.Application
import club.gardentotable.meals.db.MemberRoomDatabase
import club.gardentotable.meals.db.SharedRepository
import kotlinx.coroutines.MainScope


class SignupApplication : Application() {
    private val database by lazy { MemberRoomDatabase.getDatabase(this) }
    val repository by lazy { SharedRepository(database.memberDAO(), database.slotDAO()) }

}