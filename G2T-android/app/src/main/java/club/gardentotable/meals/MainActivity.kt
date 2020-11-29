package club.gardentotable.meals

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import club.gardentotable.meals.NewMemberActivity.Companion.MEMBER_INFO
import club.gardentotable.meals.databinding.ActivityMainBinding
import club.gardentotable.meals.databinding.RecyclerviewItemBinding
import club.gardentotable.meals.ui.SlotListAdapter
import club.gardentotable.meals.ui.SlotViewModel


const val GRID_SPAN : Int = 6
class MainActivity : AppCompatActivity() {

    private val newMemberActivityRequestCode = 1
    private lateinit var slotViewModel: SlotViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sets the layout and adapter
        val activityMainBinding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val adapter = SlotListAdapter(this)
        activityMainBinding.recyclerview.adapter = adapter
        activityMainBinding.recyclerview.layoutManager = GridLayoutManager(this, GRID_SPAN)




        // Get a new or existing ViewModel from the ViewModelProvider.
        slotViewModel = ViewModelProvider(this).get(SlotViewModel::class.java)
        slotViewModel.allSlots.observe(this, { slots ->
            slots?.let { adapter.setSlots(it, GRID_SPAN) }
        })

        slotViewModel.export(this)
        Log.i("TAG", filesDir.toString())


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newMemberActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getBundleExtra(MEMBER_INFO)?.let { info ->


            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }

        Log.i("TAG", filesDir.toString())
    }


}
