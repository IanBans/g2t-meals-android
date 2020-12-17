package club.gardentotable.meals.db
import androidx.room.TypeConverter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDate

class Converters {

    private val moshi = Moshi.Builder().add(LocalDateAdapter()).add(KotlinJsonAdapterFactory()).build()
    private val prefsAdapter : JsonAdapter<Prefs> = moshi.adapter(Prefs::class.java)
    private val taskAdapter : JsonAdapter<Tasks> = moshi.adapter(Tasks::class.java)
    private val dayAdapter: JsonAdapter<Days> = moshi.adapter(Days::class.java)
    private val memberAdapter : JsonAdapter<Member> = moshi.adapter(Member::class.java)
    private val dateAdapter : JsonAdapter<LocalDate> = moshi.adapter(LocalDate::class.java)

    @TypeConverter
    fun fromMemberToString(member : Member?): String {

       return memberAdapter.toJson(member)
    }

    @TypeConverter
    fun toMember(string : String): Member? {

        return memberAdapter.fromJson(string)
    }

    @TypeConverter
    fun fromTask(task: Tasks?): String {

        return taskAdapter.toJson(task)
    }

    @TypeConverter
    fun toTask(string : String): Tasks? {

        return taskAdapter.fromJson(string)
    }

    @TypeConverter
    fun fromDay(day: Days?): String {

        return dayAdapter.toJson(day)
    }

    @TypeConverter
    fun toDay(string : String): Days? {

        return dayAdapter.fromJson(string)
    }

    @TypeConverter
    fun fromPrefs(pref : Prefs?): String {

        return prefsAdapter.toJson(pref) ?: ""
    }

    @TypeConverter
    fun toPrefs(string : String): Prefs? {

        return prefsAdapter.fromJson(string)
    }

    @TypeConverter
    fun fromLocalDate(date : LocalDate?): String {

        return dateAdapter.toJson(date) ?: ""
    }

    @TypeConverter
    fun toLocalDate(string : String): LocalDate? {

        return dateAdapter.fromJson(string)
    }
}