package club.gardentotable.meals.ui

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.meals.GRID_SPAN
import club.gardentotable.meals.R
import club.gardentotable.meals.databinding.RecyclerviewItemBinding
import club.gardentotable.meals.databinding.RecyclerviewRowlabelBinding
import club.gardentotable.meals.db.*
import com.google.android.material.internal.ContextUtils.getActivity
import java.time.LocalDate
const val LABEL_TYPE : Int = 1
const val SLOT_TYPE : Int = 2
class SlotListAdapter internal constructor(private val context: Context, private val clickListener: (Slot) -> Unit ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var slots: List<Slot> = emptyList() //cache

    class SlotViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val container : ConstraintLayout = binding.container
        val firstName : TextView = binding.memberFirstname
        val taskName : TextView = binding.taskName
        val photo : ImageView = binding.displayPhoto



    }

    class LabelViewHolder(binding: RecyclerviewRowlabelBinding) : RecyclerView.ViewHolder(binding.root) {
        val day : TextView = binding.day
        val date : TextView = binding.date

    }

    override fun getItemViewType(position: Int): Int {
        return if(position % GRID_SPAN == 0) {
            LABEL_TYPE
        } else {
            SLOT_TYPE
        }
    }

    /*
    * inflate the binding and return appropriate viewHolder based on type
    * one for slots, one for the row labels
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
        val slotBinding: RecyclerviewItemBinding =
            RecyclerviewItemBinding.inflate(inflater, parent, false)
        val rowLabelBinding : RecyclerviewRowlabelBinding = RecyclerviewRowlabelBinding.inflate(inflater, parent, false)

        return if (viewType == SLOT_TYPE) {
            SlotViewHolder(slotBinding)
        } else
            LabelViewHolder(rowLabelBinding)
    }

     /*
      * populates the recyclerview with the slot data and controls visibility based on the ViewType
      */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = slots[holder.adapterPosition]
        lateinit var next : Slot
        if(holder.itemViewType == SLOT_TYPE) {
            holder as SlotViewHolder
            holder.itemView.setOnClickListener{ clickListener(current) }
            if(current.day != null) {
                holder.firstName.text = current.assignee?.firstName ?: ""
                holder.taskName.text = current.task.toString()
                holder.photo.background = ContextCompat.getDrawable(context, current.task?.getBG() ?: R.drawable.shape_circle)
                holder.container.visibility = View.VISIBLE

                //replace with profile photo of assignee
                    if(holder.firstName.text.equals("Example")) {
                        holder.photo.setImageResource(R.drawable.ic_launcher_foreground)
                    } else
                        holder.photo.setImageDrawable(null)

            }
        } else if (holder.itemViewType == LABEL_TYPE) {
            next = slots[holder.adapterPosition+1]
            holder as LabelViewHolder
            if(next.date != LocalDate.MAX) {
                holder.day.visibility = View.VISIBLE
                holder.date.visibility = View.VISIBLE
            }
            holder.day.text = next.day.toString()
            holder.date.text = context.getString(R.string.display_date, next.date.monthValue.toString(), next.date.dayOfMonth.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setSlots(slots : List<Slot>, gridSize: Int) {
        this.slots = padList(slots, gridSize)
        notifyDataSetChanged()
    }

    /* Pads the list of slots with null elements to control the placement of days on the grid
    * loops through the list copies the list of slots to an empty list of appropriate size
    *  if the days do not match, skips to the next line of the gridsize by moving up size - (index % size) positions
    */
     private fun padList(slots : List<Slot>, gridSize: Int): List<Slot> {
        val gridList : Array<Slot> = Array(gridSize*6) {Slot(null, null, LocalDate.MAX, null, null) }
        var gridIndex = 1
        for(i in (0..slots.size-2)) {

                if(slots[i].day != slots[i+1].day) {
                    gridList[gridIndex] = slots[i]
                    gridIndex += (gridSize + 1) - (gridIndex % gridSize)
                } else {
                    gridList[gridIndex] = slots[i]
                    gridIndex++
                }

        }
        return gridList.toList()

    }
    override fun getItemCount() = slots.size

}