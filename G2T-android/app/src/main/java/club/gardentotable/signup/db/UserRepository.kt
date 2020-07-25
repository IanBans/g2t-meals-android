package club.gardentotable.signup.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.squareup.moshi.*
import kotlinx.coroutines.delay

class UserRepository(private val userDAO: UserDAO) {

    val allUsers: LiveData<List<User>> = userDAO.getAllOrderedUID()
    private val listType = Types.newParameterizedType(
        List::class.java, User::class.java
    )
    suspend fun insert(user: User) {
        userDAO.insert(user)
    }

    fun getUserData(context: Context): List<User> {
        val text  = "path to file"
        val moshi = Moshi.Builder().build()
        val adapter : JsonAdapter<List<User>> = moshi.adapter(listType)
        return adapter.fromJson(text) ?: emptyList()
    }

    suspend fun addMoreUsers() {
        val user1 = User(54,"Mr.","Background")
            delay(10000)
            userDAO.insert(user1)
        }

    }
