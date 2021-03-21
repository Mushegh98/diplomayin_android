package com.diplomayin.domain.usecase

import com.diplomayin.data.datastore.AuthScreenRepository
import com.diplomayin.data.utils.Constants
import com.diplomayin.domain.interactor.AuthScreenInteractor
import com.diplomayin.entities.ApiException
import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.responsemodel.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthScreenUseCase(private val authScreenRepository: AuthScreenRepository) : AuthScreenInteractor {

    override suspend fun confirm(authSBody: AuthBody): com.diplomayin.entities.Result<AuthResponse> = withContext(
        Dispatchers.IO) {

        return@withContext when (val result = authScreenRepository.confirm(authSBody)) {
            is Result.Success -> {
                result.data?.let {
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