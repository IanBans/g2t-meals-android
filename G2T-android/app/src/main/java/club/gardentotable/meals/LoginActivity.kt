package club.gardentotable.meals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import club.gardentotable.meals.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val activityLoginBinding : ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        //when the login button is clicked, launch the main activity
        activityLoginBinding.signInConfirm.setOnClickListener {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)

            finish()

        }
    }

}