package com.diplomayin.data.utils

import android.util.Log
import com.diplomayin.entities.ApiException

import retrofit2.Response
import java.lang.Exception

suspend fun <R> makeApiCall(
    call: suspend () -> com.diplomayin.entities.Result<R>,
    errorCode: Int = 4567
) = try {
    call()
} catch (e: Exception) {
    Log.i("makeApiCall", "makeApiCall: ${e.message}")
    com.diplomayin.entities.Result.Error(ApiException(errorCode,e.message,null))
}

fun <R> analyzeResponse(
    response: Response<R>
): com.diplomayin.entities.Result<R> {
    return when {
        response.isSuccessful -> {
            com.diplomayin.entities.Result.Success(response.body())
        }
        else -> {
            com.diplomayin.entities.Result.Error(ApiException(response.code(),response.message(),response.errorBody()!!))
        }
    }
}