package com.ponomar.shoper.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ponomar.shoper.model.entities.converters.ImageConverters
import kotlinx.android.parcel.Parcelize

@Entity
data class Product(
    @PrimaryKey val id:Int,
    val title:String,
    val desc:String,
    val cost:Float,
    val weight:Int,
    val images:List<String>,
    val tags:List<String>?,
    val quantity:Int?
)