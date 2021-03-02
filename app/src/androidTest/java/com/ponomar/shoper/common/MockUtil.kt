package com.ponomar.shoper.common

import com.ponomar.shoper.model.ProductResponse
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.model.entities.User

object MockUtil {

    fun mockProductResponse() = ProductResponse(
        status = 0,
        data = mockListOfProduct()
    )

    fun mockProduct() = Product(
        id = 1,
        title = "Наполеон",
        desc = "Its test product!",
        cost = 1443f,
        weight = 800,
        images = arrayListOf(),
        tags = arrayListOf(
            "торт",
            "сливочный крем",
            "слои",
            "сгущенное молоко",
            "популярные"),
        count = 1,
        carbs = 100f,
        protein = 10f,
        fat = 27.3f,
        composition = "Тесто (мука пшеничная хлебопекарная в/с, вода, маргарин (рафинированные дезодорированные масла (модифицированные пальмовые, пальмовое, подсолнечное), вода, эмульгаторы (Е471, Е475, соевый лецитин), соль, консерванты (Е200, Е211), краситель (Е160а), ароматизатор масла, идентичный натуральному, регулятор кислотности, масло сливочное (пастеризованные сливки (приготовленные из коровьего молока), соль поваренная пищевая, улучшитель х/п, уксус, ароматизатор), крем (сливки (сливки, загуститель каррагинан), молоко, сахар-песок, смесь для заварного крема (пудра сахарная, загуститель Е1414, молоко сухое цельное, загуститель альгинат натрия, ароматизаторы, краситель бета-каротин)), сахарная пудра.",
        energy = 383f
    )

    fun mockListOfProduct() = arrayListOf(mockProduct())

    fun mockUser() = User(
        id = 1,
        firstName = "User",
        phone = "88005553535",
        email = "test@test.com"
    )

}