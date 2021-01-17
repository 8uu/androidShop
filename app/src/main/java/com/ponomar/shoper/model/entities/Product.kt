package com.ponomar.shoper.model.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.ponomar.shoper.model.entities.converters.ImageConverters
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Product(
    @PrimaryKey val id:Int,
    val title:String,
    val desc:String,
    val cost:Float,
    val weight:Int,
    val images:List<String>,
    val tags:List<String>?,
    val count:Int?,
    val carbs:Float?,
    val protein:Float?,
    val fat:Float?,
    val composition:String?,
    @SerializedName("energy_value") val energy:Float?

):Parcelable