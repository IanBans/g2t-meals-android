package club.gardentotable.meals.db

enum class Tasks {
    SETUP {
        override fun toString(): String { return "Setup/Clean" }
    },
        CLEANUP {
            override fun toString(): String { return "2nd Clean" }
        },
    LEAD {
        override fun toString(): String { return "Lead Cook" }
    },
    BANANA {
        override fun toString(): String { return "2nd Banana" }
    },
    INVENTORY {
        override fun toString(): String { return "Inventory" }
    },

    MOP {
        override fun toString(): String { return "MOPPING" }
    },
}