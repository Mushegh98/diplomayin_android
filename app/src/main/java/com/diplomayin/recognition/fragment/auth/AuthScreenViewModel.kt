package com.diplomayin.recognition.fragment.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diplomayin.domain.interactor.AuthScreenInteractor
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.entities.responsemodel.AuthResponse
import com.diplomayin.recognition.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject

class AuthScreenViewModel(private val authScreenInteractor: AuthScreenInteractor) : BaseViewModel() {


    private val _confirmSuccess = MutableLiveData<String>()
    val confirmSuccess: LiveData<String> get() = _confirmSuccess

    private val _confirmError = MutableLiveData<String>()
    val confirmError: LiveData<String> get() = _confirmError

    private val _visibilityProgress = MutableLiveData<Boolean>()
    val visibilityProgress: LiveData<Boolean> get() = _visibilityProgress

    fun confirm(authBody: AuthBody) {
        viewModelScope.launch {
            loadingStart()
            when (val data = authScreenInteractor.confirm(authBody)) {
                is com.diplomayin.entities.Result.Success -> {
                    _confirmSuccess.value = data.data?.message
                }
                is com.diplomayin.entities.Result.Error -> {
                    val jObjError = JSONObject(data.errors.errorBody?.string())
                    _confirmError.value = jObjError.getJSONObject("error").getString("message")

                }
            }
            loadingEnd()
        }
    }



    private fun loadingStart() {
        _visibilityProgress.value = true
    }

    private fun loadingEnd() {
        _visibilityProgress.value = false
    }
}