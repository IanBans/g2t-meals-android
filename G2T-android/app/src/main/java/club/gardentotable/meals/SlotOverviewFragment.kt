package club.gardentotable.meals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import club.gardentotable.meals.databinding.FragmentSlotOverviewBinding
import club.gardentotable.meals.db.Slot
import club.gardentotable.meals.ui.*
import java.time.LocalDate

class SlotOverviewFragment : Fragment() {
    private var _binding : FragmentSlotOverviewBinding? = null
    val binding get() = _binding!!
    private val slotViewModel: SlotViewModel by activityViewModels{
        SlotViewModelFactory((requireActivity().application as SignupApplication).repository) }
    private val memberViewModel: MemberViewModel by activityViewModels {
        MemberViewModelFactory(((requireActivity().application as SignupApplication).repository)) }
    private lateinit var overviewButtons : List<Button>
    private lateinit var slotTitles : List<TextView>

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
        _binding = FragmentSlotOverviewBinding.inflate(inflater, container, false)
        overviewButtons = listOf(binding.slot1, binding.slot2, binding.slot3, binding.slot4)
        slotTitles = listOf(binding.slot1Name, binding.slot2Name, binding.slot3Name, binding.slot4Name)
        return binding.root
    }
    private fun onUpdateView(slots: List<Slot>) {
        val monthlySlotCount : Int
        val testDate = LocalDate.of(2020,5,1)

        slotViewModel.export(requireContext())
        val assignedMonthlySlots : List<Slot> = slots.filter { slot ->
            //TODO: testDate should be LocalDate.now.month
            slot.date.month == testDate.month

        }
        Log.i("SLOTS", slots.size.toString())

        //only update UI if the monthly slots is not empty
        binding.filledSlots = "0/4"
        overviewButtons.forEach {
            it.setOnClickListener  { parentFragmentManager.commit {
            this.replace<SlotFragment>(R.id.fragmentContainerView)
            this.addToBackStack("slotsView")
        }}
            it.text = ""
        }
        slotTitles.forEach { it.text = "" }
        binding.signupTracker.monthlySlots = 0
        if(assignedMonthlySlots.isNotEmpty()) {

            monthlySlotCount = assignedMonthlySlots.count()
            binding.signupTracker.monthlySlots = monthlySlotCount
            binding.filledSlots = requireContext().getString(R.string.display_date, monthlySlotCount.toString(), "4")


            for (i in overviewButtons.indices)
                if (monthlySlotCount > i) {
                    val currentSlot = assignedMonthlySlots[i]
                    overviewButtons[i].text = requireContext().getString(R.string.overview_slot_info, currentSlot.date.monthValue.toString(), currentSlot.date.dayOfMonth.toString(), currentSlot.day.toString())
                   slotTitles[i].text =  currentSlot.task.toString()

                    Log.i("COUNT", i.toString())

                    overviewButtons[i].setOnClickListener{
                        SlotDetailDialogFragment(currentSlot, slotViewModel).show(parentFragmentManager, "detail")
                    }
                }

        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


         memberViewModel.currentUserSlots.observe(viewLifecycleOwner, { slots ->
             memberViewModel.export(requireContext())
             slots?.let { onUpdateView(it) }
         })


    }




}


