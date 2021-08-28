package club.gardentotable.meals.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import club.gardentotable.meals.R

private const val BORDER_WIDTH = 1f
private const val SWEEP_ANGLE = (360f / 4)

class JobCompletion @JvmOverloads  constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributes, defStyleAttr) {

    //when monthlySlots is updated, add appropriate colors to the list
    var monthlySlots : Int = 0
    set(value) {
        field = value
        defineColors()
    }

    private var colors:List<Int> = emptyList()
    private val rect: RectF = RectF(0f, 0f, 0f, 0f)





    //colors for the Completion Wheel
    private val paint: Paint = Paint()
    private val grey = ContextCompat.getColor(context, R.color.colorGrey)
    private val black = ContextCompat.getColor(context, R.color.buttonLabel)
    private val green = ContextCompat.getColor(context, R.color.jobTypeB)
    private val pink =  ContextCompat.getColor(context, R.color.jobTypeA)
    private val lightBlue =  ContextCompat.getColor(context, R.color.jobTypeC)
    private val orange =  ContextCompat.getColor(context, R.color.jobTypeD)

    //set paint type and define colors based on number of monthly slots
    init {
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = 50f
        defineColors();
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    private fun defineColors() {
         colors = when(monthlySlots) {
             0 -> listOf(grey, grey, grey, grey)
             1 -> listOf(grey, grey, lightBlue, grey)
             2 -> listOf(grey, grey, lightBlue, green)
             3 -> listOf(pink, grey, lightBlue, green)
             4 -> listOf(pink, orange,lightBlue,green)
             else -> listOf(pink, orange,lightBlue,green)

         }
        invalidate()

    }
    override fun onDraw(canvas: Canvas) {
        var arcNum = 0
        colors.forEach { color ->
            paint.color = color
            canvas.drawArc(rect, (SWEEP_ANGLE*arcNum), SWEEP_ANGLE, false, paint)
            paint.color = black
            canvas.drawArc(rect, (SWEEP_ANGLE*arcNum), BORDER_WIDTH, false, paint)
            arcNum += 1
            paint.color = color
        }
    }
}


