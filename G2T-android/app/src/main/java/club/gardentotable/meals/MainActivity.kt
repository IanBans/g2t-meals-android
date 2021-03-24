package club.gardentotable.meals

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import club.gardentotable.meals.ui.LoginFragment

class MainActivity : AppCompatActivity() {

val fm = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fm.commit {
            this.add<LoginFragment>(R.id.fragmentContainer)
            this.addToBackStack("first")

        }
    }
}