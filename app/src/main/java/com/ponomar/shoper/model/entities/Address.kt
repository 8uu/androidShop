package com.ponomar.shoper.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(primaryKeys = ["district","street","house","flat"])
data class Address(
    val district:String,
    val street:String,
    val house:String,
    val flat:Int
){
    override fun toString(): String {
        return "$district район, $street, д. $house, $flat"
    }
}