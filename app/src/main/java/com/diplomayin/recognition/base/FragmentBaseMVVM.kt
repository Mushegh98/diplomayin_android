package com.diplomayin.recognition.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.diplomayin.recognition.base.viewmodel.BaseViewModel
import com.diplomayin.recognition.dialog.LoadingDialogFragment

abstract class FragmentBaseMVVM<ViewModel : BaseViewModel, ViewBind : ViewBinding> : Fragment() {
    protected abstract val viewModel: ViewModel
    protected abstract val binding: ViewBind
//    private lateinit var navController: NavController
//    private val navOptions = NavOptions.Builder()
//            .setLaunchSingleTop(false)
//            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this) {
            /*   if(requireActivity().supportFragmentManager.fragments.lastOrNull() is LoginFragment){
                   requireActivity().finish()
               }
                if(requireActivity().supportFragmentManager.fragments.lastOrNull() is MainMapFragment && requireActivity().supportFragmentManager.fragments.size == 3){
                    requireActivity().finish()
                }
                if(requireActivity().supportFragmentManager.fragments.lastOrNull() is MainMapFragment && requireActivity().supportFragmentManager.fragments[requireActivity().supportFragmentManager.fragments.size -2] is AuthFragment){
                    requireActivity().supportFragmentManager.popBackStack()
                    requireActivity().supportFragmentManager.popBackStack()
                }*/
            navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        retainInstance = true

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)
        observes()
        initView()
        initViewClickListeners()
    }

    protected fun <T> observe(liveData: LiveData<T>, action: (T) -> Unit) = view?.run {
        if (!this@FragmentBaseMVVM.isAdded) return@run
        liveData.observe(viewLifecycleOwner, Observer { action(it ?: return@Observer) })
    }

    protected abstract fun initView()
    protected open fun initViewClickListeners() {}
    protected open fun observes() {}
    protected abstract fun navigateUp()

    protected fun navigateBackStack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private var showDialog = true
    private val LOADER_DELAY: Long = 1000
    private var loadingDialog : LoadingDialogFragment? = null

    companion object {
        val LOADING_TAG = "loading_tag"
    }

    open fun showLoadingDialog() {
        showDialog = true
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (showDialog) {
                if (loadingDialog == null || loadingDialog?.dialog == null || !loadingDialog!!.dialog!!
                        .isShowing
                    || loadingDialog!!.isRemoving
                ) {
                    if (loadingDialog != null) {
                        loadingDialog?.dismissAllowingStateLoss()
                    }
                    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                        loadingDialog =
                            LoadingDialogFragment.newInstance()
                        activity?.supportFragmentManager?.beginTransaction()?.add(
                            loadingDialog!!,
                            LOADING_TAG
                        )?.commitAllowingStateLoss()
                    }
                }
            }
        }, LOADER_DELAY)
    }

    open fun closeLoadingDialog() {
        showDialog = false
        if (loadingDialog != null && loadingDialog!!.dialog != null && loadingDialog!!.dialog!!.isShowing
            && !loadingDialog!!.isRemoving
        ) {
            loadingDialog!!.dismissAllowingStateLoss()
        }
    }


}