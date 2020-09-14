package club.gardentotable.meals.db

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class LocalDateAdapter {
    val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).add(KotlinJsonAdapterFactory()).build()
    val dateObjAdapter :JsonAdapter<Date> = moshi.adapter(Date::class.java)

    @ToJson
    fun LocalDateToJson(date: LocalDate): String {
        return dateObjAdapter.toJson(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
    }

    @FromJson
    fun LocalDateFromJson(string: String): LocalDate {
        return dateObjAdapter.fromJson(string)?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate() ?: LocalDate.MAX
    }

}