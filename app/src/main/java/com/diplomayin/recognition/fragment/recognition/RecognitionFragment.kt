package com.diplomayin.recognition.fragment.recognition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.FragmentBaseMVVM
import com.diplomayin.recognition.base.utils.viewBinding
import com.diplomayin.recognition.databinding.FragmentMapScreenBinding
import com.diplomayin.recognition.databinding.FragmentRecognitionBinding
import com.diplomayin.recognition.fragment.map.MapScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecognitionFragment : FragmentBaseMVVM<RecognitionViewModel,FragmentRecognitionBinding>() {


    override val viewModel: RecognitionViewModel by viewModel()
    override val binding: FragmentRecognitionBinding by viewBinding()

    companion object {
        @JvmStatic
        fun newInstance() = RecognitionFragment()
    }

    override fun initView() {

    }

    override fun navigateUp() {
        navigateBackStack()
    }
}