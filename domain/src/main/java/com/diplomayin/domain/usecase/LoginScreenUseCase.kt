package com.diplomayin.domain.usecase

import com.diplomayin.data.datastore.LoginScreenRepository
import com.diplomayin.data.utils.Constants
import com.diplomayin.domain.interactor.LoginScreenInteractor
import com.diplomayin.entities.ApiException
import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.entities.responsemodel.AuthResponse
import com.diplomayin.entities.responsemodel.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginScreenUseCase(private val loginScreenRepository: LoginScreenRepository) :
    LoginScreenInteractor {

    override suspend fun login(loginBody: LoginBody): com.diplomayin.entities.Result<LoginResponse> = withContext(
        Dispatchers.IO) {

        return@withContext when (val result = loginScreenRepository.login(loginBody)) {
            is Result.Success -> {
                result.data?.let {
                    loginScreenRepository.setToken(it.data.token)
                    Result.Success(result.data)
                }
                    ?: Result.Error(ApiException(Constants.DATA_NULL, "", null))
            }
            is Result.Error -> {
                Result.Error(ApiException(Constants.DATA_ERROR, result.errors.errorMessage, result.errors.errorBody))
            }
        }
    }

}