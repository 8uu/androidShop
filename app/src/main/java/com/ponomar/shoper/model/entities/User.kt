package com.ponomar.shoper.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @PrimaryKey val id:Int,
    val firstName:String,
    val phone:String,
    val email:String?
)