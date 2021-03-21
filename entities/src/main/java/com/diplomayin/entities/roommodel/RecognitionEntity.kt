package com.diplomayin.entities.roommodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recognition_table")
data class RecognitionEntity(

        @PrimaryKey
        @field:SerializedName("date")
        val date : String,

        @field:SerializedName("in_vehicle")
        val in_vehicle : Long = 0,

        @field:SerializedName("on_bicycle")
        val on_bicycle : Long = 0,

        @field:SerializedName("on_foot")
        val on_foot : Long = 0,

        @field:SerializedName("still")
        val still : Long = 0,

        @field:SerializedName("unknown")
        val unknown : Long = 0,

        @field:SerializedName("tilting")
        val tilting : Long = 0,

        @field:SerializedName("walking")
        val walking : Long = 0,

        @field:SerializedName("running")
        val running : Long = 0,

        )