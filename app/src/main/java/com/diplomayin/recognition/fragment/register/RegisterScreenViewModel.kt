package com.diplomayin.recognition.fragment.register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diplomayin.domain.interactor.RegisterScreenInteractor
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.recognition.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class RegisterScreenViewModel(private val registerScreenInteractor: RegisterScreenInteractor) : BaseViewModel() {


    private val _invalidEmail = MutableLiveData<String>()
    val invalidEmail: LiveData<String> get() = _invalidEmail

    private val _invalidPassword = MutableLiveData<String>()
    val invalidPassword: LiveData<String> get() = _invalidPassword

    private val _invalidUsername = MutableLiveData<String>()
    val invalidUsername: LiveData<String> get() = _invalidUsername

    private val _areNotMismatch = MutableLiveData<String>()
    val areNotMismatch: LiveData<String> get() = _areNotMismatch

    private val _validationSuccess = MutableLiveData<String>()
    val validationSuccess: LiveData<String> get() = _validationSuccess

    private val _registerSuccess = MutableLiveData<String>()
    val registerSuccess: LiveData<String> get() = _registerSuccess

    private val _registerError = MutableLiveData<String>()
    val registerError: LiveData<String> get() = _registerError

    private val _visibilityProgress = MutableLiveData<Boolean>()
    val visibilityProgress: LiveData<Boolean> get() = _visibilityProgress


    fun validate(username : String, email : String, password: String, confirmPassword: String) {

        when (val result = registerScreenInteractor.validate(username, email, password, confirmPassword)) {
            "Invalid email." -> {
                _invalidEmail.value = result
            }
            "Invalid password." -> {
                _invalidPassword.value = result
            }
            "Invalid username." -> {
                _invalidUsername.value = result
            }

            "Passwords aren't mismatch." -> {
                _invalidPassword.value = result
            }
            else -> {
                _validationSuccess.value = "Yoy "
            }
        }
    }



    fun register(registerBody: RegisterBody) {
        viewModelScope.launch {
            loadingStart()
            when (val data = registerScreenInteractor.register(registerBody)) {
                is com.diplomayin.entities.Result.Success -> {
                    _registerSuccess.value = data.data?.message
                }
                is com.diplomayin.entities.Result.Error -> {
                    _registerError.value = data.errors.errorMessage
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