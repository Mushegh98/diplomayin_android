package com.diplomayin.recognition.fragment.auth

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.extension.makeToastShort
import com.diplomayin.recognition.base.utils.extension.replaceFragment
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentAuthBinding
import com.diplomayin.recognition.fragment.login.LoginScreenFragment
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
        with(binding){
            confirm.setOnClickListener {
                viewModel.confirm(AuthBody(email,code.text.toString()))
            }
        }
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

            observe(confirmSuccess){
                createSuccessAlertDialog(it)
            }
            observe(confirmError){
                context?.makeToastShort(it)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = arguments?.getString("email")!!
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
                activity?.supportFragmentManager?.replaceFragment(R.id.frame,LoginScreenFragment.newInstance())
            }

        })
        alertDialog.show()
    }
}