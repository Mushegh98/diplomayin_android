package com.diplomayin.data.datastore

import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.entities.responsemodel.RegisterResponse


interface RegisterScreenRepository {
    suspend fun register(registerBody: RegisterBody): Result<RegisterResponse>
}