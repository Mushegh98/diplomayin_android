package com.diplomayin.data.datastore

import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.entities.responsemodel.LoginResponse

interface LoginScreenRepository {
    suspend fun login(loginBody: LoginBody): Result<LoginResponse>
    fun setToken(token : String)
}