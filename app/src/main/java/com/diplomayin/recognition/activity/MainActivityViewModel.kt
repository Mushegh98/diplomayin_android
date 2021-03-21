package com.diplomayin.recognition.activity

import com.diplomayin.domain.interactor.MainActivityInteractor
import com.diplomayin.recognition.base.viewmodel.BaseViewModel

class MainActivityViewModel(private val mainActivityInteractor: MainActivityInteractor) : BaseViewModel() {

    fun getToken(): String? {
        return mainActivityInteractor.getToken()
    }
}