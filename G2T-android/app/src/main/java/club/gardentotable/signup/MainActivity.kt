package club.gardentotable.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import club.gardentotable.signup.NewUserActivity.Companion.UID
import club.gardentotable.signup.NewUserActivity.Companion.USER_FIRSTNAME
import club.gardentotable.signup.NewUserActivity.Companion.USER_INFO
import club.gardentotable.signup.NewUserActivity.Companion.USER_LASTNAME
import club.gardentotable.signup.databinding.ActivityMainBinding
import club.gardentotable.signup.db.User
import club.gardentotable.signup.ui.NewMemberSignupDialogFragment
import club.gardentotable.signup.ui.UserListAdapter
import club.gardentotable.signup.ui.UserViewModel


class MainActivity : AppCompatActivity() {

    private val newUserActivityRequestCode = 1
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signup = NewMemberSignupDialogFragment()
        signup.show(supportFragmentManager, "whatever")


        val activityMainBinding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val adapter = UserListAdapter(this)
        activityMainBinding.recyclerview.adapter = adapter
        activityMainBinding.recyclerview.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userViewModel.allUsers.observe(this, Observer { users ->
            // Update the cached copy of the users in the adapter.
            users?.let { adapter.setUsers(it) }
        })


        activityMainBinding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewUserActivity::class.java)
            startActivityForResult(intent, newUserActivityRequestCode)
        }
    }

    fun showSignupDialog() {


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newUserActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getBundleExtra(USER_INFO)?.let { info ->

                val user = User(
                    info.getInt(UID), info.getString(USER_FIRSTNAME),
                    info.getString(USER_LASTNAME)
                )
                userViewModel.insert(user)
                Unit
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
