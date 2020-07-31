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
import club.gardentotable.signup.NewMemberActivity.Companion.MID
import club.gardentotable.signup.NewMemberActivity.Companion.MEMBER_FIRSTNAME
import club.gardentotable.signup.NewMemberActivity.Companion.MEMBER_INFO
import club.gardentotable.signup.NewMemberActivity.Companion.MEMBER_LASTNAME
import club.gardentotable.signup.databinding.ActivityMainBinding
import club.gardentotable.signup.db.Member
import club.gardentotable.signup.ui.NewMemberSignupDialogFragment
import club.gardentotable.signup.ui.MemberListAdapter
import club.gardentotable.signup.ui.MemberViewModel


class MainActivity : AppCompatActivity() {

    private val newMemberActivityRequestCode = 1
    private lateinit var memberViewModel: MemberViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //val signup = NewMemberSignupDialogFragment()
       // signup.show(supportFragmentManager, "whatever")


        val activityMainBinding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val adapter = MemberListAdapter(this)
        activityMainBinding.recyclerview.adapter = adapter
        activityMainBinding.recyclerview.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        memberViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)

        memberViewModel.allMembers.observe(this, Observer { members ->
            // Update the cached copy of the members in the adapter.
            members?.let { adapter.setMembers(it) }
        })


        activityMainBinding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewMemberActivity::class.java)
            startActivityForResult(intent, newMemberActivityRequestCode)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newMemberActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getBundleExtra(MEMBER_INFO)?.let { info ->

                val member = Member(
                    info.getInt(MID), info.getString(MEMBER_FIRSTNAME),
                    info.getString(MEMBER_LASTNAME), "1111111111", "test", 0
                )
                memberViewModel.insert(member)
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
