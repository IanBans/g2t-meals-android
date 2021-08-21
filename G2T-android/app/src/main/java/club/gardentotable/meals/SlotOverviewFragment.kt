package club.gardentotable.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import club.gardentotable.meals.databinding.FragmentSlotOverviewBinding
import club.gardentotable.meals.db.Slot

import club.gardentotable.meals.ui.MemberViewModel
import java.time.LocalDate

class SlotOverviewFragment : Fragment() {
    private var _binding : FragmentSlotOverviewBinding? = null
    val binding get() = _binding!!
    private val memberViewModel: MemberViewModel by activityViewModels()
    private lateinit var slotDays:List<TextView?>
    private lateinit var slotNames : List<TextView?>

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
        slotDays = listOf(binding.slot1Name, binding.slot2Name, binding.slot3Name, binding.slot4Name)
        slotNames = listOf(binding.slot1Day, binding.slot2Day, binding.slot3Day, binding.slot4Day)
        return binding.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var monthlySlotCount : Int
        super.onViewCreated(view, savedInstanceState)
         val testDate = LocalDate.of(2020,5,1)
         var assignedMonthlySlots : List<Slot>

         binding.filledSlots = "0/4"
         memberViewModel.currentUserSlots.observe(viewLifecycleOwner, { slots ->
             assignedMonthlySlots = slots.filter { slot ->
                 //TODO: testDate should be LocalDate.now.month
                 slot.date.month == testDate.month

             }
             //only update UI if the monthly slots is not empty
            if(assignedMonthlySlots.isNotEmpty()) {

                monthlySlotCount = assignedMonthlySlots.count()
                binding.signupTracker.monthlySlots = monthlySlotCount
                binding.filledSlots = requireContext().getString(R.string.display_date, monthlySlotCount.toString(), "4")


                for (i in slotDays.indices)
                    if (monthlySlotCount > i) {
                        slotNames[i]?.text = assignedMonthlySlots[i].task.toString()
                        slotDays[i]?.text = assignedMonthlySlots[i].day.toString()
                    }
            }
         })


    }



}