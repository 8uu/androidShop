package com.ponomar.shoper.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
        val id:Int,
        val address:Address,
        val date:String,
        val products:List<Product>,
        val status:Int
): Parcelable{
    fun calculateTotalCost():Int{
        var totalCost = 0
        for(product in products) totalCost += product.cost.toInt() * product.count!!
        return totalCost
    }

    fun calculateTotalQuantityOfProduct():Int{
        var totalQuantity = 0
        for(product in products) totalQuantity += product.count!!
        return totalQuantity
    }

    fun convertStatusToStr():String{
        return when(status){
            1 -> "Оформлен"
            2 -> "Подтвержен"
            3 -> "Курьер выехал"
            4 -> "Доставлено"
            else -> "Отменен"
        }
    }
}
