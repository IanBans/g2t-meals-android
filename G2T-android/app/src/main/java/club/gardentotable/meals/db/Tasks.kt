package club.gardentotable.meals.db

enum class Tasks {
    SETUP {
        override fun toString(): String { return "Setup/Cleanup" }
    },
        CLEANUP {
            override fun toString(): String { return "2nd Cleanup" }
        },
    LEAD {
        override fun toString(): String { return "Lead Cook" }
    },
    BANANA {
        override fun toString(): String { return "2nd Banana" }
    },
}