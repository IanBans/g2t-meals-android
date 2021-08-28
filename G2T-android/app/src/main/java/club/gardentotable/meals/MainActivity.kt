package club.gardentotable.meals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import club.gardentotable.meals.db.MemberRoomDatabase
import club.gardentotable.meals.db.SharedRepository
import club.gardentotable.meals.ui.MemberViewModel
import club.gardentotable.meals.ui.MemberViewModelFactory
import club.gardentotable.meals.ui.SlotViewModel
import club.gardentotable.meals.ui.SlotViewModelFactory

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            this.add<LoginFragment>(R.id.fragmentContainerView)
            this.addToBackStack("login")

        }

    }
}