package com.ponomar.shoper.network

import com.ponomar.shoper.model.entities.User
import javax.inject.Inject

class Client @Inject constructor(
    private val userService: UserService,
    private val addressService: AddressService,
    private val productService: ProductService
) {

}