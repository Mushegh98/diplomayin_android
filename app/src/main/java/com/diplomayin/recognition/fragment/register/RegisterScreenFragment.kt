package com.diplomayin.recognition.fragment.register

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.extension.makeToastShort
import com.diplomayin.recognition.base.utils.extension.replaceFragment
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentRegisterScreenBinding
import com.diplomayin.recognition.fragment.auth.AuthScreenFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterScreenFragment : FragmentBaseMVVM<RegisterScreenViewModel, FragmentRegisterScreenBinding>() {

    override val viewModel: RegisterScreenViewModel by viewModel()
    override val binding: FragmentRegisterScreenBinding by viewBinding()


    companion object {
        @JvmStatic
        fun newInstance() = RegisterScreenFragment()
    }

    override fun initView() {

    }

    override fun initViewClickListeners() {
       with(binding){
           signUp.setOnClickListener {
               signUp()
           }
       }
    }

    override fun observes() {
        with(viewModel){
            observe(invalidEmail){
               context?.makeToastShort(it)
            }
            observe(invalidPassword){
                context?.makeToastShort(it)
            }
            observe(invalidUsername){
                context?.makeToastShort(it)
            }
            observe(areNotMismatch){
                context?.makeToastShort(it)
            }
            observe(validationSuccess){
                with(binding){
                    register(RegisterBody(email.text.toString(),passwordRegister.text.toString(),confirmPasswordRegister.text.toString(),usernameRegister.text.toString()))
                }
            }
            observe(registerSuccess){
                createSuccessAlertDialog(it)
            }
            observe(registerError){
                context?.makeToastShort(it)
            }
            observe(visibilityProgress){
                if(it){
                    showLoadingDialog()
                }else{
                    closeLoadingDialog()
                }
            }
        }
    }


    private fun signUp(){
        with(binding) {
            with(viewModel) {
//            viewModel.validate(usernameRegister.text.toString(),email.text.toString(),passwordRegister.text.toString(),confirmPasswordRegister.text.toString())
                register(
                    RegisterBody(
                        email.text.toString(),
                        passwordRegister.text.toString(),
                        confirmPasswordRegister.text.toString(),
                        usernameRegister.text.toString()
                    )
                )
            }
        }
    }

    override fun navigateUp() {
        navigateBackStack()
    }



    private fun createSuccessAlertDialog(message: String) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Success!")
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.cancel()
                navigateBackStack()
                activity?.supportFragmentManager?.replaceFragment(
                    R.id.frame,
                    AuthScreenFragment.newInstance(binding.email.text.toString()))
            }

        })
        alertDialog.show()
    }
}