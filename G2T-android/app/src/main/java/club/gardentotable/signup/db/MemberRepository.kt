package club.gardentotable.signup.db

import android.content.Context
import android.util.Log
import com.squareup.moshi.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import java.util.*

class MemberRepository(private val memberDAO: MemberDAO, private val slotDAO: SlotDAO) {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(Date::class.java, Rfc3339DateJsonAdapter()).build()
    private val prefsAdapter : JsonAdapter<Prefs> = PrefsJsonAdapter(moshi)
    private val slotsAdapter : JsonAdapter<Slot> = SlotsJsonAdapter(moshi)
    private val listType = Types.newParameterizedType(List::class.java, Member::class.java)
    private val memberAdapter : JsonAdapter<List<Member>> = moshi.adapter(listType)
    suspend fun insert(member: Member) {
        memberDAO.insert(member)
    }

    fun getMemberData(context: Context): List<Member> {
        val text  = "path to file"
        val moshi = Moshi.Builder().build()
        val adapter : JsonAdapter<List<Member>> = moshi.adapter(listType)
        return adapter.fromJson(text) ?: emptyList()
    }

    suspend fun addMoreMembers() {
        val member1 = Member(54,"Mr.","Background", "1111111111",
            "background@test.com", 1)
            delay(10000)
            memberDAO.insert(member1)





        }

    }
