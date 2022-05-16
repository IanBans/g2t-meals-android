package club.gardentotable.meals.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.squareup.moshi.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class SharedRepository @Inject constructor(private val memberDAO: MemberDAO, private val slotDAO: SlotDAO) {

    private val moshi = Moshi.Builder().add(LocalDateAdapter())
                        .add(Date::class.java, Rfc3339DateJsonAdapter())
                        .add(KotlinJsonAdapterFactory()).build()
    private val memberList = Types.newParameterizedType(List::class.java, Member::class.java)
    private val slotList = Types.newParameterizedType(List::class.java, Slot::class.java)
    private val memberListAdapter : JsonAdapter<List<Member>> = moshi.adapter(memberList)
    private val slotListAdapter : JsonAdapter<List<Slot>> = moshi.adapter(slotList)

    suspend fun insertMember(member: Member) {
        memberDAO.insert(member)
    }

    /* exports the list of members to json using moshi*/
    fun exportToJSON(context: Context)  {

        runBlocking {

            val filename: String = context.filesDir.toString() + "/dbtest.json"
            Log.i("EXPORT", "DB Exported to file at $filename")
            context.openFileOutput("dbtest.json", Context.MODE_PRIVATE).use { writer ->
                writer.write("{\n\"members_data\":".toByteArray())
                writer.write(memberListAdapter.toJson(memberDAO.getAllAsList()).toByteArray())
                writer.write(",\n\"slots_data\":".toByteArray())
                writer.write(slotListAdapter.toJson(slotDAO.getAllSlotsAsList()).toByteArray())
                writer.write("\n}".toByteArray())
            }
        }


    }


    suspend fun addMember(first:String, last:String, phone:String, email:String, prefDays: Array<Days>?, prefTasks: Array<Tasks>?) {
        memberDAO.insert(
            Member(null,first, last, phone, email, null, Prefs(prefDays, prefTasks))
        )


    }

    suspend fun addToDB() {
        slotDAO.deleteAll()

        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 5, 29), Tasks.INVENTORY, null))
        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 5, 29), Tasks.MOP, null))
        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 5, 29), Tasks.SHOP1, null))
        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 5, 29), Tasks.SHOP2, null))

        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 5,30),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 5,30),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 5,30),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 5,30),Tasks.CLEANUP, null))

        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,1),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,1),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,1),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,1),Tasks.CLEANUP, null))


        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,2),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,2),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,2),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,2),Tasks.CLEANUP, null))

        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,3),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,3),Tasks.CLEANUP, null))
        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,3),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,3),Tasks.BANANA, null))

        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,4),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,4),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,4),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,4),Tasks.CLEANUP, null))

        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 6, 5), Tasks.INVENTORY, null))
        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 6, 5), Tasks.MOP, null))
        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 6, 5), Tasks.SHOP1, null))
        insertSlot(Slot(null, Days.WEEKLY, LocalDate.of(2020, 6, 5), Tasks.SHOP2, null))

        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 6,6),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 6,6),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 6,6),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.SATURDAY, LocalDate.of(2020, 6,6),Tasks.CLEANUP, null))

        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,8),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,8),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,8),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.MONDAY, LocalDate.of(2020, 6,8),Tasks.CLEANUP, null))


        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,9),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,9),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,9),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.TUESDAY, LocalDate.of(2020, 6,9),Tasks.CLEANUP, null))

        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,10),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,10),Tasks.CLEANUP, null))
        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,10),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.WEDNESDAY, LocalDate.of(2020, 6,10),Tasks.BANANA, null))

        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,11),Tasks.LEAD, null))
        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,11),Tasks.BANANA, null))
        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,11),Tasks.SETUP, null))
        insertSlot(Slot(null, Days.THURSDAY, LocalDate.of(2020, 6,11),Tasks.CLEANUP, null))

    }

    fun getCurrentUser() : Member {
        return memberDAO.getMatchingFirstname("Example")
    }
    suspend fun getCurrentSlotsAssigned() : Flow<List<Slot>> {
        //TODO: replace with Cognito current user
       return slotDAO.getAssignedSlots(getCurrentUser())
    }

    suspend fun getAllSlotsByDate() : Flow<List<Slot>> {
        return slotDAO.getAllSlotsByDate()
    }

    fun getAllOrderedLast() : Flow<List<Member>> {
        return memberDAO.getAllOrderedLast()
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
