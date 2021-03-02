package com.diplomayin.recognition.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.FragmentBaseMVVM.Companion.LOADING_TAG

class LoadingDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(): LoadingDialogFragment? {
            val fragment =
                LoadingDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                if (activity!!.supportFragmentManager
                        .findFragmentByTag(LOADING_TAG) != null
                ) {
                    activity!!.onBackPressed()
                }
                super.onBackPressed()
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}