package club.gardentotable.meals.db

enum class Tasks {
    SETUP {
        override fun toString(): String { return "Setup" }
    },
        CLEANUP {
            override fun toString(): String { return "Cleanup" }
        },
    LEAD {
        override fun toString(): String { return "Lead" }
    },
    BANANA {
        override fun toString(): String { return "2nd Banana" }
    },
}