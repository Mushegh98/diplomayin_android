package com.diplomayin.data.dataservice

import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.entities.responsemodel.RegisterResponse
import com.diplomayin.data.utils.Constants.Companion.REGISTER
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST(REGISTER)
    suspend fun register(@Body registerBody : RegisterBody) : Response<RegisterResponse>
}