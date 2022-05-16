package club.gardentotable.meals.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.view.children
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.meals.GRID_SPAN


class SlotItemDecoration(private val dividerDrawable: Drawable) : RecyclerView.ItemDecoration() {


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight

        parent.children.forEach {
            val position = parent.getChildLayoutPosition(it)

            if (position  == 0) {
                val params: RecyclerView.LayoutParams = it.layoutParams as RecyclerView.LayoutParams
                val dividerTop = it.bottom + params.bottomMargin
                val dividerBottom = dividerTop + dividerDrawable.intrinsicHeight
                dividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                dividerDrawable.draw(c)
            } else {
               dividerDrawable.setVisible(false, true)
            }
        }

    }
}