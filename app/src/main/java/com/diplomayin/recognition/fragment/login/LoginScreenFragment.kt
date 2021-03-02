package com.diplomayin.recognition.fragment.login

import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentLoginScreenBinding
import com.diplomayin.recognition.fragment.register.RegisterScreenFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginScreenFragment : FragmentBaseMVVM<LoginScreenViewModel, FragmentLoginScreenBinding>() {


    override val viewModel: LoginScreenViewModel by viewModel()
    override val binding: FragmentLoginScreenBinding by viewBinding()

    companion object {
        @JvmStatic
        fun newInstance() = LoginScreenFragment()
    }

    override fun initView() {

    }

    override fun initViewClickListeners() {
        with(binding){
            signUp.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.add(
                    R.id.frame,
                RegisterScreenFragment.newInstance(), RegisterScreenFragment::class.java.simpleName)
                    ?.addToBackStack("")?.commit()
            }
        }
    }

    override fun navigateUp() {
        navigateBackStack()
    }
}