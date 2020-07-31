package club.gardentotable.signup.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.squareup.moshi.*
import kotlinx.coroutines.delay

class MemberRepository(private val memberDAO: MemberDAO) {

    val allMembers: LiveData<List<Member>> = memberDAO.getAllOrderedMID()
    private val listType = Types.newParameterizedType(
        List::class.java, Member::class.java
    )
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
