package com.diplomayin.domain.interactor

import com.diplomayin.entities.Result
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.entities.responsemodel.RegisterResponse


interface RegisterScreenInteractor {
    fun validate(username : String, email : String, password: String, confirmPassword: String) : String
    suspend fun register(registerBody: RegisterBody): Result<RegisterResponse>
}