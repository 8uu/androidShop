package com.ponomar.shoper.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
        val id:Int,
        val address:Address,
        val date:String,
        val products:List<Product>
): Parcelable
