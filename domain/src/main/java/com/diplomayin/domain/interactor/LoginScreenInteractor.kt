package com.diplomayin.domain.interactor

import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.entities.responsemodel.LoginResponse

interface LoginScreenInteractor {
    suspend fun login(loginBody: LoginBody): com.diplomayin.entities.Result<LoginResponse>
}