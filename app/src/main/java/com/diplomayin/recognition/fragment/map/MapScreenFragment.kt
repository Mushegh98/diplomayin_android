package com.diplomayin.recognition.fragment.map

import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentMapScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapScreenFragment : FragmentBaseMVVM<MapScreenViewModel, FragmentMapScreenBinding>() {

    override val viewModel: MapScreenViewModel by viewModel()
    override val binding: FragmentMapScreenBinding by viewBinding()

    companion object {
        @JvmStatic
        fun newInstance() = MapScreenFragment()
    }

    override fun initView() {

    }

    override fun navigateUp() {
        navigateBackStack()
    }
}