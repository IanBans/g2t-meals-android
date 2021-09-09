package club.gardentotable.meals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import club.gardentotable.meals.databinding.FragmentLoginBinding
import club.gardentotable.meals.db.Slot
import club.gardentotable.meals.ui.NewMemberSignupDialogFragment
import club.gardentotable.meals.ui.SlotDetailDialogFragment
import club.gardentotable.meals.ui.SlotViewModel

class LoginFragment : Fragment() {
    private var _loginBinding : FragmentLoginBinding? = null
    private val loginBinding get() = _loginBinding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //when the login button is clicked, launch the slot fragment
        loginBinding.signInConfirm.setOnClickListener {
           findNavController().navigate(R.id.slotFragment)


        }
        loginBinding.noAccount.setOnClickListener {
            findNavController().navigate(R.id.newMemberSignupDialogFragment)
            //NewMemberSignupDialogFragment().show(parentFragmentManager, "signup")
        }
    }

}