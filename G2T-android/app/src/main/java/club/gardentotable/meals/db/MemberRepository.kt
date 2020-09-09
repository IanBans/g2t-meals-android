package club.gardentotable.meals.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.squareup.moshi.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import java.util.*

class MemberRepository(private val memberDAO: MemberDAO, private val slotDAO: SlotDAO) {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(Date::class.java, Rfc3339DateJsonAdapter()).build()
    val allSlots: LiveData<List<Slot>> = slotDAO.getAllSlots()
    private val listType = Types.newParameterizedType(List::class.java, Member::class.java)
    private val memberListAdapter : JsonAdapter<List<Member>> = moshi.adapter(listType)
    suspend fun insert(member: Member) {
        memberDAO.insert(member)
    }

    suspend fun insert(slot: Slot) {
        slotDAO.insert(slot)
    }

    fun exportToJSON(context: Context)  {

        val filename : String = context.filesDir.toString()+"/dbtest.json"
        Log.i("TEST", "LOGGED TO FILE at $filename")
        context.openFileOutput("dbtest.json", Context.MODE_PRIVATE).use { writer ->
            writer.write(memberListAdapter.toJson(memberDAO.getAllAsList()).toByteArray())
        }


    }


    suspend fun addMember(first:String, last:String, phone:String, email:String, prefDays: Array<Days>?, prefTasks: Array<Tasks>?) {
        memberDAO.insert(
            Member(null,first, last, phone, email, null, Prefs(prefDays, prefTasks))
        )


    }

    suspend fun addMoreMembers() {

        val prefTest = Prefs(Array<Days>(1) {Days.MONDAY},Array<Tasks>(1) {Tasks.BANANA})
        val member1 = Member(null,"Mr.","Background", "1111111111",
            "background@test.com", 1,prefTest)
            delay(10000)
            memberDAO.insert(member1)


        }

    }
