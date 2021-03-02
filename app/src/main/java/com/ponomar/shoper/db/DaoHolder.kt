package com.ponomar.shoper.db

import javax.inject.Inject

class DaoHolder @Inject constructor(
    val addressDAO: AddressDAO,
    val cartDAO: CartDAO,
    val productDAO: ProductDAO,
    val userDAO: UserDAO
)