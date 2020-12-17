package club.gardentotable.meals.db



enum class Days {
    MONDAY {
        override fun toString(): String { return "Mon" }
    }, TUESDAY{
        override fun toString(): String { return "Tues" }
    }, WEDNESDAY {
        override fun toString(): String { return "Wed" }
    }, THURSDAY {
        override fun toString(): String {
            return "Thurs"
        }
    }, SATURDAY {
        override fun toString(): String {
            return "Sat"
        }
    }, WEEKLY {
        override fun toString(): String {
            return "Week"
        }
    }


}