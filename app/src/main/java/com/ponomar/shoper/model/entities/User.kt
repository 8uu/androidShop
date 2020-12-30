package com.ponomar.shoper.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class User(
        @PrimaryKey val id:Int,
        @SerializedName("fname") val firstName:String,
        val phone:String,
        val email:String?
)