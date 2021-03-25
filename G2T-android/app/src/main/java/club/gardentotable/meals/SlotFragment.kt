package club.gardentotable.meals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.meals.databinding.ActivityMainBinding
import club.gardentotable.meals.databinding.FragmentSlotListBinding
import club.gardentotable.meals.db.Slot
import club.gardentotable.meals.ui.SlotListAdapter
import club.gardentotable.meals.ui.SlotViewModel

const val GRID_SPAN : Int = 6
class ScheduleFragment : Fragment() {

    private var _binding: FragmentSlotListBinding? = null
    private val newMemberActivityRequestCode = 1

    private lateinit var slotViewModel: SlotViewModel
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    private fun slotClicked(slot : Slot) {
       slotViewModel.signup(slot)
       slotViewModel.export(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlotListBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.recyclerview
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SlotListAdapter(requireContext()) { clickedSlot: Slot ->
            slotClicked(
                clickedSlot
            )
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), GRID_SPAN)

        // Get a new or existing ViewModel from the ViewModelProvider.
        slotViewModel = ViewModelProvider(this).get(SlotViewModel::class.java)
        slotViewModel.allSlots.observe(viewLifecycleOwner, { slots ->
            slots?.let { adapter.setSlots(it, GRID_SPAN) }
        })
        slotViewModel

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newMemberActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getBundleExtra(NewMemberActivity.MEMBER_INFO)?.let { info ->


            }
        } else {
            Toast.makeText(
                requireContext(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }

        Log.i("TAG", requireContext().filesDir.toString())
    }
}