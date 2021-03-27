package com.diplomayin.recognition.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentMapScreenBinding
import com.diplomayin.recognition.databinding.FragmentProfileBinding
import com.diplomayin.recognition.fragment.map.MapScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : FragmentBaseMVVM<ProfileViewModel,FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModel()
    override val binding: FragmentProfileBinding by viewBinding()

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun initView() {

    }

    override fun navigateUp() {
        navigateBackStack()
    }
}