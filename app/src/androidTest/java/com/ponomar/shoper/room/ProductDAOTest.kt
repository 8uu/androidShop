package com.ponomar.shoper.room

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ponomar.shoper.common.MockUtil.mockListOfProduct
import com.ponomar.shoper.common.MockUtil.mockProduct
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.db.ProductDAO
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDAOTest: Database(){

    private lateinit var dao: ProductDAO

    @Before
    fun init(){
        dao = db.getProductDao()
    }

    @Test
    fun insertingAndGettingProductFromDaoTest() = runBlocking{
        val productMock = mockListOfProduct()
        dao.insertAll(productMock)

        val loadFromDB = dao.getProducts()

        assertEquals("product list", mockProduct().toString(),loadFromDB.toString())
        val mockProduct = mockProduct()
        assertEquals("product",mockProduct.toString(),loadFromDB[0].toString())
    }
}