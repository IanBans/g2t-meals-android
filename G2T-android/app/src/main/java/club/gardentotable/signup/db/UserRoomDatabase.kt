package club.gardentotable.signup.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null


        fun getDatabase(context: Context, scope: CoroutineScope): UserRoomDatabase {

            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_database"
                    ).addCallback(
                        UserDatabaseCallback(
                            scope
                        )
                    )
                        .build()
                    INSTANCE = instance
                    instance

                }

        }

        private class UserDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.userDAO())
                    }
                }
            }

            suspend fun populateDatabase(userDao: UserDAO) {
                //Delete all content here
                userDao.deleteAll()

                //add sample users
                var user = User(0, "Chad", "Test")
                userDao.insert(user)
                user = User(1, "James", "Joyce")
                userDao.insert(user)


                //TODO: add more users
            }
        }


    }
}