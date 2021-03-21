package com.diplomayin.recognition.fragment.login

import android.app.AlertDialog
import android.content.DialogInterface
import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.extension.addFragment
import com.diplomayin.recognition.base.utils.extension.makeToastShort
import com.diplomayin.recognition.base.utils.extension.replaceFragment
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentLoginScreenBinding
import com.diplomayin.recognition.fragment.auth.AuthScreenFragment
import com.diplomayin.recognition.fragment.map.MapScreenFragment
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

    override fun observes() {
        with(viewModel){
            observe(visibilityProgress){
                if(it){
                    showLoadingDialog()
                }else{
                    closeLoadingDialog()
                }
            }
            observe(loginSuccess){
                activity?.supportFragmentManager?.replaceFragment(R.id.frame,MapScreenFragment.newInstance())
            }
            observe(loginError){
                if(it == "Please confirm email."){
                    activity?.supportFragmentManager?.replaceFragment(R.id.frame,AuthScreenFragment.newInstance(binding.email.text.toString()))
                }else{
                    context?.makeToastShort(it)
                }

            }
        }
    }

    override fun initViewClickListeners() {
        with(binding){
            signUp.setOnClickListener {
                activity?.supportFragmentManager?.replaceFragment(
                    R.id.frame,
                RegisterScreenFragment.newInstance())
            }
            signIn.setOnClickListener {
                viewModel.login(LoginBody(email.text.toString(),password.text.toString()))
            }
        }
    }

    override fun navigateUp() {
        activity?.finish()
    }

//    private fun createSuccessAlertDialog(message: String) {
//        val alertDialog = AlertDialog.Builder(context)
//        alertDialog.setTitle("Success!")
//        alertDialog.setMessage(message)
//        alertDialog.setCancelable(false)
//        alertDialog.setPositiveButton("OK", object : DialogInterface.OnClickListener {
//            override fun onClick(dialog: DialogInterface?, which: Int) {
//                dialog?.cancel()
//                navigateBackStack()
//                activity?.supportFragmentManager?.beginTransaction()?.add(R.id.frame,MapScreenFragment.newInstance(),MapScreenFragment::class.java.simpleName)
//                    ?.addToBackStack("")?.commit()
//            }
//
//        })
//        alertDialog.show()
//    }
}