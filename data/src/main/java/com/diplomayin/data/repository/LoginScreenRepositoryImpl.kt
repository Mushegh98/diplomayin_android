package com.diplomayin.data.repository

import com.diplomayin.data.dataservice.RetrofitService
import com.diplomayin.data.dataservice.appservice.preferenceservice.PreferenceService
import com.diplomayin.data.datastore.LoginScreenRepository
import com.diplomayin.data.utils.analyzeResponse
import com.diplomayin.data.utils.makeApiCall
import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.entities.responsemodel.AuthResponse
import com.diplomayin.entities.responsemodel.LoginResponse
import retrofit2.Response

class LoginScreenRepositoryImpl(private val retrofitService: RetrofitService,
                                private val preferenceService: PreferenceService) : LoginScreenRepository {

    override suspend fun login(loginBody: LoginBody): Result<LoginResponse> =
        makeApiCall({ analyzeLogin(retrofitService.login(loginBody)) })


    private fun analyzeLogin(response: Response<LoginResponse>): Result<LoginResponse> =
        analyzeResponse(response)

    override fun setToken(token: String) {
        preferenceService.setToken(token)
    }
}