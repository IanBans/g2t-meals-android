package club.gardentotable.meals.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.squareup.moshi.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.*

class MemberRepository(private val memberDAO: MemberDAO, private val slotDAO: SlotDAO) {

    private val moshi = Moshi.Builder().add(LocalDateAdapter()).add(Date::class.java, Rfc3339DateJsonAdapter()).add(KotlinJsonAdapterFactory()).build()
    private val memberList = Types.newParameterizedType(List::class.java, Member::class.java)
    private val slotList = Types.newParameterizedType(List::class.java, Slot::class.java)
    private val memberListAdapter : JsonAdapter<List<Member>> = moshi.adapter(memberList)
    private val slotListAdapter : JsonAdapter<List<Slot>> = moshi.adapter(slotList)
    suspend fun insertMember(member: Member) {
        memberDAO.insert(member)
    }

    /* exports the list of members to json using moshi*/
    fun exportToJSON(context: Context)  {

        val filename : String = context.filesDir.toString()+"/dbtest.json"
        Log.i("TEST", "LOGGED TO FILE at $filename")
        context.openFileOutput("dbtest.json", Context.MODE_PRIVATE).use { writer ->
            writer.write("{\n\"members_data\":".toByteArray())
            writer.write(memberListAdapter.toJson(memberDAO.getAllAsList()).toByteArray())
            writer.write(",\n\"slots_data\":".toByteArray())
            writer.write(slotListAdapter.toJson(slotDAO.getAllSlotsAsList()).toByteArray())
            writer.write("\n}".toByteArray())
        }


    }


    suspend fun addMember(first:String, last:String, phone:String, email:String, prefDays: Array<Days>?, prefTasks: Array<Tasks>?) {
        memberDAO.insert(
            Member(null,first, last, phone, email, null, Prefs(prefDays, prefTasks))
        )


    }

    suspend fun getCurrentSlotsAssigned() : LiveData<List<Slot>> {
        //TODO: replace with Cognito current user
       return slotDAO.getAssignedSlots(memberDAO.getMatchingFirstname("Example"))
    }

    suspend fun getAllSlotsByDate() : LiveData<List<Slot>> {
        return slotDAO.getAllSlotsByDate()
    }

    suspend fun addMoreMembers() {
        val prefTest = Prefs(Array(1) {Days.MONDAY},Array(1) {Tasks.BANANA})
        val member = Member(null,"Mr.","Background", "1111111111",
            "background@test.com", 1, prefTest)
            delay(10000)
            memberDAO.insert(member)

        }

     fun addSlot() {


    }

    suspend fun insertSlot(slot: Slot) {
        slotDAO.insert(slot)
    }

    suspend fun signupUser(slot: Slot) {
        //TODO: testing, replace with Cognito current user
        if(slot.slotID != null) {
            slotDAO.assignSlotToUser(slot.slotID, memberDAO.getMatchingLastname("User"))
        }
    }

    suspend fun dropSlot(slot: Slot) {
        if(slot.slotID != null) {
            slotDAO.dropSlot(slot.slotID)
        }

    }

}
