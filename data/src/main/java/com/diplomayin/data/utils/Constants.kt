package com.diplomayin.data.utils

abstract class Constants {
    companion object {
        const val BASE_URL = "https://safe-hamlet-52762.herokuapp.com/"
        const val REGISTER = "register"
        const val CONFIRM = "confirm"
        const val LOGIN = "login"
        const val ADD_RECOGNITION = "addrecognition"
        const val RECOGNITION = "recognition"

        // data response
        const val DATA_NULL = 1000
        const val DATA_ERROR = 1001

        const val ACCEPTED_CONFIDENCE = 84
        const val DETECTION_INTERVAL_IN_MILLISECONDS : Long = 30 * 1000
    }
}