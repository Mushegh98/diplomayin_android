package com.diplomayin.recognition.fragment.login

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.recognition.R
import com.diplomayin.recognition.activity.ContainerActivity
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.extension.makeToastShort
import com.diplomayin.recognition.base.utils.extension.replaceFragment
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentLoginScreenBinding
import com.diplomayin.recognition.fragment.auth.AuthScreenFragment
import com.diplomayin.recognition.fragment.register.RegisterScreenFragment
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginScreenFragment : FragmentBaseMVVM<LoginScreenViewModel, FragmentLoginScreenBinding>() {


    override val viewModel: LoginScreenViewModel by viewModel()
    override val binding: FragmentLoginScreenBinding by viewBinding()

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Do something if permission granted
            val deniedPermissionList = arrayListOf<String>()
            permissions.entries.forEach {
                Log.d("DEBUG_", "${it.key} = ${it.value}")
                if (!it.value) {
                    deniedPermissionList.add(it.key)
                }
            }
            if (deniedPermissionList.size > 0) {
                //some permission is not granted ask to grant them again
                requestPermissionsIfRequired()

            } else {

            }
        }

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
                if(context?.let { it1 -> checkPermissions(it1) } == true){
                    Intent(activity,ContainerActivity::class.java).apply {
                        startActivity(this)
                        activity?.finish()
                    }
                }else{
                    requestPermissionsIfRequired()
                }

//                activity?.supportFragmentManager?.replaceFragment(R.id.frame,MapScreenFragment.newInstance())
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


    private fun requestPermissionsIfRequired() {
        if (!checkPermissions(requireContext())) {
            AlertDialog.Builder(context, R.style.AlertDialogTheme)
                .setTitle(getString(R.string.permission_required))
                .setMessage(getString(R.string.location_permission_message))
                .setPositiveButton(getString(R.string.grant_permission)) { dialogInterface: DialogInterface, i: Int ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        requestPermission.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    } else {
                        requestPermission.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }.show()
        } else {

        }
    }

    private fun checkPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED

        } else {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}