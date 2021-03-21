package com.diplomayin.entities.pojo

import com.google.gson.annotations.SerializedName

data class RecognitionBody(

        @SerializedName("date")
        val date : String,

        @SerializedName("in_vehicle")
        val in_vehicle : Long = 0,

        @SerializedName("on_bicycle")
        val on_bicycle : Long = 0,

        @SerializedName("on_foot")
        val on_foot : Long = 0,

        @SerializedName("still")
        val still : Long = 0,

        @SerializedName("unknown")
        val unknown : Long = 0,

        @SerializedName("tilting")
        val tilting : Long = 0,

        @SerializedName("walking")
        val walking : Long = 0,

        @SerializedName("running")
        val running : Long = 0,

        )