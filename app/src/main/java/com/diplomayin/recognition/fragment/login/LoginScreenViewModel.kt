package com.diplomayin.recognition.fragment.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diplomayin.domain.interactor.LoginScreenInteractor
import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.entities.responsemodel.LoginResponse
import com.diplomayin.recognition.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.adapter.rxjava2.Result.response


class LoginScreenViewModel(private val loginScreenInteractor: LoginScreenInteractor) : BaseViewModel() {


    private val _loginSuccess = MutableLiveData<String>()
    val loginSuccess: LiveData<String> get() = _loginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> get() = _loginError

    private val _visibilityProgress = MutableLiveData<Boolean>()
    val visibilityProgress: LiveData<Boolean> get() = _visibilityProgress

    fun login(loginBody: LoginBody) {
        viewModelScope.launch {
            loadingStart()
            when (val data = loginScreenInteractor.login(loginBody)) {
                is com.diplomayin.entities.Result.Success -> {
                    _loginSuccess.value = data.data?.message
                }
                is com.diplomayin.entities.Result.Error -> {
                    val jObjError = JSONObject(data.errors.errorBody?.string())
                    _loginError.value = jObjError.getString("message")
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