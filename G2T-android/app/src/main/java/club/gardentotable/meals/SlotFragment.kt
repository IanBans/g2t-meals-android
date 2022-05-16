package club.gardentotable.meals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.meals.databinding.FragmentSlotListBinding
import club.gardentotable.meals.db.Slot
import club.gardentotable.meals.ui.*
import dagger.hilt.android.AndroidEntryPoint


const val GRID_SPAN : Int = 6
@AndroidEntryPoint
class SlotFragment : Fragment() {

    private var _binding: FragmentSlotListBinding? = null
    private val newMemberActivityRequestCode = 1

    private val slotViewModel: SlotViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSlotListBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerview
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = SlotListAdapter(requireContext()) { clickedSlot: Slot ->
            onSlotClicked(clickedSlot)
        }

        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), GRID_SPAN)
        recyclerView.addItemDecoration(SlotItemDecoration(AppCompatResources.getDrawable(requireContext(), R.drawable.slot_divider)!!))

        slotViewModel.allSlots.observe(viewLifecycleOwner) { slots ->
            slots?.let { adapter.setSlots(slots, GRID_SPAN) }
        }

    }

    fun onJobCancel(dialog: DialogFragment, slot : Slot) {
        slotViewModel.drop(slot)
        dialog.dismiss()
    }
    private fun onSlotClicked(slot : Slot) {
        if (slot.assignee == null) {
            slotViewModel.signup(slot)
        } else {
           findNavController().navigate(R.id.slotOverviewFragment)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {

        if (requestCode == newMemberActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getBundleExtra(NewMemberActivity.MEMBER_INFO)?.let {
                
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