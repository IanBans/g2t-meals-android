package club.gardentotable.signup.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import club.gardentotable.signup.R
import java.lang.IllegalStateException

class NewMemberSignupDialogFragment: DialogFragment() {
   override fun onCreateDialog(savedInstanceState : Bundle?) : Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_auth_newuser, null))
            builder.create()
        } ?: throw IllegalStateException("no")
    }
}