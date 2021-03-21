package com.diplomayin.data.repository

import com.diplomayin.data.dataservice.RetrofitService
import com.diplomayin.data.datastore.AuthScreenRepository
import com.diplomayin.data.utils.analyzeResponse
import com.diplomayin.data.utils.makeApiCall
import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.responsemodel.AuthResponse
import retrofit2.Response

class AuthScreenRepositoryImpl(private val retrofitService: RetrofitService) : AuthScreenRepository {

    override suspend fun confirm(authBody: AuthBody): Result<AuthResponse> =
        makeApiCall({ analyzeConfirm(retrofitService.confirm(authBody)) })


    private fun analyzeConfirm(response: Response<AuthResponse>): Result<AuthResponse> =
        analyzeResponse(response)
}