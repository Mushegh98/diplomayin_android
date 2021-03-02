package com.diplomayin.entities.pojo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class RegisterBody (
//    @Json(name = "email") var email : String,
//    @Json(name = "password") var password : String,
//    @Json(name = "passwordConfirmation") var passwordConfirmation : String,
//    @Json(name = "name") var name : String

   var email : String,
   var password : String,
   var passwordConfirmation : String,
   var name : String
)