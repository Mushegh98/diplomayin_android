package com.diplomayin.data.datastore

import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.responsemodel.AuthResponse

interface AuthScreenRepository {
    suspend fun confirm(authBody: AuthBody): Result<AuthResponse>
}