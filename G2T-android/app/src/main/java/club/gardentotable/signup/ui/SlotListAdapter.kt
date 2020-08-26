package club.gardentotable.signup.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.signup.databinding.RecyclerviewItemBinding
import club.gardentotable.signup.db.Slot

class SlotListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<SlotListAdapter.MemberViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var slots = emptyList<Slot>() //cache

    inner class MemberViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val firstName : TextView = binding.memberFirstname
        val taskName : TextView = binding.taskName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding : RecyclerviewItemBinding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val current = slots[position]
        holder.firstName.text = current.assignee!!.firstName
        holder.taskName.text = current.task.toString()


    }

    internal fun setSlots(slots : List<Slot>) {
        this.slots = slots
        notifyDataSetChanged()
    }

    override fun getItemCount() = slots.size

}