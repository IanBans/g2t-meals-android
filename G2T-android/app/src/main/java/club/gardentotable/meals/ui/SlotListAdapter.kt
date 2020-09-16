package club.gardentotable.meals.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.meals.databinding.RecyclerviewItemBinding
import club.gardentotable.meals.db.*
import com.squareup.moshi.JsonAdapter
import java.time.LocalDate

class SlotListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<SlotListAdapter.SlotViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var slots: List<Slot> = emptyList<Slot>() //cache

    class SlotViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val container : ConstraintLayout = binding.container
        val firstName : TextView = binding.memberFirstname
        val taskName : TextView = binding.taskName


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val binding :  RecyclerviewItemBinding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return SlotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        var current = slots[position]

        if(current.date != LocalDate.MAX) {
            holder.firstName.text = current.assignee?.firstName ?: ""
            holder.taskName.text = current.task.toString()
        } else {
            holder.container.visibility = View.INVISIBLE
        }


    }

    internal fun setSlots(slots : List<Slot>) {



        this.slots = padList(slots.toMutableList(), 6)
        notifyDataSetChanged()
    }

     private fun padList(slots: MutableList<Slot>, grid: Int): List<Slot> {
         var position = 0
        while(position < slots.size-1) {
            var currentPos = position
            var current = slots[position]
            val next = slots[position + 1]

            if ((current.day != next.day) && ((currentPos + 1) % grid != 0)) {
                while ((currentPos + 1) % grid != 0) {
                    slots.add(currentPos + 1, Slot(null, null, LocalDate.MAX, null, null))
                    currentPos++
                }

            }
            position++
        }
        return slots.toList()

    }

    override fun getItemCount() = slots.size

}