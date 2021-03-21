package com.diplomayin.entities.responsemodel

import com.diplomayin.entities.pojo.Token

data class LoginResponse(
    var status : Boolean,
    var data : Token,
    var message : String
)
