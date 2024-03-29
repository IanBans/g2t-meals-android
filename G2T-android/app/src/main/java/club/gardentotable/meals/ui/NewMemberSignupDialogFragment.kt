package club.gardentotable.meals.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import club.gardentotable.meals.R
import java.lang.IllegalStateException

class NewMemberSignupDialogFragment: DialogFragment() {
   override fun onCreateDialog(savedInstanceState : Bundle?) : Dialog {
       return activity?.let {activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_auth_newmember, null))
            builder.create()
        } ?: throw IllegalStateException("not created!")
    }
}