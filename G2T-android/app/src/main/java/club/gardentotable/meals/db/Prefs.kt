package club.gardentotable.meals.db

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Prefs(val days: Array<Days>?, val tasks: Array<Tasks>?)








