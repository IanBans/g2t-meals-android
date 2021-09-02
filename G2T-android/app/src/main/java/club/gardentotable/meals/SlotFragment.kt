package club.gardentotable.meals

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.gardentotable.meals.databinding.FragmentSlotListBinding
import club.gardentotable.meals.db.Slot
import club.gardentotable.meals.ui.*

const val GRID_SPAN : Int = 6
class SlotFragment : Fragment() {

    private var _binding: FragmentSlotListBinding? = null
    private val newMemberActivityRequestCode = 1

    private val slotViewModel: SlotViewModel by activityViewModels{
        SlotViewModelFactory((requireActivity().application as SignupApplication).repository) }
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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
            slotClicked(clickedSlot)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), GRID_SPAN)
        slotViewModel.allSlots.observe(viewLifecycleOwner, { slots ->
            slots?.let { adapter.setSlots(it, GRID_SPAN) }
        })

    }

    fun onJobCancel(dialog: DialogFragment, slot : Slot) {
        slotViewModel.drop(slot)
        dialog.dismiss()
    }
    private fun slotClicked(slot : Slot) {
        if (slot.assignee == null) {
            slotViewModel.signup(slot)
        } else {
            SlotDetailDialogFragment(slot, slotViewModel).show(parentFragmentManager, "detail")

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