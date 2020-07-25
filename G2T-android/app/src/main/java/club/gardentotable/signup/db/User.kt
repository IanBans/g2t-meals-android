package club.gardentotable.signup.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(@PrimaryKey @ColumnInfo(name = "uid") val uid: Int,
                @ColumnInfo(name = "first_name") val firstName: String?,
                @ColumnInfo(name = "last_name") val lastName : String?)
