package com.ponomar.shoper.repository

import android.service.autofill.UserData
import com.nhaarman.mockitokotlin2.mock
import com.ponomar.shoper.db.*
import com.ponomar.shoper.network.*
import org.junit.Before

abstract class ApiAbstract {
    lateinit var client: Client
    lateinit var daoHolder: DaoHolder

    val productService: ProductService = mock()
    val authService: AuthService = mock()
    val newsService: NewsService = mock()
    val orderService: OrderService = mock()
    val userService: UserService = mock()

    val addressDAO: AddressDAO = mock()
    val productDAO: ProductDAO = mock()
    val userDAO: UserDAO = mock()
    val cartDAO: CartDAO = mock()

    @Before
    fun init(){
        client = Client(userService, productService, authService, orderService, newsService)
        daoHolder = DaoHolder(addressDAO, cartDAO, productDAO, userDAO)
    }



}