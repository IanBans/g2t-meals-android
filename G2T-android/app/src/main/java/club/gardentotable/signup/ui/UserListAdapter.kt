package club.gardentotable.signup.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.signup.databinding.RecyclerviewItemBinding
import club.gardentotable.signup.db.User

class UserListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>() //cache

    inner class UserViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val firstName : TextView = binding.displayFirstname
        val lastName : TextView = binding.displayLastname
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding : RecyclerviewItemBinding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]
        holder.firstName.text = current.firstName
        holder.lastName.text = current.lastName


    }

    internal fun setUsers(users : List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size

}