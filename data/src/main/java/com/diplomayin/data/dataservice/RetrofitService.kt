package com.diplomayin.data.dataservice

import com.diplomayin.data.utils.Constants.Companion.ADD_RECOGNITION
import com.diplomayin.data.utils.Constants.Companion.CONFIRM
import com.diplomayin.data.utils.Constants.Companion.LOGIN
import com.diplomayin.data.utils.Constants.Companion.RECOGNITION
import com.diplomayin.entities.pojo.RegisterBody
import com.diplomayin.entities.responsemodel.RegisterResponse
import com.diplomayin.data.utils.Constants.Companion.REGISTER
import com.diplomayin.entities.pojo.AuthBody
import com.diplomayin.entities.pojo.LoginBody
import com.diplomayin.entities.responsemodel.AuthResponse
import com.diplomayin.entities.responsemodel.LoginResponse
import com.diplomayin.entities.roommodel.RecognitionEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RetrofitService {

    @POST(REGISTER)
    suspend fun register(@Body registerBody : RegisterBody) : Response<RegisterResponse>

    @POST(CONFIRM)
    suspend fun confirm(@Body authBody: AuthBody) : Response<AuthResponse>

    @POST(LOGIN)
    suspend fun login(@Body loginBody: LoginBody) : Response<LoginResponse>

    @POST(ADD_RECOGNITION)
    suspend fun addRecognition(@HeaderMap header : HashMap<String,String>, @Body recognitionEntity: RecognitionEntity )

    @GET(RECOGNITION)
    suspend fun getRecognition(@HeaderMap header : HashMap<String,String>)
}