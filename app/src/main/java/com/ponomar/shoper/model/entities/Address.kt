package com.ponomar.shoper.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(primaryKeys = ["district","street","house","flat"])
@Parcelize
data class Address(
    val district:String,
    val street:String,
    val house:String,
    val flat:Int
):Parcelable{
    override fun toString(): String {
        return "$district район, $street, д. $house, $flat"
    }
}