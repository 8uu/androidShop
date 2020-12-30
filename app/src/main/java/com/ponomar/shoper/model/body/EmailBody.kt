package com.ponomar.shoper.model.body

import com.ponomar.shoper.model.TokenResponse

//TODO:MERGE 2 DATA CLASS???
data class EmailBody(
        val email:String,
        val token:String
)
