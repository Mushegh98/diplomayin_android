package com.diplomayin.entities.responsemodel

data class AuthResponse(
    var status : Boolean,
    var data : List<Any>,
    var message : String
)