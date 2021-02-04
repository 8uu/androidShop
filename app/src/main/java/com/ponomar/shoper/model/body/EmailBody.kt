package com.ponomar.shoper.model.body


data class EmailBody(
        val email:String,
        override val token:String
):BaseToken()
