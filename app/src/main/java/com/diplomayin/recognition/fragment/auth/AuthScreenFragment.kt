package com.diplomayin.recognition.fragment.auth

import android.os.Bundle
import android.view.View
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthScreenFragment : FragmentBaseMVVM<AuthScreenViewModel, FragmentAuthBinding>() {

    override val viewModel: AuthScreenViewModel by viewModel()
    override val binding: FragmentAuthBinding by viewBinding()
    private var email = ""

    companion object {
        @JvmStatic
        fun newInstance(email : String) : AuthScreenFragment = AuthScreenFragment().apply {
            arguments = Bundle().apply {
                putString("email",email)
            }
        }
    }

    override fun initView() {

    }

    override fun initViewClickListeners() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = arguments?.getString("email")!!
    }

    override fun navigateUp() {
       navigateBackStack()
    }
}