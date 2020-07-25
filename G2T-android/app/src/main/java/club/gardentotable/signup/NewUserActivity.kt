package club.gardentotable.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import club.gardentotable.signup.databinding.ActivityNewUserBinding


class NewUserActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityNewUserBinding: ActivityNewUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_user)

        //when the save button is clicked, send the field info to the intent
        //with a bundle
        activityNewUserBinding.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            val extras = Bundle()
            if (TextUtils.isEmpty(activityNewUserBinding.editUserFirstname.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val  userFirstname = activityNewUserBinding.editUserFirstname.text.toString()
                val userLastname = activityNewUserBinding.editUserLastname.text.toString()
                val uid = activityNewUserBinding.editUserId.text.toString().toInt()
                extras.putString(USER_FIRSTNAME, userFirstname)
                extras.putString(USER_LASTNAME, userLastname)
                extras.putInt(UID, uid)
                replyIntent.putExtra(USER_INFO, extras)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val USER_FIRSTNAME = "com.example.android.userlistsql.USER_FIRSTNAME"
        const val USER_LASTNAME = "com.example.android.userlistsql.USER_LASTNAME"
        const val UID = "com.example.android.userlistsql.UID"
        const val USER_INFO = "com.example.android.userlistsql.USER_INFO"

    }
}