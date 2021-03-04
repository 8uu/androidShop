package com.ponomar.shoper.common

import com.ponomar.shoper.model.ProductResponse
import com.ponomar.shoper.model.entities.*
import com.ponomar.shoper.model.sqlOutput.EmbeddedProduct

object MockUtilUnit {

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

    fun mockCartInfo() = Cart(
            1,
            1
    )

    fun mockCartInfoList() = arrayListOf(mockCartInfo())

    fun mockEmdeddedProduct() = EmbeddedProduct(mockProduct(), mockCartInfo())

    fun mockEmdeddedProductList() = arrayListOf(mockEmdeddedProduct())

    fun mockAddress() = Address(
            district = "VO",
            street = "12 liniya",
            house = "51",
            flat = 320
    )

    fun mockAddressList() = arrayListOf(mockAddress())

    fun mockOrder() = Order(1, mockAddress(),"01.01.21", mockListOfProduct(),1)

    fun mockOrderList() = arrayListOf(mockOrder())

    fun mockNews() = News(1,"Title","Description","01.01.21","large","ImageURL")

    fun mockNewsList() = arrayListOf(mockNews()
    )

}