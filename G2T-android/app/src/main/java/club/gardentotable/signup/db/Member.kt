package club.gardentotable.signup.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member_table")
data class Member(@PrimaryKey @ColumnInfo(name = "memberID") val mid: Int,
                  @ColumnInfo(name = "first") val firstName: String?,
                  @ColumnInfo(name = "last") val lastName : String?,
                  @ColumnInfo(name = "phone") val phone : String?,
                  @ColumnInfo(name = "email") val email : String?,
                  @ColumnInfo(name = "current_slots") val currentSlots : Int?)
