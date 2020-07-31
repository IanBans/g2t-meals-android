package club.gardentotable.signup.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.signup.databinding.RecyclerviewItemBinding
import club.gardentotable.signup.db.Member

class MemberListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<MemberListAdapter.MemberViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var members = emptyList<Member>() //cache

    inner class MemberViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val firstName : TextView = binding.displayFirstname
        val lastName : TextView = binding.displayLastname
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding : RecyclerviewItemBinding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val current = members[position]
        holder.firstName.text = current.firstName
        holder.lastName.text = current.lastName


    }

    internal fun setMembers(members : List<Member>) {
        this.members = members
        notifyDataSetChanged()
    }

    override fun getItemCount() = members.size

}