package com.ponomar.shoper.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Address(
    @PrimaryKey val id:Int,
    val district:String,
    val street:String,
    val house:String,
    val flat:Int
)