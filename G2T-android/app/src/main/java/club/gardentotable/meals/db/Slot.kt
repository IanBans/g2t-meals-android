package club.gardentotable.meals.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "slot_table")
data class Slot(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "taskID") val slotID: Int? = null,
                @ColumnInfo(name ="day")val day: Days?, @ColumnInfo(name="date") val date: String,
                @ColumnInfo(name = "task")val task: Tasks?, @ColumnInfo(name = "assignee") val assignee: Member? = null)

