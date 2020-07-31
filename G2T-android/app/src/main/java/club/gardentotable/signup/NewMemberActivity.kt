package club.gardentotable.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import club.gardentotable.signup.databinding.ActivityAddMemberBinding


class NewMemberActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityAddMemberBinding : ActivityAddMemberBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_member)

        //when the save button is clicked, send the field info to the intent
        //with a bundle
        activityAddMemberBinding.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            val extras = Bundle()
            if (TextUtils.isEmpty(activityAddMemberBinding.editMemberFirstname.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val  memberFirstname = activityAddMemberBinding.editMemberFirstname.text.toString()
                val memberLastname = activityAddMemberBinding.editMemberLastname.text.toString()
                val uid = activityAddMemberBinding.editMemberId.text.toString().toInt()
                extras.putString(MEMBER_FIRSTNAME, memberFirstname)
                extras.putString(MEMBER_LASTNAME, memberLastname)
                extras.putInt(MID, uid)
                extras.putString(MEMBER_PHONE, "1111111111")
                extras.putString(MEMBER_EMAIL, memberFirstname+ "@test.com")
                extras.putInt(MEMBER_SLOTS, 1)
                replyIntent.putExtra(MEMBER_INFO, extras)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val MEMBER_FIRSTNAME = "club.gardentotable.signup.MEMBER_FIRSTNAME"
        const val MEMBER_LASTNAME = "club.gardentotable.signup.MEMBER_LASTNAME"
        const val MID = "club.gardentotable.signup.MID"
        const val MEMBER_INFO = "club.gardentotable.signup.MEMBER_INFO"
        const val MEMBER_PHONE = "club.gardentotable.signup.MEMBER_PHONE"
        const val MEMBER_EMAIL = "club.gardentotable.signup.MEMBER_EMAIL"
        const val MEMBER_SLOTS = "club.gardentotable.signup.MEMBER_SLOTS"

    }
}