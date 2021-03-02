package com.diplomayin.entities

import okhttp3.ResponseBody

data class ApiException(val errorCode: Int, val errorMessage:String? = null,  val errorBody : ResponseBody?)