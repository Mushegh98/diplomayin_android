package com.diplomayin.entities.responsemodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class RegisterResponse(
//    @Json(name = "status") var status : Boolean,
//    @Json(name = "data") var data : List<Any>,
//    @Json(name = "message") var message : String

    var status : Boolean,
    var data : List<Any>,
    var message : String
)