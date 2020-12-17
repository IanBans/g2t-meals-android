package club.gardentotable.meals.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "member_table")
data class Member(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "memberID") val mid: Int? = 0, @ColumnInfo(name = "first") val firstName: String?,
                  @ColumnInfo(name = "last") val lastName : String?, @ColumnInfo(name = "phone") val phone : String?,
                  @ColumnInfo(name = "email") val email : String?, @ColumnInfo(name = "current_slots") val currentSlots : Int? = 0,
                  @ColumnInfo(name = "prefs") val prefs : Prefs? = null, @ColumnInfo(name = "exclusions") val exclusions : Prefs? = null)


