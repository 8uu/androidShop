package com.ponomar.shoper.model

import com.ponomar.shoper.model.entities.Product

data class ProductResponse(
    val status:Int,
    val data: List<Product>?
)