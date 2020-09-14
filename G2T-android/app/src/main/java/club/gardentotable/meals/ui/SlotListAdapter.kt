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

class SlotListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<SlotListAdapter.SlotViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var slots = emptyList<Slot>() //cache

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

        val current = slots[position]

        if(current.day == Days.MONDAY) {


        }
         holder.firstName.text = current.assignee?.firstName ?: ""
         holder.taskName.text = current.task.toString()


    }

    internal fun setSlots(slots : List<Slot>) {
        this.slots = slots
        notifyDataSetChanged()
    }

    override fun getItemCount() = slots.size

}