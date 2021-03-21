package com.diplomayin.domain.usecase

import android.text.TextUtils
import android.util.Patterns
import com.diplomayin.data.datastore.RegisterScreenRepository
import com.diplomayin.data.utils.Constants.Companion.DATA_ERROR
import com.diplomayin.data.utils.Constants.Companion.DATA_NULL
import com.diplomayin.domain.interactor.RegisterScreenInteractor
import com.diplomayin.entities.ApiException
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.entities.responsemodel.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RegisterScreenUseCase(private val registerScreenRepository: RegisterScreenRepository) :
    RegisterScreenInteractor {

    private fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun isValidPassword(password : String) : Boolean{
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}")
        return (regex.matches(password))
    }

    private fun isPasswordAndConfirmPasswordEquals(password: String, confirmPassword : String) : Boolean{
        return password == confirmPassword
    }

     private fun isValidUsername(username : String) : Boolean{
        val regex = Regex("^(?=[a-zA-Z0-9._]{8,20}\$)(?!.*[_.]{2})[^_.].*[^_.]\$")
        return regex.matches(username)
    }

    override fun validate(username : String, email : String, password: String, confirmPassword: String) : String {
        if(!isValidEmail(email)){
            return "Invalid email."
        }else if(!isValidPassword(password)){
            return "Invalid password."
        }else if(!isValidUsername(username)){
            return "Invalid username."
        }
        else if(!isPasswordAndConfirmPasswordEquals(password, confirmPassword)){
            return "Passwords aren't mismatch."
        }else {
            return "Valid Data."
        }
    }


    override suspend fun register(registerBody: RegisterBody): com.diplomayin.entities.Result<RegisterResponse> = withContext(Dispatchers.IO) {

        return@withContext when (val result = registerScreenRepository.register(registerBody)) {
            is com.diplomayin.entities.Result.Success -> {
                result.data?.let {
                    com.diplomayin.entities.Result.Success(result.data)
                }
                    ?: com.diplomayin.entities.Result.Error(ApiException(DATA_NULL, "", null))
            }
            is com.diplomayin.entities.Result.Error -> {
                com.diplomayin.entities.Result.Error(ApiException(result.errors.errorCode, result.errors.errorMessage, result.errors.errorBody))
            }
        }
    }

}