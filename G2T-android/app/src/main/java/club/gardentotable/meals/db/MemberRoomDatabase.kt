package club.gardentotable.meals.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


@Database(entities = [Member::class, Slot::class], version = 8, exportSchema = false )
abstract class MemberRoomDatabase : RoomDatabase() {

    abstract fun memberDAO(): MemberDAO
    abstract fun slotDAO(): SlotDAO

    companion object {
        @Volatile
        private var INSTANCE: MemberRoomDatabase? = null


        fun getDatabase(context: Context, scope: CoroutineScope): MemberRoomDatabase {


            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemberRoomDatabase::class.java,
                        "member_database"
                    ).addCallback(
                        MemberDatabaseCallback(scope)
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    instance

                }

        }

        private class MemberDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateMembers(database.memberDAO())
                        populateSlots(database.slotDAO(), database.memberDAO())
                    }
                }
            }

            suspend fun populateMembers(memberDAO: MemberDAO) {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val adapter : JsonAdapter<Prefs> = moshi.adapter(Prefs::class.java)
                memberDAO.deleteAll()
                //add sample members
                var member = Member(null,"Robert", "Test", "1111111111", "chad@test.com", 1,
                    adapter.toJson(Prefs(Array(1){Days.MONDAY}, Array(1){Tasks.SETUP})))
                memberDAO.insert(member)
                member = Member(null, "James", "Joyce","2222222222", "joyce@ulysses.com", 0)
                memberDAO.insert(member)

                //TODO: add more members
            }

            suspend fun populateSlots(slotDAO: SlotDAO, memberDAO: MemberDAO) {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val dayAdapter : JsonAdapter<Days> = moshi.adapter(Days::class.java)
                val memberAdapter : JsonAdapter<Member> = moshi.adapter(Member::class.java)
                val taskAdapter: JsonAdapter<Tasks> = moshi.adapter(Tasks::class.java)
                slotDAO.deleteAll()
                //add sample slots
                val slot1 = Slot(null,dayAdapter.toJson(Days.TUESDAY), "2020-05-06", taskAdapter.toJson(Tasks.CLEANUP), memberAdapter.toJson(memberDAO.getMatchingFirstname("Robert")))
                slotDAO.insert(slot1)
                var slot2 = Slot(null,dayAdapter.toJson(Days.TUESDAY), "2020-05-06", taskAdapter.toJson(Tasks.SETUP))
                slotDAO.insert(slot2)
                slot2 = Slot(null,dayAdapter.toJson(Days.TUESDAY), "2020-05-06", taskAdapter.toJson(Tasks.LEAD))
                slotDAO.insert(slot2)
                 slot2 = Slot(null,dayAdapter.toJson(Days.TUESDAY), "2020-05-06", taskAdapter.toJson(Tasks.BANANA))
                slotDAO.insert(slot2)
                //TODO: add more slots
            }
        }


    }
}