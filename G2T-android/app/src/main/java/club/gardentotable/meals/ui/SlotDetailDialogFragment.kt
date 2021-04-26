package club.gardentotable.meals.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import club.gardentotable.meals.R
import club.gardentotable.meals.SlotFragment
import club.gardentotable.meals.databinding.DialogSlotDetailBinding
import club.gardentotable.meals.db.Slot
import java.lang.ClassCastException


class SlotDetailDialogFragment(currentSlot: Slot): DialogFragment() {

    private val clickedSlot : Slot = currentSlot

    // fills the text and creates the dialog
   override fun onCreateDialog(savedInstanceState : Bundle?) : Dialog {

       val binding : DialogSlotDetailBinding = DialogSlotDetailBinding.inflate(LayoutInflater.from(context))
       binding.jobTitle.text = clickedSlot.task.toString()
       binding.jobDate.text = requireContext().getString(R.string.display_date, clickedSlot.date.monthValue.toString(), clickedSlot.date.dayOfMonth.toString())
        // temporary to keep consistency, will be replaced with profile of assignee
        binding.profilePhoto.setImageResource(R.drawable.ic_launcher_foreground)
        binding.jobCancelButton.setOnClickListener {
            //listener for cancelling job
        }
       val dialog: Dialog = super.onCreateDialog(savedInstanceState)
       dialog.setContentView(binding.root)
       return dialog

    }

}