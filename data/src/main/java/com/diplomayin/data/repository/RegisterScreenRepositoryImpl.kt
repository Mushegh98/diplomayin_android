package com.diplomayin.data.repository

import com.diplomayin.data.dataservice.RetrofitService
import com.diplomayin.data.datastore.RegisterScreenRepository
import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.entities.responsemodel.RegisterResponse
import com.diplomayin.data.utils.analyzeResponse
import com.diplomayin.data.utils.makeApiCall
import retrofit2.Response

class RegisterScreenRepositoryImpl(private val retrofitService: RetrofitService) :
    RegisterScreenRepository {

    override suspend fun register(registerBody: RegisterBody): Result<RegisterResponse> =
        makeApiCall({ analyzeRegister(retrofitService.register(registerBody)) })


    private fun analyzeRegister(response: Response<RegisterResponse>): Result<RegisterResponse> =
        analyzeResponse(response)
}