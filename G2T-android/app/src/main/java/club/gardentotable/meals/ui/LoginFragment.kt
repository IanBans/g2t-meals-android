package club.gardentotable.meals.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import club.gardentotable.meals.MainActivity
import club.gardentotable.meals.R
import club.gardentotable.meals.ScheduleFragment
import club.gardentotable.meals.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _loginBinding : FragmentLoginBinding? = null
    val loginBinding get() = _loginBinding!!

    public override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //when the login button is clicked, launch the main activity
        loginBinding.signInConfirm.setOnClickListener {
            parentFragmentManager.commit {
                this.replace<ScheduleFragment>(R.id.fragmentContainer)
                this.addToBackStack(null)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return loginBinding.root
    }

}