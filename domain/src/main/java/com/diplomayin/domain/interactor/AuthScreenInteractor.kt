package com.diplomayin.domain.interactor

import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.responsemodel.AuthResponse

interface AuthScreenInteractor {
    suspend fun confirm(authSBody: AuthBody): com.diplomayin.entities.Result<AuthResponse>
}