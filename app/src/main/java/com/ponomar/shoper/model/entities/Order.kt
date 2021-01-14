package com.ponomar.shoper.model.entities

data class Order(
        val id:Int,
        val address:Address,
        val date:String,
        val products:List<Product>
)
