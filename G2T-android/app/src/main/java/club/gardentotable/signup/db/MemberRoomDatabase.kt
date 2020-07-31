package club.gardentotable.signup.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Member::class], version = 2, exportSchema = false)
abstract class MemberRoomDatabase : RoomDatabase() {

    abstract fun memberDAO(): MemberDAO

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
                        MemberDatabaseCallback(
                            scope
                        )
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
                        populateDatabase(database.memberDAO())
                    }
                }
            }

            suspend fun populateDatabase(memberDAO: MemberDAO) {
                //Delete all content here
                memberDAO.deleteAll()

                //add sample members
                var member = Member(0, "Chad", "Test", "1111111111", "chad@test.com", 1)
                memberDAO.insert(member)
                member = Member(1, "James", "Joyce","2222222222", "joyce@ulysses.com", 0 )
                memberDAO.insert(member)


                //TODO: add more members
            }
        }


    }
}