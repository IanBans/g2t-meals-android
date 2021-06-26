package club.gardentotable.meals.db

import androidx.core.content.res.*
import club.gardentotable.meals.R

enum class Tasks {
    SETUP {
        override fun toString(): String { return "Setup/Clean" }
        override fun getBG(): Int {return R.drawable.bg_clean }
    },
        CLEANUP {
            override fun toString(): String { return "2nd Clean" }
            override fun getBG(): Int {return R.drawable.bg_clean }
        },
    LEAD {
        override fun toString(): String { return "Lead Cook" }
        override fun getBG(): Int {return R.drawable.bg_cook }
    },
    BANANA {
        override fun toString(): String { return "2nd Banana" }
        override fun getBG(): Int {return R.drawable.bg_cook }
    },
    INVENTORY {
        override fun toString(): String { return "Inventory" }
        override fun getBG(): Int {return R.drawable.bg_inv }
    },

    MOP {
        override fun toString(): String { return "Mopping" }
        override fun getBG(): Int {return R.drawable.bg_clean }
    },
    SHOP1 {
        override fun toString(): String { return "Lead Shop" }
       override fun getBG(): Int {return R.drawable.bg_shop }
    },
    SHOP2 {
        override fun toString(): String { return "2nd Shop" }
        override fun getBG(): Int {return R.drawable.bg_shop }
    };

    open fun getBG(): Int {
        return R.drawable.shape_circle
    }
}