package com.ponomar.shoper.model

import com.ponomar.shoper.model.entities.Address

data class AddressResponse(
    val status:Int,
    val data:Address?
)